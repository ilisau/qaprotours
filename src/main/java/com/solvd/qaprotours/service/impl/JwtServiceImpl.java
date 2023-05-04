package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.impl.generator.AccessTokenGenerator;
import com.solvd.qaprotours.service.impl.generator.ActivationTokenGenerator;
import com.solvd.qaprotours.service.impl.generator.RefreshTokenGenerator;
import com.solvd.qaprotours.service.impl.generator.ResetTokenGenerator;
import com.solvd.qaprotours.service.property.JwtProperties;
import com.solvd.qaprotours.web.security.jwt.JwtTokenType;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetails;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetailsFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Objects;

/**
 * @author Ermakovich Kseniya
 */
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;
    private final ReactiveUserDetailsService userDetailsService;
    private final AccessTokenGenerator accessTokenGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final ActivationTokenGenerator activationTokenGenerator;
    private final ResetTokenGenerator resetTokenGenerator;
    private Key key;

    @PostConstruct
    private void postConstruct() {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    @Override
    public Claims parse(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String generateToken(final JwtTokenType type, final User user) {
        return switch (type) {
            case ACCESS -> accessTokenGenerator.generate(user);
            case REFRESH -> refreshTokenGenerator.generate(user);
            case ACTIVATION -> activationTokenGenerator.generate(user);
            case RESET -> resetTokenGenerator.generate(user);
        };
    }

    @Override
    public Authentication getAuthentication(final String token) {
        Claims claims = parse(token);
        JwtUserDetails jwtUserDetails = JwtUserDetailsFactory.create(claims);
        return userDetailsService.findByUsername(jwtUserDetails.getEmail())
                .map(userDetails -> new UsernamePasswordAuthenticationToken(
                        userDetails,
                        "",
                        userDetails.getAuthorities()))
                .block();
    }

    @Override
    public boolean isTokenType(final String token,
                               final JwtTokenType type) {
        Claims claims = parse(token);
        return Objects.equals(claims.get("type"), type.name());
    }

    @Override
    public boolean validateToken(final String token) {
        try {
            Jws<Claims> claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
