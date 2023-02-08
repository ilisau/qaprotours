package com.solvd.qaprotours.web.dto;

import com.solvd.qaprotours.domain.hotel.Point;
import com.solvd.qaprotours.domain.tour.Tour;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Varya Petrova
 */
@Data
public class TourCriteriaDto {

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
