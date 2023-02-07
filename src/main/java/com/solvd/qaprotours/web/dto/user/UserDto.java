package com.solvd.qaprotours.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.qaprotours.domain.user.User;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.dto.validation.OnUpdate;
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
    @Size(min = 1, max = 35, message = "name must be between 1 and 35 characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "surname cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, max = 35, message = "surname must be between 1 and 35 characters", groups = {OnCreate.class, OnUpdate.class})
    private String surname;

    @NotNull(message = "email cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, max = 35, message = "email must be between 1 and 35 characters", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private User.Role role;

}
