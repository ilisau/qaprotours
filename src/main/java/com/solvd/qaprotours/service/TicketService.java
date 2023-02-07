package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.Ticket;

import java.util.List;

/**
 * @author Lisov Ilya
 */
public interface TicketService {

    Ticket getById(Long id);

    List<Ticket> getTickets(Long userId);

    void addTicket(Long userId, Long tourId, Integer peopleAmount);

    void deleteTicket(Long userId, Long tourId);

    void confirmTicket(Long userId, Long tourId);

}
