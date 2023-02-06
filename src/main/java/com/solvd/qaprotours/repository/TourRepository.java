package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ermakovich Kseniya
 */
@Repository
public interface TourRepository extends JpaRepository<Tour, Long>, TourCriteriaRepository {


}
