package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) {
        super(message);
    }

}
