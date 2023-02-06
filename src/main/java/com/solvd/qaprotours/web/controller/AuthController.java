package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtRefresh;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.service.AuthService;
import com.solvd.qaprotours.web.dto.jwt.AuthenticationDto;
import com.solvd.qaprotours.web.dto.jwt.JwtRefreshDto;
import com.solvd.qaprotours.web.dto.jwt.JwtResponseDto;
import com.solvd.qaprotours.web.mapper.jwt.AuthenticationMapper;
import com.solvd.qaprotours.web.mapper.jwt.JwtResponseMapper;
import com.solvd.qaprotours.web.mapper.jwt.RefreshMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtResponseMapper jwtResponseMapper;
    private final RefreshMapper refreshMapper;
    private final AuthenticationMapper authenticationMapper;

    @PostMapping("/login")
    public JwtResponseDto login(@Valid @RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationMapper.toEntity(authenticationDto);
        JwtResponse response = authService.login(authentication);
        return jwtResponseMapper.toDto(response);
    }

    @PostMapping("/refresh")
    public JwtResponseDto refresh(@Valid @RequestBody JwtRefreshDto jwtRefreshDto) {
        JwtRefresh jwtRefresh = refreshMapper.toEntity(jwtRefreshDto);
        JwtResponse response = authService.refresh(jwtRefresh);
        return jwtResponseMapper.toDto(response);
    }

    @PostMapping("/forget")
    public void forget(@RequestBody String email) {
        authService.sendRestoreToken(email);
    }

    @GetMapping("/password/restore")
    public void restore(@RequestParam String token) {
        authService.validateRestoreToken(token);
    }

    @PostMapping("/password/restore")
    public void restore(@RequestParam String token, @RequestBody String password) {
        authService.restoreUserPassword(token, password);
    }

}
