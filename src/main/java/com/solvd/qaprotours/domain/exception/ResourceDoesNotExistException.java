package com.solvd.qaprotours.domain.exception;

/**
 * @author Ermakovich Kseniya
 */
public class ResourceDoesNotExistException extends RuntimeException {

    public ResourceDoesNotExistException(String message) {
        super(message);
    }

}
