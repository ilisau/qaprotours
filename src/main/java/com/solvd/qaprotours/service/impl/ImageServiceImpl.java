package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.ImageUploadException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.service.ImageService;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.service.property.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
    private final MinioProperties minioProperties;
    private final TourService tourService;
    private final List<String> imageExtensions = List.of("jpg", "jpeg", "png", "svg", "webp");

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

            String fileName400 = generateThumbnailName(tour, file, 400);
            InputStream is400 = getThumbnailInputStream(file, 400);
            saveImage(is400, fileName400);

            String fileName100 = generateThumbnailName(tour, file, 100);
            InputStream is100 = getThumbnailInputStream(file, 100);
            saveImage(is100, fileName100);

            tourService.addImage(tourId, fileName);
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: " + e.getMessage());
        }
    }

    @SneakyThrows
    private void createBucket() {
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(minioProperties.getBucket())
                        .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFileName(Tour tour, MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name");
        }
        String extension = getExtension(file);
        if (tour.getImageUrls().size() > 0) {
            return "tour_" + tour.getId() + "_full_" + (tour.getImageUrls().size()) + "." + extension;
        }
        return "tour_" + tour.getId() + "_full" + "." + extension;
    }

    private String generateThumbnailName(Tour tour, MultipartFile file, int height) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name");
        }
        String extension = getExtension(file);
        if (tour.getImageUrls().size() > 0) {
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
                        .bucket(minioProperties.getBucket())
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
