package com.solvd.qaprotours.domain.field;

import lombok.Data;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

@Data
public class DayDurationField implements Field {

    private Integer minDaysAmount;
    private Integer maxDaysAmount;

    @Override
    public void apply(final CriteriaQuery criteriaQuery) {
        if (minDaysAmount != null) {
            Criteria criteria = new Criteria("daysAmount");
            criteria.greaterThanEqual(minDaysAmount);
            criteriaQuery.addCriteria(criteria);
        }
        if (maxDaysAmount != null) {
            Criteria criteria = new Criteria("daysAmount");
            criteria.lessThanEqual(maxDaysAmount);
            criteriaQuery.addCriteria(criteria);
        }
    }

}
