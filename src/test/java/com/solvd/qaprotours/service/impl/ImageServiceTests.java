package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.config.TestConfig;
import com.solvd.qaprotours.domain.Image;
import com.solvd.qaprotours.repository.TicketRepository;
import com.solvd.qaprotours.repository.TourRepository;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class ImageServiceTests {

    @MockBean
    private MinioClient minioClient;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private TourRepository tourRepository;

    @Autowired
    private ImageServiceImpl imageService;

    @Test
    @SneakyThrows
    void uploadImage() {
        Image image = generateImage();
        Mockito.when(minioClient.bucketExists(Mockito.any()))
                .thenReturn(true);
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

}
