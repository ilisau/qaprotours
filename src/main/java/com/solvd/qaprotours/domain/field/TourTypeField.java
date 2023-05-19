package com.solvd.qaprotours.domain.field;

import com.solvd.qaprotours.domain.tour.Tour;
import lombok.Data;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.util.List;

@Data
public class TourTypeField implements Field {

    private List<Tour.TourType> tourTypes;

    @Override
    public void apply(final CriteriaQuery criteriaQuery) {
        if (tourTypes != null && !tourTypes.isEmpty()) {
            Criteria criteria = new Criteria("type");
            criteria.in(tourTypes);
            criteriaQuery.addCriteria(criteria);
        }
    }

}
