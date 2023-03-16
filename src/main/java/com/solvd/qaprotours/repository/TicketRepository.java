package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.user.Ticket;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

/**
 * @author Lisov Ilya
 */
@Repository
public interface TicketRepository extends ReactiveCrudRepository<Ticket, Long> {

    Flux<Ticket> findAllByUserId(Long userId);

    Flux<Ticket> findAllByTourArrivalTimeIsBeforeAndStatus(LocalDateTime time, Ticket.Status status);

    Flux<Ticket> findAllByTourArrivalTimeIsAfterAndTourArrivalTimeIsBeforeAndStatus(LocalDateTime start, LocalDateTime end, Ticket.Status status);

}
