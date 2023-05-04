package com.solvd.qaprotours.domain.exception;

import lombok.Getter;

import java.util.Map;

/**
 * @author Lisov Ilya
 */
@Getter
public class UserClientException extends RuntimeException {

    private final Map<String, String> details;

    /**
     * Create an exception with a message and details.
     * @param message message to be returned to client
     * @param details details to be returned to client
     */
    public UserClientException(final String message,
                               final Map<String, String> details) {
        super(message);
        this.details = details;
    }

}
