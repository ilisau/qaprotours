package com.solvd.qaprotours.web.dto;

import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.dto.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Lisov Ilya
 */
@Data
public class HotelDto {

    @NotNull(message = "id cannot be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "name cannot be null", groups = {OnUpdate.class, OnCreate.class})
    @Size(min = 1, max = 300, message = "name must be between 1 and 255 characters", groups = {OnUpdate.class, OnCreate.class})
    private String name;

    @NotNull(message = "coastline cannot be null", groups = {OnUpdate.class, OnCreate.class})
    @Size(min = 1, max = 10, message = "coastline must be between 1 and 10", groups = {OnUpdate.class, OnCreate.class})
    private Integer coastline;

    @NotNull(message = "stars amount cannot be null", groups = {OnUpdate.class, OnCreate.class})
    @Size(min = 1, max = 5, message = "stars amount must be between 1 and 5", groups = {OnUpdate.class, OnCreate.class})
    private Integer starsAmount;

    @NotNull(message = "address cannot be null", groups = {OnUpdate.class, OnCreate.class})
    @Valid
    private AddressDto address;

}
