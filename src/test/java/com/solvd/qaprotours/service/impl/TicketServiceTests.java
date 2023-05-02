package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.NoFreePlacesException;
import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.repository.TicketRepository;
import com.solvd.qaprotours.service.TourService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTests {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TourService tourService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void getByExistingId() {
        Long id = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(id);
        when(ticketRepository.findById(id))
                .thenReturn(Mono.just(ticket));
        Mono<Ticket> result = ticketService.getById(id);
        StepVerifier.create(result)
                .expectNext(ticket)
                .verifyComplete();
        verify(ticketRepository).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;
        when(ticketRepository.findById(id))
                .thenReturn(Mono.justOrEmpty(Optional.empty()));
        Mono<Ticket> result = ticketService.getById(id);
        StepVerifier.create(result)
                .expectError(ResourceDoesNotExistException.class)
                .verify();
        verify(ticketRepository).findById(id);
    }

    @Test
    void getAllByUserId() {
        String userId = "1";
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        List<Ticket> tickets = List.of(ticket1, ticket2);
        when(ticketRepository.findAllByUserId(userId))
                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
        Flux<Ticket> result = ticketService.getAllByUserId(userId);
        StepVerifier.create(result)
                .expectNext(ticket1)
                .expectNext(ticket2)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getAllSoonTickets() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        List<Ticket> tickets = List.of(ticket1, ticket2);
        when(ticketRepository.findAllByTourArrivalTimeIsAfterAndTourArrivalTimeIsBeforeAndStatus(
                any(),
                any(),
                eq(Ticket.Status.CONFIRMED)))
                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
        Flux<Ticket> result = ticketService.getAllSoonTickets();
        StepVerifier.create(result)
                .expectNext(ticket1)
                .expectNext(ticket2)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getAllSoonNotConfirmedTickets() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        List<Ticket> tickets = List.of(ticket1, ticket2);
        when(ticketRepository.findAllByTourArrivalTimeIsBeforeAndStatus(
                any(),
                eq(Ticket.Status.ORDERED)))
                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
        Flux<Ticket> result = ticketService.getAllSoonNotConfirmedTickets();
        StepVerifier.create(result)
                .expectNext(ticket1)
                .expectNext(ticket2)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void createWithEnoughPlacesAmount() {
        String userId = "1";
        Long tourId = 1L;
        Integer peopleAmount = 1;
        Tour tour = new Tour();
        tour.setId(tourId);
        tour.setPlacesAmount(5);
        tour.setArrivalTime(LocalDateTime.now().plusDays(1));
        Ticket ticket = new Ticket();
        ticket.setTour(tour);
        when(tourService.getById(tourId))
                .thenReturn(Mono.just(tour));
        when(ticketRepository.save(any()))
                .thenReturn(Mono.just(ticket));
        when(tourService.save(any()))
                .thenReturn(Mono.empty());
        Mono<Void> result = ticketService.create(userId, tourId, peopleAmount);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(ticketRepository).save(any());
    }

    @Test
    void createWithNotEnoughPlacesAmount() {
        String userId = "1";
        Long tourId = 1L;
        Integer peopleAmount = 2;
        Tour tour = new Tour();
        tour.setId(tourId);
        tour.setPlacesAmount(1);
        tour.setArrivalTime(LocalDateTime.now().plusDays(1));
        Ticket ticket = new Ticket();
        ticket.setTour(tour);
        when(tourService.getById(tourId))
                .thenReturn(Mono.just(tour));
        verify(ticketRepository, never()).save(any());
        StepVerifier.create(ticketService.create(userId, tourId, peopleAmount))
                .expectError(NoFreePlacesException.class)
                .verify();
    }

    @Test
    void delete() {
        Long ticketId = 1L;
        int clientsAmount = 2;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setClientsAmount(clientsAmount);
        Tour tour = new Tour();
        tour.setArrivalTime(LocalDateTime.now().plusDays(1));
        tour.setPlacesAmount(2);
        ticket.setTour(tour);
        when(ticketRepository.findById(ticketId))
                .thenReturn(Mono.just(ticket));
        when(tourService.save(any()))
                .thenReturn(Mono.just(tour));
        when(ticketRepository.deleteById(ticketId))
                .thenReturn(Mono.empty());
        Mono<Void> result = ticketService.delete(ticketId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(ticketRepository).deleteById(ticketId);
        verify(tourService).save(any());
    }

    @Test
    void confirm() {
        Long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        when(ticketRepository.findById(ticketId))
                .thenReturn(Mono.just(ticket));
        when(ticketRepository.save(any()))
                .thenReturn(Mono.just(ticket));
        ticket.setStatus(Ticket.Status.CONFIRMED);
        Mono<Void> result = ticketService.confirm(ticketId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(ticketRepository).save(ticket);
    }

    @Test
    void cancel() {
        Long ticketId = 1L;
        int clientsAmount = 2;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setClientsAmount(clientsAmount);
        Tour tour = new Tour();
        tour.setArrivalTime(LocalDateTime.now().plusDays(1));
        tour.setPlacesAmount(2);
        ticket.setTour(tour);
        when(ticketRepository.findById(ticketId))
                .thenReturn(Mono.just(ticket));
        when(tourService.save(any()))
                .thenReturn(Mono.just(tour));
        when(ticketRepository.save(any()))
                .thenReturn(Mono.just(ticket));
        Mono<Void> result = ticketService.cancel(ticketId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(ticketRepository).save(any());
        verify(tourService).save(any());
    }

}
