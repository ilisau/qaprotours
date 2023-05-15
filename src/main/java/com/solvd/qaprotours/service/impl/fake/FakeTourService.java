package com.solvd.qaprotours.service.impl.fake;

import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.service.TourService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

public class FakeTourService implements TourService {

    @Override
    public Mono<Tour> getById(Long tourId) {
        Tour tour = new Tour();
        tour.setId(tourId);
        tour.setImageUrls(new ArrayList<>());
        return Mono.just(tour);
    }

    @Override
    public Flux<Tour> getAll(Integer currentPage, Integer pageSize, TourCriteria tourCriteria) {
        Mono<Tour> tour1 = getById(1L);
        Mono<Tour> tour2 = getById(2L);
        return Flux.concat(tour1, tour2);
    }

    @Override
    public Mono<Tour> save(Tour tour) {
        tour.setId(1L);
        return Mono.just(tour);
    }

    @Override
    public Mono<Tour> publish(Tour tour) {
        tour.setId(1L);
        return Mono.just(tour);
    }

    @Override
    public Mono<Void> delete(Long tourId) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> addImage(Long tourId, String fileName) {
        return Mono.empty();
    }

}
