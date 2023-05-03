package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(final String message) {
        super(message);
    }

}
