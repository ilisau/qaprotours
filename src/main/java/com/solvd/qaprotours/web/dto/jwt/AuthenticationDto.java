package com.solvd.qaprotours.web.dto.jwt;

import com.solvd.qaprotours.web.dto.validation.OnCreate;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Ermakovich Kseniya
 */
@Data
public class AuthenticationDto {

    @NotBlank(groups = OnCreate.class, message = "email can`t be empty")
    private String email;

    @NotBlank(groups = OnCreate.class, message = "password can`t be empty")
    private String password;

}
