package com.solvd.qaprotours.domain.field;

import lombok.Data;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.math.BigDecimal;

@Data
public class Price implements Field {

    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    @Override
    public void apply(final CriteriaQuery criteriaQuery) {
        if (minPrice != null) {
            Criteria criteria = new Criteria("price");
            criteria.greaterThanEqual(minPrice);
            criteriaQuery.addCriteria(criteria);
        }
        if (maxPrice != null) {
            Criteria criteria = new Criteria("price");
            criteria.lessThanEqual(maxPrice);
            criteriaQuery.addCriteria(criteria);
        }
    }

}
