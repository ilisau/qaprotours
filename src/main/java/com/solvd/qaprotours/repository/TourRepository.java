package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.tour.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Varya Petrova
 */
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    Page<Tour> findAll(Pageable pageable);

}
