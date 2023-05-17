package com.solvd.qaprotours.service.impl.fake;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.service.AuthService;
import reactor.core.publisher.Mono;

public class FakeAuthService implements AuthService {

    @Override
    public Mono<JwtResponse> login(final Authentication authentication) {
        JwtResponse response = generateResponse();
        return Mono.just(response);
    }

    @Override
    public Mono<JwtResponse> refresh(final JwtToken jwtToken) {
        JwtResponse response = generateResponse();
        return Mono.just(response);
    }

    @Override
    public Mono<Void> sendRestoreToken(final String email) {
        return Mono.empty();
    }

    @Override
    public Mono<Void> restoreUserPassword(final String token,
                                          final String password) {
        return Mono.empty();
    }

    private JwtResponse generateResponse() {
        String accessToken = "Access token";
        String refreshToken = "Refresh token";
        JwtResponse response = new JwtResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        return response;
    }

}
