package com.solvd.qaprotours.service.impl.fake;

import com.solvd.qaprotours.domain.Image;
import com.solvd.qaprotours.service.ImageService;
import reactor.core.publisher.Mono;

public class FakeImageService implements ImageService {

    @Override
    public Mono<Void> uploadImage(Long tourId, Image image) {
        return Mono.empty();
    }

}
