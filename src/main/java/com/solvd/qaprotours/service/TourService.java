package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.Tour;

import java.util.List;

/**
 * @author Lisov Ilya
 */
public interface TourService {

    List<Tour> getAll();

    void save(Tour tour);

    Tour getById(Long tourId);

    void delete(Long tourId);

}
