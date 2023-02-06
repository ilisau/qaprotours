package com.solvd.qaprotours.repository.impl;

import com.solvd.qaprotours.domain.Country;
import com.solvd.qaprotours.domain.Tour;
import com.solvd.qaprotours.domain.TourCriteria;
import com.solvd.qaprotours.repository.TourCriteriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ermakovich Kseniya
 */
public class TourCriteriaRepositoryImpl implements TourCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tour> findByCriteria(TourCriteria tourCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tour> query = cb.createQuery(Tour.class);
        Root<Tour> root = query.from(Tour.class);
        List<Predicate> predicates = new ArrayList<>();

        if (tourCriteria.getTourType() != null) {
            Expression<String> type = root.get("tourType");
            predicates.add(cb.equal(type, tourCriteria.getTourType().name()));
        }

        if (tourCriteria.getCountry() != null) {
            Path<Country> country = root.get("country");
            predicates.add(cb.equal(country.<Long> get("id"), tourCriteria.getCountry()));
        }

        query.select(root)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .getResultList();
    }

}
