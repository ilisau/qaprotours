package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.config.TestConfig;
import com.solvd.qaprotours.domain.exception.AuthException;
import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.kafka.MessageSender;
import com.solvd.qaprotours.web.mapper.MailDataMapper;
import com.solvd.qaprotours.web.mapper.UserMapper;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private UserClient userClient;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MailDataMapper mailDataMapper;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void loginWithCorrectPassword() {
        User user = generateUser();
        Authentication authentication = generateAuthentication(user);
        JwtResponse response = generateResponse();
        Mockito.when(passwordEncoder.matches(
                        ArgumentMatchers.eq(authentication.getPassword()),
                        ArgumentMatchers.anyString()))
                .thenReturn(true);
        Mono<JwtResponse> result = authService.login(authentication);
        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
        Mockito.verify(jwtService).generateToken(JwtTokenType.ACCESS, user);
        Mockito.verify(jwtService).generateToken(JwtTokenType.REFRESH, user);
        Mockito.verify(passwordEncoder).matches(
                ArgumentMatchers.eq(authentication.getPassword()),
                ArgumentMatchers.anyString());
    }

    @Test
    void loginWithIncorrectPassword() {
        User user = generateUser();
        Authentication authentication = generateAuthentication(user);
        Mockito.when(passwordEncoder.matches(
                        ArgumentMatchers.eq(authentication.getPassword()),
                        ArgumentMatchers.anyString()))
                .thenReturn(false);
        Mono<JwtResponse> result = authService.login(authentication);
        StepVerifier.create(result)
                .expectError(AuthException.class)
                .verify();
        Mockito.verify(jwtService, Mockito.never())
                .generateToken(JwtTokenType.ACCESS, user);
        Mockito.verify(jwtService, Mockito.never())
                .generateToken(JwtTokenType.REFRESH, user);
        Mockito.verify(passwordEncoder).matches(
                ArgumentMatchers.eq(authentication.getPassword()),
                ArgumentMatchers.anyString());
    }

    @Test
    void refreshWithCorrectToken() {
        User user = generateUser();
        JwtToken token = new JwtToken();
        token.setToken("Token");
        JwtResponse response = generateResponse();
        Mono<JwtResponse> result = authService.refresh(token);
        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
        Mockito.verify(jwtService).generateToken(JwtTokenType.ACCESS, user);
        Mockito.verify(jwtService).generateToken(JwtTokenType.REFRESH, user);
    }

    @Test
    void sendRestoreToken() {
        User user = generateUser();
        Mockito.when(messageSender.sendMessage(ArgumentMatchers.eq("mail"),
                        ArgumentMatchers.anyInt(),
                        ArgumentMatchers.eq(user.getId()),
                        ArgumentMatchers.any()))
                .thenReturn(Flux.empty());
        Mono<Void> result = authService.sendRestoreToken(user.getEmail());
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(jwtService).generateToken(JwtTokenType.RESET, user);
    }

    @Test
    void restoreUserPasswordWithCorrectToken() {
        String userId = "1";
        String token = "token";
        String password = "password";
        Mono<Void> result = authService.restoreUserPassword(token, password);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(userClient).updatePassword(userId, password);
    }

    @Test
    void restoreUserPasswordWithIncorrectToken() {
        String userId = "1";
        String token = "token";
        String password = "password";
        Mockito.when(jwtService.isTokenType(token, JwtTokenType.RESET))
                .thenReturn(false);
        Mono<Void> result = authService.restoreUserPassword(token, password);
        StepVerifier.create(result)
                .expectError()
                .verify();
        Mockito.verify(userClient, Mockito.never())
                .updatePassword(userId, password);
    }

    @Test
    void restoreUserPasswordWithExpiredToken() {
        String userId = "1";
        String token = "token";
        String password = "password";
        Mockito.when(jwtService.validateToken(token))
                .thenReturn(false);
        Mono<Void> result = authService.restoreUserPassword(token, password);
        StepVerifier.create(result)
                .expectError()
                .verify();
        Mockito.verify(userClient, Mockito.never())
                .updatePassword(userId, password);
    }

    private User generateUser() {
        String userId = "1";
        String userName = "Mike";
        String userSurname = "Ivanov";
        String userEmail = "mike@example.com";
        String password = "12345678";
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setSurname(userSurname);
        user.setEmail(userEmail);
        user.setActivated(true);
        user.setPassword(password);
        return user;
    }

    private Authentication generateAuthentication(User user) {
        Authentication authentication = new Authentication();
        authentication.setEmail("email@example.com");
        authentication.setPassword(user.getPassword());
        return authentication;
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
