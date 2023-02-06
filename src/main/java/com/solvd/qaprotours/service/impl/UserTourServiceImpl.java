package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.user.UserTour;
import com.solvd.qaprotours.service.UserTourService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lisov Ilya
 */
@Service
public class UserTourServiceImpl implements UserTourService {

    @Override
    public List<UserTour> getTours(Long userId) {
        return null;
    }

    @Override
    public void addTour(Long userId, Long tourId) {

    }

    @Override
    public void confirmTour(Long userId, Long tourId) {

    }

}
