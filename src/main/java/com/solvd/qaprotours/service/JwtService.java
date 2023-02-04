package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.JwtAccess;
import com.solvd.qaprotours.domain.jwt.Refresh;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetails;
import org.springframework.security.core.Authentication;

public interface JwtService {

    JwtUserDetails parseToken(String token);

    Refresh generateRefreshToken(User user);

    JwtAccess generateAccessToken(User user);

    Authentication getAuthentication(String token);

    boolean isAccessToken(JwtUserDetails jwtUserDetails);

}
