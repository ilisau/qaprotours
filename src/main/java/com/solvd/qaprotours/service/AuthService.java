package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.Refresh;

/**
 * @author Ermakovich Kseniya
 */
public interface AuthService {

    JwtResponse login(Authentication authentication);

    JwtResponse refresh(Refresh refresh);

}
