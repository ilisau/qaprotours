package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.config.TestConfig;
import com.solvd.qaprotours.domain.exception.NoFreePlacesException;
import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.repository.TicketRepository;
import com.solvd.qaprotours.service.TourService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class TicketServiceTests {

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private TourService tourService;

    @Autowired
    private TicketServiceImpl ticketService;

    @Test
    void getByExistingId() {
        Long id = 1L;
        Ticket ticket = new Ticket();
        Mockito.when(ticketRepository.findById(id))
                .thenReturn(Mono.just(ticket));
        Mono<Ticket> result = ticketService.getById(id);
        StepVerifier.create(result)
                .expectNext(ticket)
                .verifyComplete();
        Mockito.verify(ticketRepository).findById(id);
    }

    @Test
    void getByNotExistingId() {
        Long id = 1L;
        Mockito.when(ticketRepository.findById(id))
                .thenReturn(Mono.justOrEmpty(Optional.empty()));
        Mono<Ticket> result = ticketService.getById(id);
        StepVerifier.create(result)
                .expectError(ResourceDoesNotExistException.class)
                .verify();
        Mockito.verify(ticketRepository).findById(id);
    }

    @Test
    void getAllByUserId() {
        String userId = "1";
        List<Ticket> tickets = generateTickets();
        Mockito.when(ticketRepository.findAllByUserId(userId))
                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
        Flux<Ticket> result = ticketService.getAllByUserId(userId);
        StepVerifier.create(result)
                .expectNext(tickets.get(0))
                .expectNext(tickets.get(1))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getAllSoonTickets() {
        List<Ticket> tickets = generateTickets();
        Mockito.when(ticketRepository.findAllByTourArrivalTimeIsAfterAndTourArrivalTimeIsBeforeAndStatus(
                        ArgumentMatchers.any(),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(Ticket.Status.CONFIRMED)))
                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
        Flux<Ticket> result = ticketService.getAllSoonTickets();
        StepVerifier.create(result)
                .expectNext(tickets.get(0))
                .expectNext(tickets.get(1))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void getAllSoonNotConfirmedTickets() {
        List<Ticket> tickets = generateTickets();
        Mockito.when(ticketRepository.findAllByTourArrivalTimeIsBeforeAndStatus(
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(Ticket.Status.ORDERED)))
                .thenReturn(Flux.just(tickets.toArray(new Ticket[0])));
        Flux<Ticket> result = ticketService.getAllSoonNotConfirmedTickets();
        StepVerifier.create(result)
                .expectNext(tickets.get(0))
                .expectNext(tickets.get(1))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void createWithEnoughPlacesAmount() {
        String userId = "1";
        Long tourId = 1L;
        int clientsAmount = 3;
        int placesAmount = clientsAmount + 1;
        Ticket ticket = generateTicket(tourId, placesAmount, clientsAmount);
        Mockito.when(ticketRepository.save(ArgumentMatchers.any()))
                .thenReturn(Mono.just(ticket));
        Mockito.when(tourService.getById(tourId))
                .thenReturn(Mono.just(ticket.getTour()));
        Mockito.when(tourService.save(ticket.getTour()))
                .thenReturn(Mono.just(ticket.getTour()));
        Mono<Void> result = ticketService.create(userId, tourId, clientsAmount);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(ticketRepository).save(ArgumentMatchers.any());
    }

    @Test
    void createWithNotEnoughPlacesAmount() {
        String userId = "1";
        Long tourId = 1L;
        int clientsAmount = 3;
        int placesAmount = clientsAmount - 1;
        Ticket ticket = generateTicket(tourId, placesAmount, clientsAmount);
        Mockito.when(ticketRepository.save(ArgumentMatchers.any()))
                .thenReturn(Mono.just(ticket));
        Mockito.when(tourService.getById(tourId))
                .thenReturn(Mono.just(ticket.getTour()));
        Mockito.verify(ticketRepository, Mockito.never())
                .save(ArgumentMatchers.any());
        StepVerifier.create(ticketService.create(userId, tourId, clientsAmount))
                .expectError(NoFreePlacesException.class)
                .verify();
    }

    @Test
    void delete() {
        Long ticketId = 1L;
        Long tourId = 1L;
        int clientsAmount = 2;
        int placesAmount = 2;
        Ticket ticket = generateTicket(tourId, placesAmount, clientsAmount);
        Mockito.when(ticketRepository.findById(ticketId))
                .thenReturn(Mono.just(ticket));
        Mockito.when(ticketRepository.deleteById(ticketId))
                .thenReturn(Mono.empty());
        Mockito.when(tourService.save(ticket.getTour()))
                .thenReturn(Mono.just(ticket.getTour()));
        Mono<Void> result = ticketService.delete(ticketId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(ticketRepository).deleteById(ticketId);
        Mockito.verify(tourService).save(ArgumentMatchers.any());
    }

    @Test
    void confirm() {
        Long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        Mockito.when(ticketRepository.findById(ticketId))
                .thenReturn(Mono.just(ticket));
        Mockito.when(ticketRepository.save(ArgumentMatchers.any()))
                .thenReturn(Mono.just(ticket));
        Mono<Void> result = ticketService.confirm(ticketId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(ticketRepository).save(ticket);
    }

    @Test
    void cancel() {
        Long ticketId = 1L;
        Long tourId = 1L;
        int clientsAmount = 2;
        int placesAmount = 2;
        Ticket ticket = generateTicket(tourId, placesAmount, clientsAmount);
        Mockito.when(ticketRepository.findById(ticketId))
                .thenReturn(Mono.just(ticket));
        Mockito.when(ticketRepository.save(ArgumentMatchers.any()))
                .thenReturn(Mono.just(ticket));
        Mockito.when(tourService.save(ticket.getTour()))
                .thenReturn(Mono.just(ticket.getTour()));
        Mono<Void> result = ticketService.cancel(ticketId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(ticketRepository).save(ArgumentMatchers.any());
        Mockito.verify(tourService).save(ArgumentMatchers.any());
    }

    private List<Ticket> generateTickets() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        return List.of(ticket1, ticket2);
    }

    private Ticket generateTicket(Long tourId, int placesAmount, int clientsAmount) {
        Tour tour = new Tour();
        tour.setId(tourId);
        tour.setPlacesAmount(placesAmount);
        tour.setArrivalTime(LocalDateTime.now().plusDays(1));
        Ticket ticket = new Ticket();
        ticket.setClientsAmount(clientsAmount);
        ticket.setTour(tour);
        return ticket;
    }

}
