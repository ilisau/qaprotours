package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtRefresh;
import com.solvd.qaprotours.domain.jwt.JwtResponse;

/**
 * @author Ermakovich Kseniya
 */
public interface AuthService {

    JwtResponse login(Authentication authentication);

    JwtResponse refresh(JwtRefresh jwtRefresh);

}
