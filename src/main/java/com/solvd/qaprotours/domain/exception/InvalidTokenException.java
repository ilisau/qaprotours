package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(final String message) {
        super(message);
    }

}
