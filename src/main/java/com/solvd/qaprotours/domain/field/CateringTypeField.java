package com.solvd.qaprotours.domain.field;

import com.solvd.qaprotours.domain.tour.Tour;
import lombok.Data;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.util.List;

@Data
public class CateringTypeField implements Field {

    private List<Tour.CateringType> cateringTypes;

    @Override
    public void apply(final CriteriaQuery criteriaQuery) {
        if (cateringTypes != null && !cateringTypes.isEmpty()) {
            Criteria criteria = new Criteria("cateringType");
            criteria.in(cateringTypes);
            criteriaQuery.addCriteria(criteria);
        }
    }

}
