package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.user.OrderTourStatus;
import com.solvd.qaprotours.domain.user.UserTour;
import com.solvd.qaprotours.domain.user.UserTourId;
import com.solvd.qaprotours.repository.UserTourRepository;
import com.solvd.qaprotours.service.TourService;
import com.solvd.qaprotours.service.UserService;
import com.solvd.qaprotours.service.UserTourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class UserTourServiceImpl implements UserTourService {

    private final UserTourRepository userTourRepository;
    private final UserService userService;
    private final TourService tourService;

    @Override
    @Transactional
    public List<UserTour> getTours(Long userId) {
        return userTourRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public void addTour(Long userId, Long tourId, Integer peopleAmount) {
        UserTour userTour = new UserTour();
        userTour.setId(new UserTourId(userId, tourId));
        userTour.setUser(userService.getById(userId));
        userTour.setTour(tourService.getById(tourId));
        userTour.setOrderTime(LocalDateTime.now());
        userTour.setStatus(OrderTourStatus.ORDERED);
        userTour.setClientsAmount(peopleAmount);
        userTourRepository.save(userTour);
    }

    @Override
    @Transactional
    public void deleteTour(Long userId, Long tourId) {
        userTourRepository.deleteByUserIdAndTourId(userId, tourId);
    }

    @Override
    @Transactional
    public void confirmTour(Long userId, Long tourId) {
        UserTour userTour = userTourRepository.findByUserIdAndTourId(userId, tourId)
                .orElseThrow(() -> new RuntimeException("UserTour not found"));
        userTour.setStatus(OrderTourStatus.CONFIRMED);
        userTourRepository.save(userTour);
    }

}
