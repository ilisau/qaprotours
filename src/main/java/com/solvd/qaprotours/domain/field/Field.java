package com.solvd.qaprotours.domain.field;

import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

public interface Field {

    /**
     * Method applies field criterias to criteriaquery.
     * @param criteriaQuery CriteriaQuery with all search criteria
     */
    void apply(CriteriaQuery criteriaQuery);

}
