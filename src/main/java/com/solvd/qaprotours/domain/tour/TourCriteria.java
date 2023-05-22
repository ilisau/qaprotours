package com.solvd.qaprotours.domain.tour;

import com.solvd.qaprotours.domain.field.Field;
import lombok.Data;
import org.cactoos.list.ListOf;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.util.List;

/**
 * @author Varya Petrova
 */
@Data
public class TourCriteria {

    private List<Field> fields;

    /**
     * Creates a TourCriteria object with empty criteria.
     *
     * @param fields list of criteria
     */
    public TourCriteria(final List<Field> fields) {
        this.fields = fields;
    }

    /**
     * Creates a TourCriteria object with empty criteria.
     */
    public TourCriteria() {
        this(new ListOf<>());
    }

    /**
     * Applies all criteria to fields.
     * @param searchQuery CriteriaQuery object
     */
    public void apply(final CriteriaQuery searchQuery) {
        fields.forEach(f -> {
            if (f != null) {
                f.apply(searchQuery);
            }
        });
    }

}
