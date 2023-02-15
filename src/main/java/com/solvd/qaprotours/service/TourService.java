package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;

import java.util.List;

/**
 * @author Varya Petrova
 */
public interface TourService {

    Tour getById(Long tourId);

    List<Tour> getAll(int currentPage, int pageSize, TourCriteria tourCriteria);

    List<Tour> getAllByCriteria(TourCriteria tourCriteria);

    Tour save(Tour tour);

    Tour publish(Tour tour);

    void delete(Long tourId);

    void addImage(Long tourId, String fileName);

}
