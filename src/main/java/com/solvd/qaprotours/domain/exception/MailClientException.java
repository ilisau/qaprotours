package com.solvd.qaprotours.domain.exception;

import lombok.Getter;

import java.util.Map;

/**
 * @author Lisov Ilya
 */
@Getter
public class MailClientException extends RuntimeException {

    private final Map<String, String> details;

    /**
     * Create an exception with message and details.
     * @param message exception message
     * @param details exception details
     */
    public MailClientException(final String message,
                               final Map<String, String> details) {
        super(message);
        this.details = details;
    }

}
