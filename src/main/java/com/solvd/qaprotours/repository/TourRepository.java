package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lisov Ilya
 */
public interface TourRepository extends JpaRepository<Tour, Long> {


}
