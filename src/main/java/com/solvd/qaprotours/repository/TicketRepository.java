package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.user.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Lisov Ilya
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByUserIdAndTourId(Long userId, Long tourId);

    List<Ticket> findAllByUserId(Long userId);

    void deleteByUserIdAndTourId(Long userId, Long tourId);

}
