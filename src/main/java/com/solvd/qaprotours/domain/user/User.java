package com.solvd.qaprotours.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_activated")
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
