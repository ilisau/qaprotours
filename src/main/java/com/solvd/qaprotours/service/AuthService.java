package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import reactor.core.publisher.Mono;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
public interface AuthService {

    /**
     * Authorizes user.
     * @param authentication user login data
     * @return pair of tokens
     */
    Mono<JwtResponse> login(Authentication authentication);

    /**
     * Refreshes tokens.
     * @param jwtToken refresh token
     * @return pair of tokens
     */
    Mono<JwtResponse> refresh(JwtToken jwtToken);

    /**
     * Sends restore token to user email.
     * @param email user email
     * @return empty response
     */
    Mono<Void> sendRestoreToken(String email);

    /**
     * Restores user password.
     *
     * @param token    restore token
     * @param password new password
     * @return empty response
     */
    Mono<Void> restoreUserPassword(String token, String password);

}
