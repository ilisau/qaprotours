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
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_activated", nullable = false)
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
