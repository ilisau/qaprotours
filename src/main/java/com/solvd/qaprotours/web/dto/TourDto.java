package com.solvd.qaprotours.web.dto;

import com.solvd.qaprotours.domain.CateringType;
import com.solvd.qaprotours.domain.City;
import com.solvd.qaprotours.domain.Country;
import com.solvd.qaprotours.domain.TourType;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Lisov Ilya
 */
@Data
public class TourDto {

    @NotNull(message = "id cannot be null", groups = {OnCreate.class})
    private Long id;

    @NotNull(message = "name cannot be null", groups = {OnCreate.class})
    @Size(min = 1, max = 255, message = "name length must be between 1 and 255", groups = {OnCreate.class})
    private String name;

    @NotNull(message = "description cannot be null", groups = {OnCreate.class})
    @Size(min = 1, max = 1024, message = "description length must be between 1 and 1024", groups = {OnCreate.class})
    private String description;

    @NotNull(message = "country cannot be null", groups = {OnCreate.class})
    private Country country;

    @NotNull(message = "city cannot be null", groups = {OnCreate.class})
    private City city;

    @NotNull(message = "type cannot be null", groups = {OnCreate.class})
    private TourType type;

    @NotNull(message = "catering type cannot be null", groups = {OnCreate.class})
    private CateringType cateringType;

    @NotNull(message = "hotel cannot be null", groups = {OnCreate.class})
    private HotelDto hotel;

    @NotNull(message = "arrival time cannot be null", groups = {OnCreate.class})
    private LocalDateTime arrivalTime;

    @NotNull(message = "departure time cannot be null", groups = {OnCreate.class})
    private LocalDateTime departureTime;

    @NotNull(message = "places amount cannot be null", groups = {OnCreate.class})
    @Min(value = 0, message = "places amount cannot be less than 0", groups = {OnCreate.class})
    private Integer placesAmount;

    @NotNull(message = "price cannot be null", groups = {OnCreate.class})
    @Min(value = 0, message = "price cannot be less than 0", groups = {OnCreate.class})
    private BigDecimal price;

}
