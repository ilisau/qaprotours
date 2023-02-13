package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.JwtToken;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
public interface AuthService {

    JwtResponse login(Authentication authentication);

    JwtResponse refresh(JwtToken jwtToken);

    void sendRestoreToken(String email);

    void restoreUserPassword(String token, String password);

}
