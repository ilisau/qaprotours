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

    public ErrorDto(String message) {
        this.message = message;
    }

}
