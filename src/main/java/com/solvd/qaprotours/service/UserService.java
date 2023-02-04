package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.User;

public interface UserService {

    User findByEmail(String email);

}
