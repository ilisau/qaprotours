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

    Mono<Tour> getById(Long tourId);

    Flux<Tour> getAll(Pagination pagination,
                      TourCriteria tourCriteria);

    Mono<Tour> save(Tour tour);

    Mono<Tour> publish(Tour tour);

    Mono<Void> delete(Long tourId);

    Mono<Void> addImage(Long tourId, String fileName);

}
