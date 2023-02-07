package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.UserTour;

import java.util.List;

/**
 * @author Lisov Ilya
 */
public interface UserTourService {

    List<UserTour> getTours(Long userId);

    void addTour(Long userId, Long tourId, Integer peopleAmount);

    void deleteTour(Long userId, Long tourId);

    void confirmTour(Long userId, Long tourId);

}
