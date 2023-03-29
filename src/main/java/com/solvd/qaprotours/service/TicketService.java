package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.Ticket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Lisov Ilya
 */
public interface TicketService {

    Mono<Ticket> getById(Long id);

    Flux<Ticket> getAllByUserId(String userId);

    Flux<Ticket> getAllSoonTickets();

    Flux<Ticket> getAllSoonNotConfirmedTickets();

    Mono<Void> create(String userId, Long tourId, Integer peopleAmount);

    Mono<Void> delete(Long ticketId);

    Mono<Void> confirm(Long ticketId);

    Mono<Void> cancel(Long ticketId);

}
