package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.User;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
public interface UserService {

    User getById(Long id);

    User getByEmail(String email);

    void update(User user);

    void updatePassword(Long userId, String newPassword);

    void updatePassword(Long userId, String oldPassword, String newPassword);

    void create(User user);

    void activate(JwtToken token);

    void delete(Long id);

}
