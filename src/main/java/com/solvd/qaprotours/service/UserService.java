package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.domain.user.UserTour;

import java.util.List;

/**
 * @author Ermakovich Kseniya
 */
public interface UserService {

    User getById(Long id);

    User getByEmail(String email);

    void update(User user);

    void create(User user);

    void delete(Long id);

    List<UserTour> getTours(Long userId);

    void addTour(Long userId, Long tourId);

    void confirmTour(Long userId, Long tourId);

}
