package com.solvd.qaprotours.web.dto;

import com.solvd.qaprotours.domain.field.ArrivalTime;
import com.solvd.qaprotours.domain.field.CateringType;
import com.solvd.qaprotours.domain.field.Country;
import com.solvd.qaprotours.domain.field.DayDuration;
import com.solvd.qaprotours.domain.field.DepartureTime;
import com.solvd.qaprotours.domain.field.Price;
import com.solvd.qaprotours.domain.field.TourType;
import lombok.Data;

/**
 * @author Varya Petrova
 */
@Data
public class TourCriteriaDto {

    private Country country;
    private TourType tourType;
    private CateringType cateringType;
    private ArrivalTime arrivalTime;
    private DepartureTime departureTime;
    private DayDuration dayDuration;
    private Price price;

}
