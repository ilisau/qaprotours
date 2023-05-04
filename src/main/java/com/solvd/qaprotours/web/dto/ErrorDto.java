package com.solvd.qaprotours.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author Ermakovich Kseniya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {

    private String message;

    private Map<String, String> details;

    /**
     * Create an error DTO with a message.
     * @param message message to be returned to client
     */
    public ErrorDto(final String message) {
        this.message = message;
    }

}
