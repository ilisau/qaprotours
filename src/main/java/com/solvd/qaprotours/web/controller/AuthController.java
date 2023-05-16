package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.AuthService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.jwt.AuthenticationDto;
import com.solvd.qaprotours.web.dto.jwt.JwtResponseDto;
import com.solvd.qaprotours.web.dto.jwt.JwtTokenDto;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.mapper.UserMapper;
import com.solvd.qaprotours.web.mapper.jwt.AuthenticationMapper;
import com.solvd.qaprotours.web.mapper.jwt.JwtResponseMapper;
import com.solvd.qaprotours.web.mapper.jwt.JwtTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserClient userClient;
    private final JwtResponseMapper jwtResponseMapper;
    private final JwtTokenMapper jwtTokenMapper;
    private final AuthenticationMapper authenticationMapper;
    private final UserMapper userMapper;

    /**
     * Generate a pair of tokens for user by its email and password.
     * @param authenticationDto user's email and password
     * @return pair of tokens
     */
    @PostMapping("/login")
    public Mono<JwtResponseDto> login(
            @Validated @RequestBody final AuthenticationDto authenticationDto
    ) {
        Authentication authentication = authenticationMapper
                .toEntity(authenticationDto);
        return authService.login(authentication)
                .map(jwtResponseMapper::toDto);
    }

    /**
     * Register a new user.
     * @param userDto user's data
     * @return empty response
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> register(
            @Validated(OnCreate.class) @RequestBody final UserDto userDto
    ) {
        User user = userMapper.toEntity(userDto);
        user.setRole(User.Role.CLIENT);
        return userClient.create(user);
    }

    /**
     * Confirm user's account.
     * @param jwtTokenDto token
     * @return empty response
     */
    @PostMapping("/register/confirm")
    public Mono<Void> confirm(
            @Validated @RequestBody final JwtTokenDto jwtTokenDto
    ) {
        JwtToken jwtToken = jwtTokenMapper.toEntity(jwtTokenDto);
        return userClient.activate(jwtToken);
    }

    /**
     * Refresh user's tokens.
     * @param jwtTokenDto refresh token
     * @return pair of tokens
     */
    @PostMapping("/refresh")
    public Mono<JwtResponseDto> refresh(
            @Validated @RequestBody final JwtTokenDto jwtTokenDto
    ) {
        JwtToken jwtToken = jwtTokenMapper.toEntity(jwtTokenDto);
        return authService.refresh(jwtToken)
                .map(jwtResponseMapper::toDto);
    }

    /**
     * Send a token to user's email for restoring password.
     * @param email user's email
     * @return empty response
     */
    @PostMapping("/forget")
    public Mono<Void> forget(@RequestBody final String email) {
        return authService.sendRestoreToken(email);
    }

    /**
     * Restore user's password.
     *
     * @param token    token
     * @param password new password
     * @return empty response
     */
    @PostMapping("/password/restore")
    public Mono<Void> restore(@RequestParam final String token,
                              @RequestBody final String password) {
        return authService.restoreUserPassword(token, password);
    }

}
