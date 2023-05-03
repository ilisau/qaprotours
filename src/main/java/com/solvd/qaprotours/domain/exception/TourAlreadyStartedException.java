package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class TourAlreadyStartedException extends RuntimeException {

    public TourAlreadyStartedException(final String message) {
        super(message);
    }

}
