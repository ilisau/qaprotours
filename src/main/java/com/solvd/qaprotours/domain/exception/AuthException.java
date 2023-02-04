package com.solvd.qaprotours.domain.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Ermakovich Kseniya
 */
public class AuthException extends AuthenticationException {

    public AuthException(String message) {
        super(message);
    }

}
