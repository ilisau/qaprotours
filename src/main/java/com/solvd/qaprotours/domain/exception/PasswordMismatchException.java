package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class PasswordMismatchException extends RuntimeException {

    /**
     * Create an exception with a message.
     * @param message message to be returned to client
     */
    public PasswordMismatchException(final String message) {
        super(message);
    }

}
