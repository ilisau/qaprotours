package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.Tour;
import com.solvd.qaprotours.domain.TourCriteria;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author Lisov Ilya
 */
public interface TourService {

    List<Tour> getAll(TourCriteria tourCriteria, Pageable page);

    void save(Tour tour);

    Tour getById(Long tourId);

    void delete(Long tourId);

}
