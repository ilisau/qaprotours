package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.jwt.JwtAccess;
import com.solvd.qaprotours.domain.jwt.JwtRefresh;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetails;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

/**
 * @author Ermakovich Kseniya
 */
public interface JwtService {

    JwtUserDetails parseToken(String token);

    Claims parse(String token);

    JwtRefresh generateRefreshToken(User user);

    JwtAccess generateAccessToken(User user);

    String generateActivationToken(User user);

    String generateResetToken(User user);

    Authentication getAuthentication(String token);

    boolean isAccessToken(JwtUserDetails jwtUserDetails);

    boolean validateToken(String token);

    Long retrieveUserId(String token);

}
