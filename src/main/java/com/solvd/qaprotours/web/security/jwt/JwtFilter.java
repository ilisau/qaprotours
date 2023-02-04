package com.solvd.qaprotours.web.security.jwt;

import com.solvd.qaprotours.domain.exception.AuthException;
import com.solvd.qaprotours.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;


import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author Ermakovich Kseniya
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = getTokenFromRequest((HttpServletRequest) req);
            if (token != null) {
                JwtUserDetails jwtUserDetails = jwtService.parseToken(token);
                if (!jwtService.isAccessToken(jwtUserDetails)) {
                    throw new JwtException("invalid token");
                }
                Authentication auth = jwtService.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(req, res);
        } catch (JwtException | AuthException ex) {
            throw new BadCredentialsException("bad credentials");
        }
    }

    public String getTokenFromRequest(HttpServletRequest req) {
        final String bearer = req.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
