package com.solvd.qaprotours.web.controller;

import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.Refresh;
import com.solvd.qaprotours.service.AuthService;
import com.solvd.qaprotours.web.dto.jwt.AuthenticationDto;
import com.solvd.qaprotours.web.dto.jwt.JwtResponseDto;
import com.solvd.qaprotours.web.dto.jwt.RefreshDto;
import com.solvd.qaprotours.web.mapper.jwt.AuthenticationMapper;
import com.solvd.qaprotours.web.mapper.jwt.JwtResponseMapper;
import com.solvd.qaprotours.web.mapper.jwt.RefreshMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ermakovich Kseniya
 */
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final JwtResponseMapper mapper;
    private final RefreshMapper refreshMapper;
    private final AuthenticationMapper authenticationMapper;

    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody AuthenticationDto authenticationDto) {
        Authentication authentication = authenticationMapper.dtoToEntity(authenticationDto);
        JwtResponse response = authService.login(authentication);
        return mapper.entityToDto(response);
    }

    @PostMapping("/refresh")
    public JwtResponseDto refresh(@RequestBody RefreshDto refreshDto) {
        Refresh refresh = refreshMapper.dtoToEntity(refreshDto);
        JwtResponse response = authService.refresh(refresh);
        return mapper.entityToDto(response);
    }

}
