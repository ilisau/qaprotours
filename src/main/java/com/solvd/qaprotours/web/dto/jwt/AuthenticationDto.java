package com.solvd.qaprotours.web.dto.jwt;

import com.solvd.qaprotours.web.dto.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Ermakovich Kseniya
 */
@Data
public class AuthenticationDto {

    @NotBlank(groups = OnCreate.class, message = "can`t be empty")
    @Size(min = 3, max = 320, groups = OnCreate.class, message = "length should be in 3..320")
    private String email;

    @NotBlank(groups = OnCreate.class, message = "can`t be empty")
    @Size(min = 4, max = 20, groups = OnCreate.class, message = "length should be in 4..20")
    private String password;

}
