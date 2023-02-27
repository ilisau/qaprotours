package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;

import java.util.List;

/**
 * @author Varya Petrova, Lisov Ilya
 */
public interface TourService {

    Tour getById(Long tourId);

    List<Tour> getAll(Integer currentPage, Integer pageSize, TourCriteria tourCriteria);

    List<Tour> getAllByCriteria(TourCriteria tourCriteria, int currentPage, int pageSize);

    Tour save(Tour tour);

    Tour publish(Tour tour);

    void delete(Long tourId);

    void addImage(Long tourId, String fileName);

}
