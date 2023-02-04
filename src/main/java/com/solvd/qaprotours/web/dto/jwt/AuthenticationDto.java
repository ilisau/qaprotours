package com.solvd.qaprotours.web.dto.jwt;

import com.solvd.qaprotours.web.dto.onCreate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "authentication info for user login")
public class AuthenticationDto {

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 3, max = 320, groups = onCreate.class, message = "length should be in 3..320")
    @Schema(description = "user email")
    private String email;

    @NotBlank(groups = onCreate.class, message = "can`t be empty")
    @Size(min = 4, max = 20, groups = onCreate.class, message = "length should be in 4..20")
    @Schema(description = "user password")
    private String password;

}
