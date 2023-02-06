package com.solvd.qaprotours.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Lisov Ilya
 */
@Data
public class TourCriteria {

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
