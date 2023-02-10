package com.solvd.qaprotours.web.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Lisov Ilya
 */
@Data
public class PasswordDto {

    @NotEmpty(message = "old password must be not empty")
    private String oldPassword;

    @Size(min = 8, message = "new password must be longer than {min} characters")
    private String newPassword;

}
