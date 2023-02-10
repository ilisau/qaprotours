package com.solvd.qaprotours.domain.tour;

import com.solvd.qaprotours.domain.hotel.Point;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Varya Petrova
 */
@Data
public class TourCriteria {

    private Point userLocation;
    private List<String> countries;
    private Double maxRadius;
    private List<Tour.TourType> tourTypes;
    private Integer stars;
    private List<Tour.CateringType> cateringTypes;
    private List<Integer> coastLines;
    private LocalDateTime arrivedAt;
    private LocalDateTime leavedAt;
    private Integer dayDuration;
    private BigDecimal minCost;
    private BigDecimal maxCost;

}
