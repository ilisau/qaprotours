package com.solvd.qaprotours.domain.exception;

/**
 * @author Ermakovich Kseniya
 */
public class ResourceDoesNotExistException extends RuntimeException {

    /**
     * Create an exception with a message.
     * @param message message to be returned to client
     */
    public ResourceDoesNotExistException(final String message) {
        super(message);
    }

}
