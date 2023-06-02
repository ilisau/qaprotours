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
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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
     *
     * @param credentials user's email and password
     * @return pair of tokens
     */
    @PostMapping("/login")
    @QueryMapping
    public Mono<JwtResponseDto> login(
            @Validated
            @RequestBody @Argument final AuthenticationDto credentials
    ) {
        Authentication authentication = authenticationMapper
                .toEntity(credentials);
        return authService.login(authentication)
                .map(jwtResponseMapper::toDto);
    }

    /**
     * Register a new user.
     *
     * @param user user's data
     * @return empty response
     */
    @PostMapping("/register")
    @MutationMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createUser(
            @Validated(OnCreate.class) @RequestBody @Argument final UserDto user
    ) {
        User u = userMapper.toEntity(user);
        u.setRole(User.Role.CLIENT);
        return userClient.create(u);
    }

    /**
     * Confirm user's account.
     *
     * @param token token
     * @return empty response
     */
    @PostMapping("/register/confirm")
    @MutationMapping
    public Mono<Void> confirmUser(
            @Validated @RequestBody @Argument final JwtTokenDto token
    ) {
        JwtToken jwtToken = jwtTokenMapper.toEntity(token);
        return userClient.activate(jwtToken);
    }

    /**
     * Refresh user's tokens.
     *
     * @param token refresh token
     * @return pair of tokens
     */
    @PostMapping("/refresh")
    @MutationMapping
    public Mono<JwtResponseDto> refresh(
            @Validated @RequestBody @Argument final JwtTokenDto token
    ) {
        JwtToken jwtToken = jwtTokenMapper.toEntity(token);
        return authService.refresh(jwtToken)
                .map(jwtResponseMapper::toDto);
    }

    /**
     * Send a token to user's email for restoring password.
     *
     * @param email user's email
     * @return empty response
     */
    @PostMapping("/forget")
    @MutationMapping
    public Mono<Void> forget(@RequestBody @Argument final String email) {
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
    @MutationMapping
    public Mono<Void> restore(@RequestParam @Argument final String token,
                              @RequestBody @Argument final String password) {
        return authService.restoreUserPassword(token, password);
    }

}
