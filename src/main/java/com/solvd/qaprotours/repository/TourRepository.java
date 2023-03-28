package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.tour.Tour;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 * @author Varya Petrova
 */
public interface TourRepository extends ReactiveCrudRepository<Tour, Long> {

    Flux<Tour> findAll(Sort sort);

}
