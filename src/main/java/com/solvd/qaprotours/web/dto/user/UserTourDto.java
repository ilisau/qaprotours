package com.solvd.qaprotours.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.qaprotours.domain.user.OrderTourStatus;
import com.solvd.qaprotours.domain.user.UserTourId;
import com.solvd.qaprotours.web.dto.TourDto;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Lisov Ilya
 */
@Data
public class UserTourDto {

    @NotNull(message = "id must not be null", groups = {OnUpdate.class})
    private UserTourId id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDto user;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TourDto tour;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime orderTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderTourStatus status;

    @NotNull(message = "clients amount must not be null", groups = {OnCreate.class})
    @Size(min = 1, max = 30, message = "clients amount must be between 1 and 30", groups = {OnCreate.class})
    private Integer clientsAmount;

}
