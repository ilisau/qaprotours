package com.solvd.qaprotours.domain.exception;

import lombok.Getter;

import java.util.Map;

/**
 * @author Lisov Ilya
 */
@Getter
public class MailClientException extends RuntimeException {

    private final Map<String, String> details;

    public MailClientException(String message, Map<String, String> details) {
        super(message);
        this.details = details;
    }

}
