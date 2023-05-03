package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.AuthException;
import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.kafka.MessageSender;
import com.solvd.qaprotours.web.mapper.MailDataMapper;
import com.solvd.qaprotours.web.mapper.UserMapper;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private UserClient userClient;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private MailDataMapper mailDataMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void loginWithCorrectPassword() {
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
        Authentication authentication = new Authentication();
        authentication.setEmail("email@example.com");
        authentication.setPassword(password);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName(userName);
        userDto.setSurname(userSurname);
        userDto.setEmail(userEmail);
        when(userClient.getByEmail(authentication.getEmail()))
                .thenReturn(Mono.just(userDto));
        when(userMapper.toEntity(userDto))
                .thenReturn(user);
        when(passwordEncoder.matches(eq(authentication.getPassword()), anyString()))
                .thenReturn(true);
        String accessToken = "Access token";
        String refreshToken = "Refresh token";
        when(jwtService.generateToken(JwtTokenType.ACCESS, user))
                .thenReturn(accessToken);
        when(jwtService.generateToken(JwtTokenType.REFRESH, user))
                .thenReturn(refreshToken);
        JwtResponse response = new JwtResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        Mono<JwtResponse> result = authService.login(authentication);
        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
        verify(jwtService).generateToken(JwtTokenType.ACCESS, user);
        verify(jwtService).generateToken(JwtTokenType.REFRESH, user);
        verify(passwordEncoder).matches(eq(authentication.getPassword()), anyString());
    }

    @Test
    void loginWithIncorrectPassword() {
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
        Authentication authentication = new Authentication();
        authentication.setEmail("email@example.com");
        authentication.setPassword(password);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName(userName);
        userDto.setSurname(userSurname);
        userDto.setEmail(userEmail);
        when(userClient.getByEmail(authentication.getEmail()))
                .thenReturn(Mono.just(userDto));
        when(userMapper.toEntity(userDto))
                .thenReturn(user);
        when(passwordEncoder.matches(eq(authentication.getPassword()), anyString()))
                .thenReturn(false);
        Mono<JwtResponse> result = authService.login(authentication);
        StepVerifier.create(result)
                .expectError(AuthException.class)
                .verify();
        verify(jwtService, never()).generateToken(JwtTokenType.ACCESS, user);
        verify(jwtService, never()).generateToken(JwtTokenType.REFRESH, user);
        verify(passwordEncoder).matches(eq(authentication.getPassword()), anyString());
    }

    @Test
    void refreshWithCorrectToken() {
        String userId = "1";
        String userEmail = "email@example.com";
        JwtToken token = new JwtToken();
        token.setToken("Token");
        String accessToken = "Access token";
        String refreshToken = "Refresh token";
        JwtResponse response = new JwtResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        Claims claims = new DefaultClaims();
        claims.put("id", userId);
        claims.setSubject(userEmail);
        User user = new User();
        user.setId(userId);
        user.setEmail(userEmail);
        UserDto userDto = new UserDto();
        userDto.setEmail(userEmail);
        when(jwtService.parse(token.getToken()))
                .thenReturn(claims);
        when(jwtService.isTokenType(token.getToken(), JwtTokenType.REFRESH))
                .thenReturn(true);
        when(userClient.getByEmail(userEmail))
                .thenReturn(Mono.just(userDto));
        when(userMapper.toEntity(userDto))
                .thenReturn(user);
        when(jwtService.generateToken(JwtTokenType.ACCESS, user))
                .thenReturn(accessToken);
        when(jwtService.generateToken(JwtTokenType.REFRESH, user))
                .thenReturn(refreshToken);
        Mono<JwtResponse> result = authService.refresh(token);
        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
        verify(jwtService).generateToken(JwtTokenType.ACCESS, user);
        verify(jwtService).generateToken(JwtTokenType.REFRESH, user);
    }

    @Test
    void sendRestoreToken() {
        String userId = "1";
        String userName = "Mike";
        String userSurname = "Ivanov";
        String userEmail = "mike@example.com";
        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setSurname(userSurname);
        user.setEmail(userEmail);
        UserDto userDto = new UserDto();
        userDto.setName(userName);
        userDto.setSurname(userSurname);
        userDto.setEmail(userEmail);
        when(userClient.getByEmail(userEmail))
                .thenReturn(Mono.just(userDto));
        when(userMapper.toEntity(userDto))
                .thenReturn(user);
        when(jwtService.generateToken(JwtTokenType.RESET, user))
                .thenReturn("RESET TOKEN");
        when(messageSender.sendMessage(eq("mail"), anyInt(), eq(userId), any()))
                .thenReturn(Flux.empty());
        Mono<Void> result = authService.sendRestoreToken(userEmail);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(jwtService).generateToken(JwtTokenType.RESET, user);
    }

    @Test
    void restoreUserPasswordWithCorrectToken() {
        String userId = "1";
        String token = "token";
        String password = "password";
        when(jwtService.validateToken(token))
                .thenReturn(true);
        Claims claims = new DefaultClaims();
        claims.put("id", userId);
        when(jwtService.parse(token))
                .thenReturn(claims);
        when(jwtService.isTokenType(token, JwtTokenType.RESET))
                .thenReturn(true);
        when(userClient.updatePassword(userId, password))
                .thenReturn(Mono.empty());
        Mono<Void> result = authService.restoreUserPassword(token, password);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        verify(userClient).updatePassword(userId, password);
    }

    @Test
    void restoreUserPasswordWithInсorrectToken() {
        String userId = "1";
        String token = "token";
        String password = "password";
        when(jwtService.validateToken(token))
                .thenReturn(true);
        Claims claims = new DefaultClaims();
        claims.put("id", userId);
        when(jwtService.parse(token))
                .thenReturn(claims);
        when(jwtService.isTokenType(token, JwtTokenType.RESET))
                .thenReturn(false);
        Mono<Void> result = authService.restoreUserPassword(token, password);
        StepVerifier.create(result)
                .expectError()
                .verify();
        verify(userClient, never()).updatePassword(userId, password);
    }

    @Test
    void restoreUserPasswordWithExpiredToken() {
        String userId = "1";
        String token = "token";
        String password = "password";
        when(jwtService.validateToken(token))
                .thenReturn(false);
        Mono<Void> result = authService.restoreUserPassword(token, password);
        StepVerifier.create(result)
                .expectError()
                .verify();
        verify(userClient, never()).updatePassword(userId, password);
    }

}
