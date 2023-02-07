package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    void deleteById(Long id);

}
