package com.solvd.qaprotours.domain.user;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Ermakovich Kseniya
 */
@Data
public class User {

    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
    private boolean isActivated;

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

}
