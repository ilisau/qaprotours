package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.tour.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Varya Petrova
 */
@Repository
public interface TourRepository extends PagingAndSortingRepository<Tour, Long> {

    Page<Tour> findAll(Pageable pageable);

}
