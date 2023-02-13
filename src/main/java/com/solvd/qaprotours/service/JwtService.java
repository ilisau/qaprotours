package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetails;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

/**
 * @author Ermakovich Kseniya
 */
public interface JwtService {

    Claims parse(String token);

    String generateToken(JwtTokenType type, User user);

    Authentication getAuthentication(String token);

    boolean isAccessToken(JwtUserDetails jwtUserDetails);

    boolean validateToken(String token);

    Long retrieveUserId(String token);

}
