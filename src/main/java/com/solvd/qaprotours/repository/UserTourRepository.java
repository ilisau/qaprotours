package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.user.UserTour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Lisov Ilya
 */
public interface UserTourRepository extends JpaRepository<UserTour, Long> {

    Optional<UserTour> findByUserIdAndTourId(Long userId, Long tourId);

    List<UserTour> findAllByUserId(Long userId);

    List<UserTour> findAllByTourId(Long tourId);

    void deleteByUserIdAndTourId(Long userId, Long tourId);

}
