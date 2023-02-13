package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.InvalidTokenException;
import com.solvd.qaprotours.domain.exception.PasswordMismatchException;
import com.solvd.qaprotours.domain.exception.ResourceAlreadyExistsException;
import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.repository.UserRepository;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.UserService;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public void update(User user) {
        Optional<User> userWithSameEmail = userRepository.findByEmail(user.getEmail());
        if (userWithSameEmail.isPresent() && !Objects.equals(userWithSameEmail.get().getId(), user.getId())) {
            throw new ResourceAlreadyExistsException("user with email " + user.getEmail() + " already exists");
        }
        User oldUser = getById(user.getId());
        oldUser.setEmail(user.getEmail());
        oldUser.setName(user.getName());
        oldUser.setSurname(user.getSurname());
        userRepository.save(oldUser);
    }

    @Override
    public void updatePassword(Long userId, String newPassword) {
        User user = getById(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = getById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordMismatchException("old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void create(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("user with email " + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(false);
        userRepository.save(user);
        String token = jwtService.generateToken(JwtTokenType.ACTIVATION, user);
        //TODO send email with activation token
    }

    @Override
    @Transactional
    public void activate(JwtToken token) {
        if (!jwtService.validateToken(token.getToken())) {
            throw new InvalidTokenException("token is expired");
        }
        Long id = jwtService.retrieveUserId(token.getToken());
        User user = getById(id);
        user.setActivated(true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
