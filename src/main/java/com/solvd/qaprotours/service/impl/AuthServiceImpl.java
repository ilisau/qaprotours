package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.AuthException;
import com.solvd.qaprotours.domain.jwt.Authentication;
import com.solvd.qaprotours.domain.jwt.JwtAccess;
import com.solvd.qaprotours.domain.jwt.JwtResponse;
import com.solvd.qaprotours.domain.jwt.Refresh;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.AuthService;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.UserService;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public JwtResponse login(Authentication authentication) {
        User user = userService.findByEmail(authentication.getEmail());
        if (!passwordEncoder.matches(authentication.getPassword(), user.getPassword())) {
            throw new AuthException("wrong password");
        }
        final JwtAccess access = jwtService.generateAccessToken(user);
        final Refresh refreshToken = jwtService.generateRefreshToken(user);
        return new JwtResponse(access, refreshToken);
    }

    @Override
    public JwtResponse refresh(Refresh refresh) {
        String refreshToken = refresh.getToken();
        JwtUserDetails userDetails = jwtService.parseToken(refreshToken);
        final User user = userService.findByEmail(userDetails.getEmail());
        if (!user.getPassword().equals(userDetails.getPassword())) {
            throw new AuthException("wrong password");
        }
        final JwtAccess access = jwtService.generateAccessToken(user);
        final Refresh newRefreshToken = jwtService.generateRefreshToken(user);
        return new JwtResponse(access, newRefreshToken);
    }
}
