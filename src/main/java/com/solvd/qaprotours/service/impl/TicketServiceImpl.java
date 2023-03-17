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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    public Ticket getById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("ticket not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getAllByUserId(Long userId) {
        return ticketRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getAllSoonTickets() {
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
    public List<Ticket> getAllSoonNotConfirmedTickets() {
        LocalDateTime time = LocalDateTime.of(
                LocalDate.now().plusDays(4),
                LocalTime.MIDNIGHT);
        return ticketRepository.findAllByTourArrivalTimeIsBeforeAndStatus(time, Ticket.Status.ORDERED);
    }

    @Override
    @Transactional
    public void create(Long userId, Long tourId, Integer peopleAmount) {
        Tour tour = tourService.getById(tourId);
        if (tour.getPlacesAmount() < peopleAmount) {
            throw new NoFreePlacesException("not enough places in tour");
        }
        if (tour.getArrivalTime().isBefore(LocalDateTime.now())) {
            throw new TourAlreadyStartedException("tour already started");
        }

        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setTour(tourService.getById(tourId));
        ticket.setOrderTime(LocalDateTime.now());
        ticket.setStatus(Ticket.Status.ORDERED);
        ticket.setClientsAmount(peopleAmount);
        ticketRepository.save(ticket);

        tour.setPlacesAmount(tour.getPlacesAmount() - peopleAmount);
        tourService.save(tour);
    }

    @Override
    @Transactional
    public void delete(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceDoesNotExistException("ticket not found"));
        int peopleAmount = ticket.getClientsAmount();
        Tour tour = tourService.getById(ticket.getTour().getId());
        if (tour.getArrivalTime().isAfter(LocalDateTime.now())) {
            tour.setPlacesAmount(tour.getPlacesAmount() + peopleAmount);
        }
        tourService.save(tour);
        ticketRepository.deleteById(ticketId);
    }

    @Override
    @Transactional
    public void confirm(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceDoesNotExistException("ticket not found"));
        ticket.setStatus(Ticket.Status.CONFIRMED);
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void cancel(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceDoesNotExistException("ticket not found"));
        int peopleAmount = ticket.getClientsAmount();
        Tour tour = tourService.getById(ticket.getTour().getId());
        if (tour.getArrivalTime().isAfter(LocalDateTime.now())) {
            tour.setPlacesAmount(tour.getPlacesAmount() + peopleAmount);
        }
        tourService.save(tour);
        ticket.setStatus(Ticket.Status.CANCELED);
        ticketRepository.save(ticket);
    }

}
