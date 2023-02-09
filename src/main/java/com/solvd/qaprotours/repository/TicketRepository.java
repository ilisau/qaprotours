package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.user.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Lisov Ilya
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByUserId(Long userId);

}
