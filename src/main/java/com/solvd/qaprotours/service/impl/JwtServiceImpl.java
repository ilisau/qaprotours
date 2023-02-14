package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.JwtService;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
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
        return switch (type) {
            case ACCESS -> generateAccessToken(user);
            case REFRESH -> generateRefreshToken(user);
            case ACTIVATION -> generateActivationToken(user);
            case RESET -> generateResetToken(user);
        };
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = parse(token);
        JwtUserDetails jwtUserDetails = JwtUserDetailsFactory.create(claims);
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUserDetails.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public boolean isAccessToken(JwtUserDetails jwtUserDetails) {
        return Objects.equals(jwtUserDetails.getType(), JwtTokenType.ACCESS.name());
    }

    private String generateRefreshToken(User user) {
        final Instant refreshExpiration = Instant.now().plus(jwtProperties.getRefresh(), ChronoUnit.HOURS);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("type", JwtTokenType.REFRESH.name())
                .setExpiration(Date.from(refreshExpiration))
                .signWith(key)
                .compact();
    }

    private String generateAccessToken(User user) {
        final Instant accessExpiration = Instant.now().plus(jwtProperties.getAccess(), ChronoUnit.MINUTES);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("type", JwtTokenType.ACCESS.name())
                .claim("role", user.getRole())
                .setExpiration(Date.from(accessExpiration))
                .signWith(key)
                .compact();
    }

    private String generateActivationToken(User user) {
        final Instant accessExpiration = Instant.now().plus(jwtProperties.getActivation(), ChronoUnit.HOURS);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("type", JwtTokenType.ACTIVATION.name())
                .setExpiration(Date.from(accessExpiration))
                .signWith(key)
                .compact();
    }

    private String generateResetToken(User user) {
        final Instant accessExpiration = Instant.now().plus(jwtProperties.getReset(), ChronoUnit.HOURS);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .claim("type", JwtTokenType.RESET.name())
                .setExpiration(Date.from(accessExpiration))
                .signWith(key)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
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

    @Override
    public Long retrieveUserId(String token) {
        return Long.valueOf(parse(token)
                .get("id")
                .toString());
    }

}
