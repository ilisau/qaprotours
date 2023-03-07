package com.solvd.qaprotours.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.solvd.qaprotours.domain.user.Ticket;
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
public class TicketDto {

    @NotNull(message = "id must not be null", groups = {OnUpdate.class})
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TourDto tour;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime orderTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Ticket.Status status;

    @NotNull(message = "clients amount must not be null", groups = {OnCreate.class})
    @Size(min = 1, max = 30, message = "clients amount must be between {min} and {max}", groups = {OnCreate.class})
    private Integer clientsAmount;

}
