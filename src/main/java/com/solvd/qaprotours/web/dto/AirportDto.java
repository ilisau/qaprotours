package com.solvd.qaprotours.web.dto;

import com.solvd.qaprotours.domain.City;
import com.solvd.qaprotours.domain.Country;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Lisov Ilya
 */
@Data
public class AirportDto {

    @NotNull(message = "id cannot be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "name cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 1, max = 300, message = "name must be between 1 and 300 characters", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "code cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 3, max = 3, message = "code must be 3 characters", groups = {OnCreate.class, OnUpdate.class})
    private String code;

    @NotNull(message = "country cannot be null", groups = {OnCreate.class, OnUpdate.class})
    private Country country;

    @NotNull(message = "city cannot be null", groups = {OnCreate.class, OnUpdate.class})
    private City city;

}
