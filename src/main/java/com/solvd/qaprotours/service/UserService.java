package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.User;

/**
 * @author Ermakovich Kseniya
 */
public interface UserService {

    User getById(Long id);

    User getByEmail(String email);

    void update(User user);

    void create(User user);

    void delete(Long id);

}
