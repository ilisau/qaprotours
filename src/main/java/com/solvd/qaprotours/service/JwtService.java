package com.solvd.qaprotours.service;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
public interface JwtService {

    /**
     * Parse token.
     * @param token token
     * @return claims
     */
    Claims parse(String token);

    /**
     * Generate token.
     * @param type type
     * @param user user
     * @return token
     */
    String generateToken(JwtTokenType type, User user);

    /**
     * Get authentication from token.
     * @param token token
     * @return authentication
     */
    Authentication getAuthentication(String token);

    /**
     * Validate token type.
     *
     * @param token token
     * @param type  type
     * @return true if token type is valid
     */
    boolean isTokenType(String token, JwtTokenType type);

    /**
     * Validate token.
     * @param token token
     * @return true if token is valid
     */
    boolean validateToken(String token);

}
