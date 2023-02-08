package com.solvd.qaprotours.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Lisov Ilya
 */
@Data
public class UserDto {

    @NotNull(message = "id cannot be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "name cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, max = 255, message = "name must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "surname cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, max = 255, message = "surname must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String surname;

    @NotNull(message = "email cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "email must be valid", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, max = 255, message = "email must be between {min} and {max} characters", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @NotNull(message = "password cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 8, message = "password must be more than {min} characters", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private User.Role role;

}
