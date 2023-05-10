package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class NoFreePlacesException extends RuntimeException {

    /**
     * Create an exception with a message.
     * @param message message to be returned to client
     */
    public NoFreePlacesException(final String message) {
        super(message);
    }

}
