package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.User;

/**
 * @author Ermakovich Kseniya
 */
public interface UserService {

    User findByEmail(String email);

}
