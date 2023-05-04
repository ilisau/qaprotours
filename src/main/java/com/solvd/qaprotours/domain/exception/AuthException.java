package com.solvd.qaprotours.domain.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Ermakovich Kseniya
 */
public class AuthException extends AuthenticationException {

    /**
     * Create an exception with a message.
     * @param message message to be returned to client
     */
    public AuthException(final String message) {
        super(message);
    }

}
