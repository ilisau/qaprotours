package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.JwtToken;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.AuthService;
import com.solvd.qaprotours.service.UserClient;
import com.solvd.qaprotours.web.dto.jwt.AuthenticationDto;
import com.solvd.qaprotours.web.dto.jwt.JwtResponseDto;
import com.solvd.qaprotours.web.dto.jwt.JwtTokenDto;
import com.solvd.qaprotours.web.dto.user.UserDto;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.mapper.jwt.AuthenticationMapper;
import com.solvd.qaprotours.web.mapper.jwt.JwtResponseMapper;
import com.solvd.qaprotours.web.mapper.jwt.JwtTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public JwtResponseDto login(@Validated @RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationMapper.toEntity(authenticationDto);
        JwtResponse response = authService.login(authentication);
        return jwtResponseMapper.toDto(response);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        userDto.setRole(User.Role.CLIENT);
        userClient.create(userDto);
    }

    @PostMapping("/register/confirm")
    public void confirm(@Validated @RequestBody JwtTokenDto jwtTokenDto) {
        userClient.activate(jwtTokenDto);
    }

    @PostMapping("/refresh")
    public JwtResponseDto refresh(@Validated @RequestBody JwtTokenDto jwtTokenDto) {
        JwtToken jwtToken = jwtTokenMapper.toEntity(jwtTokenDto);
        JwtResponse response = authService.refresh(jwtToken);
        return jwtResponseMapper.toDto(response);
    }

    @PostMapping("/forget")
    public void forget(@RequestBody String email) {
        authService.sendRestoreToken(email);
    }

    @PostMapping("/password/restore")
    public void restore(@RequestParam String token,
                        @RequestBody String password) {
        authService.restoreUserPassword(token, password);
    }

}
