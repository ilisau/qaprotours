package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.user.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Lisov Ilya
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByUserId(String userId);

    List<Ticket> findAllByTourArrivalTimeIsBeforeAndStatus(LocalDateTime time, Ticket.Status status);

    List<Ticket> findAllByTourArrivalTimeIsAfterAndTourArrivalTimeIsBeforeAndStatus(LocalDateTime start, LocalDateTime end, Ticket.Status status);

}
