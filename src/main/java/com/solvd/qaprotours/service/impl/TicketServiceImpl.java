package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.user.Ticket;
import com.solvd.qaprotours.domain.user.Status;
import com.solvd.qaprotours.repository.TicketRepository;
import com.solvd.qaprotours.service.TicketService;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final TourService tourService;

    @Override
    @Transactional
    public Ticket getById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("ticket not found"));
    }

    @Override
    @Transactional
    public List<Ticket> getTickets(Long userId) {
        return ticketRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void addTicket(Long userId, Long tourId, Integer peopleAmount) {
        Ticket ticket = new Ticket();
        ticket.setUser(userService.getById(userId));
        ticket.setTour(tourService.getById(tourId));
        ticket.setOrderTime(LocalDateTime.now());
        ticket.setStatus(Status.ORDERED);
        ticket.setClientsAmount(peopleAmount);
        ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void deleteTicket(Long userId, Long tourId) {
        ticketRepository.deleteByUserIdAndTourId(userId, tourId);
    }

    @Override
    @Transactional
    public void confirmTicket(Long userId, Long tourId) {
        Ticket ticket = ticketRepository.findByUserIdAndTourId(userId, tourId)
                .orElseThrow(() -> new ResourceDoesNotExistException("ticket not found"));
        ticket.setStatus(Status.CONFIRMED);
        ticketRepository.save(ticket);
    }

}
