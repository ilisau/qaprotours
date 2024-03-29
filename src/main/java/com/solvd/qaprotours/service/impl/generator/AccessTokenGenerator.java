package com.solvd.qaprotours.service.impl.generator;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.property.JwtProperties;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccessTokenGenerator implements TokenGenerator {

    private final JwtProperties jwtProperties;
    private Key key;

    @PostConstruct
    private void postConstruct() {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    @Override
    public String generate(final User user) {
        final Instant accessExpiration = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.MINUTES);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("type", JwtTokenType.ACCESS.name())
                .claim("role", user.getRole())
                .setExpiration(Date.from(accessExpiration))
                .signWith(key)
                .compact();
    }

}
