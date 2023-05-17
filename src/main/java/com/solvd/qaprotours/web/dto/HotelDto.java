package com.solvd.qaprotours.web.dto;

import com.solvd.qaprotours.web.dto.validation.OnCreate;
import com.solvd.qaprotours.web.dto.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Lisov Ilya
 */
@Data
public class HotelDto {

    @NotNull(message = "id cannot be null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "name cannot be null",
            groups = {OnUpdate.class, OnCreate.class})
    @Size(min = 1,
            max = 255,
            message = "name must be between {min} and {max} characters",
            groups = {OnUpdate.class, OnCreate.class})
    @Field(type = FieldType.Text, name = "name")
    private String name;

    @NotNull(message = "coastline cannot be null",
            groups = {OnUpdate.class, OnCreate.class})
    @Size(min = 1,
            max = 10,
            message = "coastline must be between {min} and {max}",
            groups = {OnUpdate.class, OnCreate.class})
    @Field(type = FieldType.Integer, name = "coastline")
    private Integer coastline;

    @NotNull(message = "stars amount cannot be null",
            groups = {OnUpdate.class, OnCreate.class})
    @Size(min = 1,
            max = 5,
            message = "stars amount must be between {min} and {max}",
            groups = {OnUpdate.class, OnCreate.class})
    @Field(type = FieldType.Integer, name = "starsAmount")
    private Integer starsAmount;

    @NotNull(message = "address cannot be null",
            groups = {OnUpdate.class, OnCreate.class})
    @Valid
    @Field(type = FieldType.Nested, name = "address")
    private AddressDto address;

}
