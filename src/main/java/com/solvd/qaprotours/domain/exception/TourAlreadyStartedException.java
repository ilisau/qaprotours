package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class TourAlreadyStartedException extends RuntimeException {

    /**
     * Create an exception with a message.
     * @param message message to be returned to client
     */
    public TourAlreadyStartedException(final String message) {
        super(message);
    }

}
