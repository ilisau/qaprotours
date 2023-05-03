package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class NoFreePlacesException extends RuntimeException {

    public NoFreePlacesException(final String message) {
        super(message);
    }

}
