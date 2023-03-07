package com.solvd.qaprotours.domain.exception;

import lombok.Getter;

import java.util.Map;

/**
 * @author Lisov Ilya
 */
@Getter
public class MicroserviceException extends RuntimeException {

    private final Map<String, String> details;

    public MicroserviceException(String message, Map<String, String> details) {
        super(message);
        this.details = details;
    }

}
