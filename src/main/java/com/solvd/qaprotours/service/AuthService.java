package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import reactor.core.publisher.Mono;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
public interface AuthService {

    Mono<JwtResponse> login(Authentication authentication);

    Mono<JwtResponse> refresh(JwtToken jwtToken);

    Mono<Void> sendRestoreToken(String email);

    Mono<Void> restoreUserPassword(String token, String password);

}
