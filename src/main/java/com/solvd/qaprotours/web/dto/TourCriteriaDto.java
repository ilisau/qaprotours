package com.solvd.qaprotours.web.dto;

import com.solvd.qaprotours.domain.field.ArrivalTimeField;
import com.solvd.qaprotours.domain.field.CateringTypeField;
import com.solvd.qaprotours.domain.field.CountryField;
import com.solvd.qaprotours.domain.field.DayDurationField;
import com.solvd.qaprotours.domain.field.DepartureTimeField;
import com.solvd.qaprotours.domain.field.PriceField;
import com.solvd.qaprotours.domain.field.TourTypeField;
import lombok.Data;

/**
 * @author Varya Petrova
 */
@Data
public class TourCriteriaDto {

    private CountryField country;
    private TourTypeField tourType;
    private CateringTypeField cateringType;
    private ArrivalTimeField arrivalTime;
    private DepartureTimeField departureTime;
    private DayDurationField dayDuration;
    private PriceField price;

}
