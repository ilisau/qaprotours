package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.repository.TourRepository;
import com.solvd.qaprotours.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Varya Petrova, Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final int PAGE_SIZE = 20;

    @Override
    @Transactional(readOnly = true)
    public Flux<Tour> getAll(Integer currentPage, Integer pageSize, TourCriteria tourCriteria) {
        if (currentPage == null || pageSize == null) {
            currentPage = 0;
            pageSize = PAGE_SIZE;
        }
        Sort sort = Sort.by(Sort.Order.asc("arrivalTime"),
                Sort.Order.desc("rating"));
        return tourRepository.findAll(sort)
                .buffer(pageSize)
                .skip(currentPage)
                .take(1)
                .flatMapIterable(tours -> tours);
    }

    @Override
    @Transactional
    public Mono<Tour> save(Tour tour) {
        return tourRepository.save(tour);
    }

    @Override
    @Transactional
    public Mono<Tour> publish(Tour tour) {
        return Mono.just(tour)
                .map(t -> {
                    t.setDraft(false);
                    return t;
                })
                .flatMap(tourRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Tour> getById(Long tourId) {
        Mono<Tour> error = Mono.error(
                new ResourceDoesNotExistException("tour with id " + tourId + " does not exist")
        );
        return tourRepository.findById(tourId)
                .switchIfEmpty(error);
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long tourId) {
        return tourRepository.deleteById(tourId);
    }

    @Override
    public Mono<Void> addImage(Long tourId, String fileName) {
        return getById(tourId)
                .map(tour -> {
                    tour.getImageUrls().add(fileName);
                    return tour;
                })
                .flatMap(tourRepository::save)
                .then();
    }

}
