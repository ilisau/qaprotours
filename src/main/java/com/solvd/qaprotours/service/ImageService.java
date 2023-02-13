package com.solvd.qaprotours.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Lisov Ilya
 */
public interface ImageService {

    void uploadImage(Long tourId, MultipartFile file);

}
