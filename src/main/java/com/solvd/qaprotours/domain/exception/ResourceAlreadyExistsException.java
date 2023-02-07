package com.solvd.qaprotours.domain.exception;

/**
 * @author Lisov Ilya
 */
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

}
