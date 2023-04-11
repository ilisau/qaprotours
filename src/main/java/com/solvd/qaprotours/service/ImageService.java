package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.Image;
import reactor.core.publisher.Mono;

/**
 * @author Lisov Ilya
 */
public interface ImageService {

    Mono<Void> uploadImage(Long tourId, Image image);

}
