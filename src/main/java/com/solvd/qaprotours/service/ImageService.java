package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.Image;

/**
 * @author Lisov Ilya
 */
public interface ImageService {

    void uploadImage(Long tourId, Image image);

}
