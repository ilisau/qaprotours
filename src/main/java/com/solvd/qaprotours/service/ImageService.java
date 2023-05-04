package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.Image;
import reactor.core.publisher.Mono;

/**
 * @author Lisov Ilya
 */
public interface ImageService {

    /**
     * Upload image to tour.
     *
     * @param tourId tour id
     * @param image  image
     * @return empty response
     */
    Mono<Void> uploadImage(Long tourId, Image image);

}
