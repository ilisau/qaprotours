package com.solvd.qaprotours.web.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Lisov Ilya
 */
@Data
public class PasswordDto {

    @NotNull(message = "old password cannot be null")
    @Size(min = 8, message = "old password must be more than {min} characters")
    private String oldPassword;

    @NotNull(message = "old password cannot be null")
    @Size(min = 8, message = "old password must be more than {min} characters")
    private String newPassword;

}
