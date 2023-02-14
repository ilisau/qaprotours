package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.ImageUploadException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.service.ImageService;
import com.solvd.qaprotours.service.TourService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final MinioClient minioClient;
    private final TourService tourService;
    private final List<String> imageExtensions = List.of("jpg", "jpeg", "png", "svg", "webp");

    @Value("${minio.bucket}")
    private String defaultBucket;

    @Override
    public void uploadImage(Long tourId, MultipartFile file) {
        try {
            if (!imageExtensions.contains(getExtension(file))) {
                throw new ImageUploadException("Image must have one of the following extensions: " + imageExtensions);
            }
            createBucket();
            Tour tour = tourService.getById(tourId);

            String fileName = generateFileName(tour, file);
            InputStream inputStream = file.getInputStream();
            saveImage(inputStream, fileName);

            String fileName200 = generateThumbnailName(tour, file, 400);
            InputStream is200 = getThumbnailInputStream(file, 400);
            saveImage(is200, fileName200);

            String fileName100 = generateThumbnailName(tour, file, 100);
            InputStream is100 = getThumbnailInputStream(file, 100);
            saveImage(is100, fileName100);

            tourService.setImage(tourId, fileName);
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: " + e.getMessage());
        }
    }

    @SneakyThrows
    private void createBucket() {
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(defaultBucket)
                        .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(defaultBucket)
                    .build());
        }
    }

    private String generateFileName(Tour tour, MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name");
        }
        String extension = getExtension(file);
        if(tour.getImageUrls().size() > 1) {
            return "tour_" + tour.getId() + "_full_" + (tour.getImageUrls().size()) + "." + extension;
        }
        return "tour_" + tour.getId() + "_full" + "." + extension;
    }

    private String generateThumbnailName(Tour tour, MultipartFile file, int height) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name");
        }
        String extension = getExtension(file);
        if(tour.getImageUrls().size() > 1) {
            return "tour_" + tour.getId() + "_thumb_" + height + "_" + (tour.getImageUrls().size()) + "." + extension;
        }
        return "tour_" + tour.getId() + "_thumb" + height + "." + extension;
    }

    private String getExtension(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name");
        }
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveImage(InputStream inputStream, String fileName) {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .stream(inputStream, inputStream.available(), -1)
                        .bucket(defaultBucket)
                        .object(fileName)
                        .build()
        );
    }

    @SneakyThrows
    private InputStream getThumbnailInputStream(MultipartFile file, int height) {
        Image img = ImageIO.read(file.getInputStream());
        double ratio = 1.0 * height / img.getHeight(null);
        int width = img.getWidth(null);

        Image newImg = ImageIO.read(file.getInputStream()).getScaledInstance((int) (width * ratio), height, BufferedImage.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage((int) (width * ratio), height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(newImg, null, null);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, getExtension(file), outStream);

        return new ByteArrayInputStream(outStream.toByteArray());
    }

}
