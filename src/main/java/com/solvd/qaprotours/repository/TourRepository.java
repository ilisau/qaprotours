package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.tour.Tour;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Varya Petrova
 */
@Repository
public interface TourRepository extends ReactiveCrudRepository<Tour, Long> {

}
