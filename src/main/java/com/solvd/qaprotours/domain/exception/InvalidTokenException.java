package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class InvalidTokenException extends RuntimeException {

    /**
     * Create an exception with a message.
     * @param message message to be returned to client
     */
    public InvalidTokenException(final String message) {
        super(message);
    }

}
