package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.Pagination;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Varya Petrova, Lisov Ilya
 */
public interface TourService {

    /**
     * Get tour by id.
     * @param tourId tour id
     * @return tour
     */
    Mono<Tour> getById(Long tourId);

    /**
     * Get all tours.
     *
     * @param pagination   pagination
     * @param tourCriteria tour criteria
     * @return list of tours that matches criteria
     */
    Flux<Tour> getAll(Pagination pagination,
                      TourCriteria tourCriteria);

    /**
     * Save tour.
     * @param tour tour
     * @return saved tour
     */
    Mono<Tour> save(Tour tour);

    /**
     * Publish tour.
     * @param tour tour
     * @return published tour
     */
    Mono<Tour> publish(Tour tour);

    /**
     * Delete tour.
     * @param tourId tour id
     * @return empty response
     */
    Mono<Void> delete(Long tourId);

    /**
     * Add image to tour.
     * @param tourId tour id
     * @param fileName image name
     * @return empty response
     */
    Mono<Void> addImage(Long tourId, String fileName);

}
