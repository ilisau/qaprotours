package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;

import java.util.List;

/**
 * @author Varya Petrova
 */
public interface TourService {

    List<Tour> findAll(int currentPage, int pageSize, TourCriteria tourCriteria);

    List<Tour> findAllByCriteria(TourCriteria tourCriteria);

    void save(Tour tour);

    Tour getById(Long tourId);

    void delete(Long tourId);

}
