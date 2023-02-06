package com.solvd.qaprotours.web.dto;

import com.solvd.qaprotours.domain.CateringType;
import com.solvd.qaprotours.domain.TourType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Lisov Ilya
 */
@Data
public class TourCriteriaDto {

    private Long country;
    private Long city;
    private double cityRadius;
    private TourType tourType;
    private Integer starsAmount;
    private CateringType cateringType;
    private Integer coastline;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private Integer placesAmount;
    private Integer datesAmount;
    private BigDecimal price;

}
