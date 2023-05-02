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

import java.util.Collections;

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
        Long tourId = 1L;
        Image image = new Image();
        image.setFile(new MockMultipartFile(
                "image1.jpeg",
                "image1.jpeg",
                "image/jpeg",
                new byte[]{1, 2, 3})
        );
        Tour tour = new Tour();
        tour.setId(tourId);
        tour.setImageUrls(Collections.emptyList());
        try {
            when(minioClient.bucketExists(any()))
                    .thenReturn(true);
            when(minioProperties.getBucket())
                    .thenReturn("tours");
            when(tourService.getById(tourId))
                    .thenReturn(Mono.just(tour));
            when(tourService.addImage(eq(tourId), eq("tour_1_full.jpeg")))
                    .thenReturn(Mono.empty());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Mono<Void> result = imageService.uploadImage(1L, image);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

}
