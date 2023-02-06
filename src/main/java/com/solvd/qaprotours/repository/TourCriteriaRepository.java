package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.Tour;
import com.solvd.qaprotours.domain.TourCriteria;

import java.util.List;

/**
 * @author Ermakovich Kseniya
 */
public interface TourCriteriaRepository {

    List<Tour> findByCriteria(TourCriteria tourCriteria);

}
