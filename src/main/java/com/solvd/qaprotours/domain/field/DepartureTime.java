package com.solvd.qaprotours.domain.field;

import lombok.Data;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.time.LocalDateTime;

@Data
public class DepartureTime implements Field {

    private LocalDateTime departureTime;

    @Override
    public void apply(final CriteriaQuery criteriaQuery) {
        if (departureTime != null
                && departureTime.isBefore(LocalDateTime.now())) {
            departureTime = LocalDateTime.now();
        }
        if (departureTime != null) {
            Criteria criteria = new Criteria("departureTime");
            criteria.lessThanEqual(departureTime);
            criteriaQuery.addCriteria(criteria);
        }
    }

}
