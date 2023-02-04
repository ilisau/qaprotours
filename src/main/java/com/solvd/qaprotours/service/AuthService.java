package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.JwtRefresh;

/**
 * @author Ermakovich Kseniya
 */
public interface AuthService {

    JwtResponse login(Authentication authentication);

    JwtResponse refresh(JwtRefresh jwtRefresh);

}
