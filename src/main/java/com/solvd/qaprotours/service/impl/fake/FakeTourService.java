package com.solvd.qaprotours.service.impl.fake;

import com.solvd.qaprotours.domain.Pagination;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.service.TourService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

public class FakeTourService implements TourService {

    @Override
    public Mono<Tour> getById(final Long tourId) {
        Tour tour = new Tour();
        tour.setId(tourId);
        tour.setImageUrls(new ArrayList<>());
        return Mono.just(tour);
    }

    @Override
    public Flux<Tour> getAll(final Pagination pagination,
                             final TourCriteria tourCriteria) {
        Mono<Tour> firstTour = getById(1L);
        Mono<Tour> secondTour = getById(2L);
        return Flux.concat(firstTour, secondTour);
    }

    @Override
    public Flux<Tour> getAll(final Pagination pagination,
                             final String description) {
        Mono<Tour> firstTour = getById(1L);
        Mono<Tour> secondTour = getById(2L);
        return Flux.concat(firstTour, secondTour);
    }

    @Override
    public Mono<Tour> save(final Tour tour) {
        tour.setId(1L);
        return Mono.just(tour);
    }

    @Override
    public Mono<Tour> publish(final Tour tour) {
        tour.setId(1L);
        return Mono.just(tour);
    }

    @Override
    public Mono<Void> delete(final Long tourId) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> addImage(final Long tourId, final String fileName) {
        return Mono.empty();
    }

}
