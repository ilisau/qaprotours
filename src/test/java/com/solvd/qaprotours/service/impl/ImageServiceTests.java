package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.Image;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.service.property.ImageProperties;
import com.solvd.qaprotours.service.property.MinioProperties;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTests {

    @Mock
    private MinioClient minioClient;

    @Mock
    private MinioProperties minioProperties;

    @Mock
    private TourService tourService;

    @Mock
    private ImageProperties imageProperties;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    @SneakyThrows
    void uploadImage() {
        Tour tour = generateTour();
        Image image = generateImage();
        Mockito.when(minioClient.bucketExists(Mockito.any()))
                .thenReturn(true);
        Mockito.when(minioProperties.getBucket())
                .thenReturn("tours");
        Mockito.when(tourService.getById(tour.getId()))
                .thenReturn(Mono.just(tour));
        Mockito.when(tourService.addImage(Mockito.eq(tour.getId()),
                        Mockito.eq("tour_1_full.jpeg")))
                .thenReturn(Mono.empty());
        Integer thumbHeight = 100;
        Mockito.when(imageProperties.getThumbnails())
                .thenReturn(List.of(thumbHeight));
        Mono<Void> result = imageService.uploadImage(1L, image);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

    private Image generateImage() {
        Image image = new Image();
        try {
            MockMultipartFile file = new MockMultipartFile(
                    "image1.jpeg",
                    "image1.jpeg",
                    "image/jpeg",
                    generateImageByteStream());
            image.setFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    private InputStream generateImageByteStream() throws Exception {
        int size = 100;
        BufferedImage image = new BufferedImage(
                size,
                size,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int color;
                if (x % 2 == 0 && y % 2 == 0) {
                    color = 0xFF0000;
                } else {
                    color = 0xFFFFFF;
                }
                image.setRGB(x, y, color);
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private Tour generateTour() {
        Long tourId = 1L;
        Tour tour = new Tour();
        tour.setId(tourId);
        tour.setImageUrls(Collections.emptyList());
        return tour;
    }

}
