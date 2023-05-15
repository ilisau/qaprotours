package com.solvd.qaprotours.service.impl.fake;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.property.JwtProperties;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;

@RequiredArgsConstructor
public class FakeJwtService implements JwtService {

    private final JwtProperties jwtProperties;
    private Key key;

    @PostConstruct
    private void postConstruct() {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    @Override
    public Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String generateToken(JwtTokenType type, User user) {
        return "token";
    }

    @Override
    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken("user",
                "password");
    }

    @Override
    public boolean isTokenType(String token, JwtTokenType type) {
        return true;
    }

    @Override
    public boolean validateToken(String token) {
        return true;
    }

}
