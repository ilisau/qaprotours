package com.solvd.qaprotours.web.dto;

import com.solvd.qaprotours.domain.City;
import com.solvd.qaprotours.domain.Country;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author Lisov Ilya
 */
@Data
public class AddressDto {

    @NotNull(message = "id cannot be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "country cannot be null", groups = {OnCreate.class, OnUpdate.class})
    private Country country;

    @NotNull(message = "city cannot be null", groups = {OnCreate.class, OnUpdate.class})
    private City city;

    @NotNull(message = "street name cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, max = 120, message = "street name must be between 1 and 120 characters", groups = {OnCreate.class, OnUpdate.class})
    private String streetName;

    @NotNull(message = "house number cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, message = "house number must be greater than 0", groups = {OnCreate.class, OnUpdate.class})
    private Integer houseNumber;

}
