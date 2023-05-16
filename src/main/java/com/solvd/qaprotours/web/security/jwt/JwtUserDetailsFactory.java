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

    /**
     * Creates a JwtUserDetails object from a User object.
     * @param user the User object to be converted.
     * @return a JwtUserDetails object.
     */
    public static JwtUserDetails create(final User user) {
        return new JwtUserDetails(
                user.getId(),
                user.getPassword(),
                user.getEmail(),
                true,
                mapToGrantedAuthority(
                        Collections.singletonList(user.getRole())
                )
        );
    }

    /**
     * Creates a JwtUserDetails object from a Claims object.
     * @param claims the Claims object to be converted.
     * @return a JwtUserDetails object.
     */
    public static JwtUserDetails create(final Claims claims) {
        User.Role role = null;
        String roleClaim = claims.get("role", String.class);
        if (roleClaim != null) {
            role = User.Role.valueOf(roleClaim);
        }

        return new JwtUserDetails(
                claims.get("id", String.class),
                null,
                claims.getSubject(),
                true,
                mapToGrantedAuthority(Collections.singletonList(role)));
    }


    private static List<GrantedAuthority> mapToGrantedAuthority(
            final List<User.Role> userRoles
    ) {
        return userRoles.stream()
                .filter(Objects::nonNull)
                .map(userRole -> new SimpleGrantedAuthority(
                        userRole.getAuthority())
                )
                .collect(Collectors.toList());
    }

}
