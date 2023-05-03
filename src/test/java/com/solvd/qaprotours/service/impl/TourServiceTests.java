package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.repository.TourRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TourServiceTests {

    @Mock
    private TourRepository tourRepository;

    @InjectMocks
    private TourServiceImpl tourService;

    @Test
    void getAll() {
        Integer currentPage = 0;
        Integer pageSize = 5;
        TourCriteria tourCriteria = null;
        List<Tour> tours = generateTours();
        when(tourRepository.findAll(any()))
                .thenReturn(Flux.just(tours.toArray(new Tour[0])));
        Flux<Tour> result = tourService.getAll(currentPage, pageSize, tourCriteria);
        StepVerifier.create(result)
                .expectNext(tours.get(0))
                .expectNext(tours.get(1))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getAllWithNull() {
        Integer currentPage = null;
        Integer pageSize = null;
        TourCriteria tourCriteria = null;
        List<Tour> tours = generateTours();
        when(tourRepository.findAll(any()))
                .thenReturn(Flux.just(tours.toArray(new Tour[0])));
        Flux<Tour> result = tourService.getAll(currentPage, pageSize, tourCriteria);
        StepVerifier.create(result)
                .expectNext(tours.get(0))
                .expectNext(tours.get(1))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void save() {
        Tour tour = generateTour();
        when(tourRepository.save(tour))
                .thenReturn(Mono.just(tour));
        Mono<Tour> result = tourService.save(tour);
        StepVerifier.create(result)
                .expectNext(tour)
                .verifyComplete();
        verify(tourRepository).save(tour);
        assertTrue(tour.isDraft());
    }

    @Test
    void publish() {
        Tour tour = generateTour();
        when(tourRepository.save(tour))
                .thenReturn(Mono.just(tour));
        Mono<Tour> result = tourService.publish(tour);
        StepVerifier.create(result)
                .expectNext(tour)
                .verifyComplete();
        verify(tourRepository).save(tour);
        assertFalse(tour.isDraft());
    }

    @Test
    void getByExistingId() {
        Tour tour = generateTour();
        when(tourRepository.findById(tour.getId()))
                .thenReturn(Mono.just(tour));
        Mono<Tour> result = tourService.getById(tour.getId());
        StepVerifier.create(result)
                .expectNext(tour)
                .verifyComplete();
        verify(tourRepository).findById(tour.getId());
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;
        when(tourRepository.findById(id))
                .thenReturn(Mono.justOrEmpty(Optional.empty()));
        Mono<Tour> result = tourService.getById(id);
        StepVerifier.create(result)
                .expectError(ResourceDoesNotExistException.class)
                .verify();
        verify(tourRepository).findById(id);
    }

    @Test
    void delete() {
        Long tourId = 1L;
        when(tourRepository.deleteById(tourId))
                .thenReturn(Mono.empty());
        Mono<Void> result = tourService.delete(tourId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(tourRepository).deleteById(tourId);
    }

    @Test
    void addImage() {
        Tour tour = generateTour();
        String fileName = "image.jpeg";
        when(tourRepository.findById(tour.getId()))
                .thenReturn(Mono.just(tour));
        when(tourRepository.save(tour))
                .thenReturn(Mono.just(tour));
        Mono<Void> result = tourService.addImage(tour.getId(), fileName);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(tourRepository).save(tour);
        assertEquals(tour.getImageUrls().size(), 1);
    }

    private Tour generateTour() {
        Long tourId = 1L;
        Tour tour = new Tour();
        tour.setId(tourId);
        tour.setImageUrls(new ArrayList<>());
        return tour;
    }

    private List<Tour> generateTours() {
        Tour tour1 = new Tour();
        Tour tour2 = new Tour();
        List<Tour> tours = List.of(tour1, tour2);
        return tours;
    }

}