package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.NoFreePlacesException;
import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.exception.TourAlreadyStartedException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.repository.TicketRepository;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TourService tourService;

    @Override
    @Transactional(readOnly = true)
    public Mono<Ticket> getById(Long id) {
        Mono<Ticket> error = Mono.error(new ResourceDoesNotExistException("ticket not found"));
        return ticketRepository.findById(id)
                .switchIfEmpty(error);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Ticket> getAllByUserId(Long userId) {
        return ticketRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Ticket> getAllSoonTickets() {
        LocalDateTime startTime = LocalDateTime.of(
                LocalDate.now().plusDays(1),
                LocalTime.MIDNIGHT);
        LocalDateTime endTime = LocalDateTime.of(
                LocalDate.now().plusDays(2),
                LocalTime.MIDNIGHT);
        return ticketRepository.findAllByTourArrivalTimeIsAfterAndTourArrivalTimeIsBeforeAndStatus(startTime, endTime, Ticket.Status.CONFIRMED);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Ticket> getAllSoonNotConfirmedTickets() {
        LocalDateTime time = LocalDateTime.of(
                LocalDate.now().plusDays(4),
                LocalTime.MIDNIGHT);
        return ticketRepository.findAllByTourArrivalTimeIsBeforeAndStatus(time, Ticket.Status.ORDERED);
    }

    @Override
    @Transactional
    public Mono<Void> create(Long userId, Long tourId, Integer peopleAmount) {
        return tourService.getById(tourId)
                .onErrorResume(Mono::error)
                .flatMap(tour -> {
                    if (tour.getPlacesAmount() < peopleAmount) {
                        return Mono.error(new NoFreePlacesException("not enough places in tour"));
                    }
                    if (tour.getArrivalTime().isBefore(LocalDateTime.now())) {
                        return Mono.error(new TourAlreadyStartedException("tour already started"));
                    }
                    return Mono.just(tour);
                })
                .onErrorResume(Mono::error)
                .map(tour -> {
                    Ticket ticket = new Ticket();
                    ticket.setUserId(userId);
                    ticket.setTour(tour);
                    ticket.setOrderTime(LocalDateTime.now());
                    ticket.setStatus(Ticket.Status.ORDERED);
                    ticket.setClientsAmount(peopleAmount);
                    return ticket;
                })
                .flatMap(ticketRepository::save)
                .map(ticket -> {
                    Tour tour = ticket.getTour();
                    tour.setPlacesAmount(tour.getPlacesAmount() - peopleAmount);
                    return tour;
                })
                .flatMap(tourService::save)
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long ticketId) {
        Mono<Ticket> error = Mono.error(new ResourceDoesNotExistException("ticket not found"));
        return ticketRepository.findById(ticketId)
                .switchIfEmpty(error)
                .map(ticket -> {
                    int peopleAmount = ticket.getClientsAmount();
                    Tour tour = ticket.getTour();
                    if (tour.getArrivalTime().isAfter(LocalDateTime.now())) {
                        tour.setPlacesAmount(tour.getPlacesAmount() + peopleAmount);
                    }
                    return ticket;
                })
                .map(Ticket::getTour)
                .flatMap(tourService::save)
                .flatMap(tour -> ticketRepository.deleteById(ticketId))
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> confirm(Long ticketId) {
        Mono<Ticket> error = Mono.error(new ResourceDoesNotExistException("ticket not found"));
        return ticketRepository.findById(ticketId)
                .switchIfEmpty(error)
                .map(ticket -> {
                    ticket.setStatus(Ticket.Status.CONFIRMED);
                    return ticket;
                })
                .flatMap(ticketRepository::save)
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> cancel(Long ticketId) {
        Mono<Ticket> error = Mono.error(new ResourceDoesNotExistException("ticket not found"));
        return ticketRepository.findById(ticketId)
                .switchIfEmpty(error)
                .map(ticket -> {
                    int peopleAmount = ticket.getClientsAmount();
                    ticket.setStatus(Ticket.Status.CANCELED);
                    Tour tour = ticket.getTour();
                    if (tour.getArrivalTime().isAfter(LocalDateTime.now())) {
                        tour.setPlacesAmount(tour.getPlacesAmount() + peopleAmount);
                    }
                    return ticket;
                })
                .flatMap(ticketRepository::save)
                .map(Ticket::getTour)
                .flatMap(tourService::save)
                .then();
    }

}
