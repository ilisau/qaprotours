package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.tour.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Varya Petrova
 */
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findAllByArrivalTimeIsAfter(LocalDateTime arrivalTime);

}
