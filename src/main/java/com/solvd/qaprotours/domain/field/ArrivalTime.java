package com.solvd.qaprotours.domain.field;

import lombok.Data;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.time.LocalDateTime;

@Data
public class ArrivalTime implements Field {

    private LocalDateTime arrivalTime;

    @Override
    public void apply(final CriteriaQuery criteriaQuery) {
        if (arrivalTime == null || arrivalTime.isBefore(LocalDateTime.now())) {
            arrivalTime = LocalDateTime.now();
        }
        Criteria arrivedAtCriteria = new Criteria("arrivalTime");
        arrivedAtCriteria.greaterThanEqual(arrivalTime);
        criteriaQuery.addCriteria(arrivedAtCriteria);
    }

}
