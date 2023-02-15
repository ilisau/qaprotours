package com.solvd.qaprotours.web.security.jwt;

import com.solvd.qaprotours.domain.user.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Ermakovich Kseniya
 */
public class JwtUserDetailsFactory {

    public static JwtUserDetails create(User user) {

        return new JwtUserDetails(
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                true,
                mapToGrantedAuthority(Collections.singletonList(user.getRole())));
    }

    public static JwtUserDetails create(Claims claims) {
        User.Role role = null;
        String roleClaim = claims.get("role", String.class);
        if (roleClaim != null) {
            role = User.Role.valueOf(roleClaim);
        }

        return new JwtUserDetails(
                claims.get("id", Long.class),
                null,
                claims.getSubject(),
                true,
                mapToGrantedAuthority(Collections.singletonList(role)));
    }


    private static List<GrantedAuthority> mapToGrantedAuthority(List<User.Role> userRoles) {
        return userRoles.stream()
                .filter(Objects::nonNull)
                .map(userRole -> new SimpleGrantedAuthority(userRole.getAuthority()))
                .collect(Collectors.toList());
    }

}
