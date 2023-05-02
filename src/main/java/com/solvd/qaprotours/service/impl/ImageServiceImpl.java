package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.ImageUploadException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.service.ImageService;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.service.property.ImageProperties;
import com.solvd.qaprotours.service.property.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final TourService tourService;
    private final ImageProperties imageProperties;

    @Override
    public Mono<Void> uploadImage(Long tourId, com.solvd.qaprotours.domain.Image image) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: " + e.getMessage());
        }
        return tourService.getById(tourId)
                .flatMap(tour -> {
                    MultipartFile file = image.getFile();
                    if (file.isEmpty() || file.getOriginalFilename() == null) {
                        throw new ImageUploadException("Image must have name");
                    }
                    String fileName = generateFileName(tour, file);
                    InputStream inputStream;
                    try {
                        inputStream = file.getInputStream();
                    } catch (IOException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                    saveImage(inputStream, fileName);
                    imageProperties.getThumbnails().forEach((Integer size) -> {
                        String name = generateThumbnailName(tour, file, size);
                        InputStream is = getThumbnailInputStream(file, size);
                        saveImage(is, name);
                    });
                    return Mono.just(fileName);
                })
                .onErrorResume(Mono::error)
                .flatMap(fileName -> tourService.addImage(tourId, fileName))
                .then();

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
        String extension = getExtension(file);
        if (tour.getImageUrls().size() > 0) {
            return "tour_" + tour.getId() + "_full_" + (tour.getImageUrls().size()) + "." + extension;
        }
        return "tour_" + tour.getId() + "_full" + "." + extension;
    }

    private String generateThumbnailName(Tour tour, MultipartFile file, int height) {
        String extension = getExtension(file);
        if (tour.getImageUrls().size() > 0) {
            return "tour_" + tour.getId() + "_thumb_" + height + "_" + (tour.getImageUrls().size()) + "." + extension;
        }
        return "tour_" + tour.getId() + "_thumb" + height + "." + extension;
    }

    private String getExtension(MultipartFile file) {
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
