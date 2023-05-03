package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.Image;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.service.property.ImageProperties;
import com.solvd.qaprotours.service.property.MinioProperties;
import io.minio.MinioClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

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
    void uploadImage() {
        try {
            Long tourId = 1L;
            Image image = new Image();
            MockMultipartFile file = new MockMultipartFile(
                    "image1.jpeg",
                    "image1.jpeg",
                    "image/jpeg",
                    generateImage());
            image.setFile(file);
            Tour tour = new Tour();
            tour.setId(tourId);
            tour.setImageUrls(Collections.emptyList());
            when(minioClient.bucketExists(any()))
                    .thenReturn(true);
            when(minioProperties.getBucket())
                    .thenReturn("tours");
            when(tourService.getById(tourId))
                    .thenReturn(Mono.just(tour));
            when(tourService.addImage(eq(tourId), eq("tour_1_full.jpeg")))
                    .thenReturn(Mono.empty());
            Integer thumbHeight = 100;
            when(imageProperties.getThumbnails())
                    .thenReturn(List.of(thumbHeight));
            Mono<Void> result = imageService.uploadImage(1L, image);
            StepVerifier.create(result)
                    .expectNextCount(0)
                    .verifyComplete();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream generateImage() throws Exception {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                int color = (x % 2 == 0 && y % 2 == 0) ? 0xFF0000 : 0xFFFFFF;
                image.setRGB(x, y, color);
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

}
