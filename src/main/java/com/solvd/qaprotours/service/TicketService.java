package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.Ticket;

import java.util.List;

/**
 * @author Lisov Ilya
 */
public interface TicketService {

    Ticket getById(Long id);

    List<Ticket> getAllByUserId(Long userId);

    List<Ticket> findAllSoonTickets();

    List<Ticket> findAllSoonNotConfirmedTickets();

    void add(Long userId, Long tourId, Integer peopleAmount);

    void delete(Long ticketId);

    void confirm(Long ticketId);

    void cancel(Long ticketId);

}
