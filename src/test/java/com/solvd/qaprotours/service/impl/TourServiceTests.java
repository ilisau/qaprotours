package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.config.TestConfig;
import com.solvd.qaprotours.config.kafka.MessageSender;
import com.solvd.qaprotours.domain.Pagination;
import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.repository.TicketRepository;
import com.solvd.qaprotours.repository.TourRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class TourServiceTests {

    @MockBean
    private MessageSender messageSender;

    @MockBean
    private TourRepository tourRepository;

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TourServiceImpl tourService;

    @Test
    void getAll() {
        Integer currentPage = 0;
        Integer pageSize = 5;
        TourCriteria tourCriteria = null;
        List<Tour> tours = generateTours();
        Mockito.when(tourRepository.findAll(ArgumentMatchers.any()))
                .thenReturn(Flux.just(tours.toArray(new Tour[0])));
        Pagination pagination = new Pagination(currentPage, pageSize);
        Flux<Tour> result = tourService.getAll(pagination, tourCriteria);
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
        Mockito.when(tourRepository.findAll(ArgumentMatchers.any()))
                .thenReturn(Flux.just(tours.toArray(new Tour[0])));
        Pagination pagination = new Pagination(currentPage, pageSize);
        Flux<Tour> result = tourService.getAll(pagination, tourCriteria);
        StepVerifier.create(result)
                .expectNext(tours.get(0))
                .expectNext(tours.get(1))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void save() {
        Tour tour = generateTour();
        Mockito.when(tourRepository.save(tour))
                .thenReturn(Mono.just(tour));
        Mockito.when(messageSender.sendMessage(ArgumentMatchers.any()))
                .thenReturn(Flux.empty());
        Mono<Tour> result = tourService.save(tour);
        StepVerifier.create(result)
                .expectNext(tour)
                .verifyComplete();
        Mockito.verify(tourRepository).save(tour);
        Mockito.verify(messageSender)
                .sendMessage(ArgumentMatchers.any());
        Assertions.assertTrue(tour.isDraft());
    }

    @Test
    void publish() {
        Tour tour = generateTour();
        Mockito.when(tourRepository.save(tour))
                .thenReturn(Mono.just(tour));
        Mockito.when(messageSender.sendMessage(ArgumentMatchers.any()))
                .thenReturn(Flux.empty());
        Mono<Tour> result = tourService.publish(tour);
        StepVerifier.create(result)
                .expectNext(tour)
                .verifyComplete();
        Mockito.verify(tourRepository).save(tour);
        Mockito.verify(messageSender)
                .sendMessage(ArgumentMatchers.any());
        Assertions.assertFalse(tour.isDraft());
    }

    @Test
    void getByExistingId() {
        Tour tour = generateTour();
        Mockito.when(tourRepository.findById(tour.getId()))
                .thenReturn(Mono.just(tour));
        Mono<Tour> result = tourService.getById(tour.getId());
        StepVerifier.create(result)
                .expectNext(tour)
                .verifyComplete();
        Mockito.verify(tourRepository).findById(tour.getId());
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;
        Mockito.when(tourRepository.findById(id))
                .thenReturn(Mono.justOrEmpty(Optional.empty()));
        Mono<Tour> result = tourService.getById(id);
        StepVerifier.create(result)
                .expectError(ResourceDoesNotExistException.class)
                .verify();
        Mockito.verify(tourRepository).findById(id);
    }

    @Test
    void delete() {
        Long tourId = 1L;
        Mockito.when(tourRepository.deleteById(tourId))
                .thenReturn(Mono.empty());
        Mockito.when(messageSender.sendMessage(ArgumentMatchers.any()))
                .thenReturn(Flux.just(Mockito.mock(SenderResult.class)));
        Mono<Void> result = tourService.delete(tourId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(tourRepository).deleteById(tourId);
        Mockito.verify(messageSender)
                .sendMessage(ArgumentMatchers.any());
    }

    @Test
    void addImage() {
        Tour tour = generateTour();
        String fileName = "image.jpeg";
        Mockito.when(tourRepository.findById(tour.getId()))
                .thenReturn(Mono.just(tour));
        Mockito.when(tourRepository.save(tour))
                .thenReturn(Mono.just(tour));
        Mockito.when(messageSender.sendMessage(ArgumentMatchers.any()))
                .thenReturn(Flux.empty());
        Mono<Void> result = tourService.addImage(tour.getId(), fileName);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(tourRepository).save(tour);
        Mockito.verify(messageSender)
                .sendMessage(ArgumentMatchers.any());
        Assertions.assertEquals(tour.getImageUrls().size(), 1);
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