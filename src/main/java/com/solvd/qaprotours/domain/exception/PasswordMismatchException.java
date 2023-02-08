package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(String message) {
        super(message);
    }

}
