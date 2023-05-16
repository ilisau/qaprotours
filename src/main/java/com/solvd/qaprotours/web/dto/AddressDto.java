package com.solvd.qaprotours.web.dto;

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

    @NotNull(message = "id cannot be null",
            groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "country cannot be null",
            groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 3,
            max = 40,
            message = "Country should be from {min} to {max} symbols")
    private String country;

    @NotNull(message = "city cannot be null",
            groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 3,
            max = 40,
            message = "City should be from {min} to {max} symbols")
    private String city;

    @NotNull(message = "street name cannot be null",
            groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1,
            max = 255,
            message = "street name must be between {min} and {max} characters",
            groups = {OnCreate.class, OnUpdate.class})
    private String streetName;

    @NotNull(message = "house number cannot be null",
            groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1,
            message = "house number must be greater than 0",
            groups = {OnCreate.class, OnUpdate.class})
    private Integer houseNumber;

}
