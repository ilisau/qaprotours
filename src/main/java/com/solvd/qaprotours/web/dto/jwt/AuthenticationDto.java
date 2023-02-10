package com.solvd.qaprotours.web.dto.jwt;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Ermakovich Kseniya
 */
@Data
public class AuthenticationDto {

    @NotBlank(message = "email can`t be empty")
    private String email;

    @NotBlank(message = "password can`t be empty")
    private String password;

}
