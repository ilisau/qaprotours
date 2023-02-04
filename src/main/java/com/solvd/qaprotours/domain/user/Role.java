package com.solvd.qaprotours.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    CLIENT("CLIENT"),
    EMPLOYEE("EMPLOYEE");

    private final String value;

    @Override
    public String getAuthority() {
        return "ROLE_" + value;
    }
}
