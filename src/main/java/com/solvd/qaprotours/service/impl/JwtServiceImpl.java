package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.jwt.JwtAccess;
import com.solvd.qaprotours.domain.jwt.Refresh;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.service.JwtService;
import com.solvd.qaprotours.service.property.JwtProperties;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetails;
import com.solvd.qaprotours.web.security.jwt.JwtUserDetailsFactory;
import io.jsonwebtoken.Claims;
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
    public JwtUserDetails parseToken(String token) {
       Claims claims =  Jwts.parserBuilder()
               .setSigningKey(key)
               .build()
               .parseClaimsJws(token)
               .getBody();
       return JwtUserDetailsFactory.create(claims);
    }

    @Override
    public Authentication getAuthentication(String token) {
        JwtUserDetails jwtUserDetails = parseToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUserDetails.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public boolean isAccessToken(JwtUserDetails jwtUserDetails) {
        return jwtUserDetails.getId() != null && jwtUserDetails.getPassword() == null;
    }

    @Override
    public Refresh generateRefreshToken(User user) {
        final Instant refreshExpiration = Instant.now().plus(jwtProperties.getRefresh(), ChronoUnit.HOURS);
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("password", user.getPassword())
                .setExpiration(Date.from(refreshExpiration))
                .signWith(key)
                .compact();
        return new Refresh(token);
    }

    @Override
    public JwtAccess generateAccessToken(User user) {
        final Instant accessExpiration = Instant.now().plus(jwtProperties.getAccess(), ChronoUnit.MINUTES);
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .claim("id", user.getId())
                .setExpiration(Date.from(accessExpiration))
                .signWith(key)
                .compact();
        return new JwtAccess(token, accessExpiration);
    }

}
