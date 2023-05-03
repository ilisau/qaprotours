package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.user.Ticket;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

/**
 * @author Lisov Ilya
 */
public interface TicketRepository extends ReactiveCrudRepository<Ticket, Long> {

    Flux<Ticket> findAllByUserId(String userId);

    Flux<Ticket> findAllByTourArrivalTimeIsBeforeAndStatus(
            LocalDateTime time, Ticket.Status status
    );

    Flux<Ticket> findAllByTourArrivalTimeIsAfterAndTourArrivalTimeIsBeforeAndStatus(
            LocalDateTime start, LocalDateTime end, Ticket.Status status
    );

}
