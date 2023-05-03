package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
public interface JwtService {

    Claims parse(String token);

    String generateToken(JwtTokenType type, User user);

    Authentication getAuthentication(String token);

    boolean isTokenType(String token, JwtTokenType type);

    boolean validateToken(String token);

}
