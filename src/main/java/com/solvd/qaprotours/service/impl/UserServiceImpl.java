package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.ResourceAlreadyExistsException;
import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.exception.TokenExpiredException;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.repository.UserRepository;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("user with id " + id + " does not exist"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceDoesNotExistException("user with email " + email + " does not exist"));
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void create(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("user with email " + user.getEmail() + " already exists");
        }
        user.setActivated(false);
        userRepository.save(user);
    }

    @Override
    public void activate(String token) {
        if (!jwtService.validateToken(token)) {
            throw new TokenExpiredException("token is expired");
        }
        Long id = jwtService.retrieveUserId(token);
        User user = getById(id);
        user.setActivated(true);
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
