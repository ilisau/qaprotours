package com.solvd.qaprotours.domain.field;

import lombok.Data;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.util.List;

@Data
public class CountryField implements Field {

    private List<String> countries;

    @Override
    public void apply(final CriteriaQuery criteriaQuery) {
        if (countries != null && !countries.isEmpty()) {
            Criteria criteria = new Criteria("country");
            criteria.in(countries);
            criteriaQuery.addCriteria(criteria);
        }
    }

}
