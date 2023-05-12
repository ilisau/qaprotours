package com.solvd.qaprotours.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Lisov Ilya
 */
@Data
public class TourDto {

    @NotNull(message = "id cannot be null",
            groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "name cannot be null",
            groups = {OnCreate.class})
    @Size(min = 1,
            max = 255,
            message = "name length must be between {min} and {max}",
            groups = {OnCreate.class})
    private String name;

    @NotNull(message = "description cannot be null",
            groups = {OnCreate.class})
    @Size(min = 1,
            max = 1024,
            message = "description length must be between {min} and {max}",
            groups = {OnCreate.class})
    private String description;

    @NotNull(message = "country cannot be null",
            groups = {OnCreate.class})
    @Size(min = 3,
            max = 40,
            message = "Country should be from {min} to {max} symbols")
    private String country;

    @NotNull(message = "city cannot be null",
            groups = {OnCreate.class})
    @Size(min = 3,
            max = 40,
            message = "City should be from {min to {max} symbols")
    private String city;

    @NotNull(message = "type cannot be null",
            groups = {OnCreate.class})
    private Tour.TourType type;

    @NotNull(message = "catering type cannot be null",
            groups = {OnCreate.class})
    private Tour.CateringType cateringType;

    @NotNull(message = "hotel cannot be null",
            groups = {OnCreate.class})
    private HotelDto hotel;

    @DecimalMin(value = "0.0",
            message = "Rating should be 0.0 or more")
    @DecimalMax(value = "5.0",
            message = "Rating should be 5.0 or less")
    @Digits(integer = 1,
            fraction = 1,
            message = "Format : 5.0")
    @NotNull(message = "Rating cant be null")
    private BigDecimal rating;

    @Min(value = 1,
            message = "Tour duration must be 1 or more days")
    @Max(value = 50,
            message = "Tour duration must be less than 50 days")
    private Integer dayDuration;

    @NotNull(message = "arrival time cannot be null",
            groups = {OnCreate.class})
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalTime;

    @NotNull(message = "departure time cannot be null",
            groups = {OnCreate.class})
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isDraft;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> imageUrls;

    @NotNull(message = "places amount cannot be null",
            groups = {OnCreate.class})
    @Min(value = 0,
            message = "places amount cannot be less than 0",
            groups = {OnCreate.class})
    private Integer placesAmount;

    @NotNull(message = "price cannot be null",
            groups = {OnCreate.class})
    @Min(value = 0,
            message = "price cannot be less than 0",
            groups = {OnCreate.class})
    private BigDecimal price;

}
