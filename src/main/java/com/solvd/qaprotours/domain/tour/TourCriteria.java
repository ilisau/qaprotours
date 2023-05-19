package com.solvd.qaprotours.domain.tour;

import com.solvd.qaprotours.domain.field.ArrivalTimeField;
import com.solvd.qaprotours.domain.field.CateringTypeField;
import com.solvd.qaprotours.domain.field.CountryField;
import com.solvd.qaprotours.domain.field.DayDurationField;
import com.solvd.qaprotours.domain.field.DepartureTimeField;
import com.solvd.qaprotours.domain.field.PriceField;
import com.solvd.qaprotours.domain.field.TourTypeField;
import lombok.Data;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

/**
 * @author Varya Petrova
 */
@Data
public class TourCriteria {

    private CountryField country;
    private TourTypeField tourType;
    private CateringTypeField cateringType;
    private ArrivalTimeField arrivalTime;
    private DepartureTimeField departureTime;
    private DayDurationField dayDuration;
    private PriceField price;

    /**
     * Creates a TourCriteria object with empty criteria.
     */
    public TourCriteria() {
        country = new CountryField();
        tourType = new TourTypeField();
        cateringType = new CateringTypeField();
        arrivalTime = new ArrivalTimeField();
        departureTime = new DepartureTimeField();
        dayDuration = new DayDurationField();
        price = new PriceField();
    }

    /**
     * Applies all criteria to fields.
     * @param searchQuery CriteriaQuery object
     */
    public void apply(final CriteriaQuery searchQuery) {
        this.country.apply(searchQuery);
        this.tourType.apply(searchQuery);
        this.cateringType.apply(searchQuery);
        this.dayDuration.apply(searchQuery);
        this.arrivalTime.apply(searchQuery);
        this.departureTime.apply(searchQuery);
        this.price.apply(searchQuery);
    }

}
