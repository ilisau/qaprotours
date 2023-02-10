package com.solvd.qaprotours.web.security.jwt;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Ermakovich Kseniya
 */
@Data
@AllArgsConstructor
public class JwtUserDetails implements UserDetails {

    private final Long id;
    private final String password;
    private final String email;
    private final boolean enabled;
    private final String type;
    private final Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
