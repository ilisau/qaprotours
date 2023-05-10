package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.Ticket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Lisov Ilya
 */
public interface TicketService {

    /**
     * Get ticket by id.
     * @param id ticket id
     * @return ticket
     */
    Mono<Ticket> getById(Long id);

    /**
     * Get all tickets from user by id.
     * @param userId user id
     * @return tickets
     */
    Flux<Ticket> getAllByUserId(String userId);

    /**
     * Get all tickets that starts in 24-48 hours.
     * @return tickets
     */
    Flux<Ticket> getAllSoonTickets();

    /**
     * Get all tickets that are not confirmed before 4 days to start.
     * @return tickets
     */
    Flux<Ticket> getAllSoonNotConfirmedTickets();

    /**
     * Create ticket.
     *
     * @param userId       user id
     * @param tourId       tour id
     * @param peopleAmount people amount
     * @return empty response
     */
    Mono<Void> create(String userId, Long tourId, Integer peopleAmount);

    /**
     * Delete ticket.
     * @param ticketId ticket id
     * @return empty response
     */
    Mono<Void> delete(Long ticketId);

    /**
     * Confirm ticket.
     * @param ticketId ticket id
     * @return empty response
     */
    Mono<Void> confirm(Long ticketId);

    /**
     * Cancel ticket.
     * @param ticketId ticket id
     * @return empty response
     */
    Mono<Void> cancel(Long ticketId);

}
