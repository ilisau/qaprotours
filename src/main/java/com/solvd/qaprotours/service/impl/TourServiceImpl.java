package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.hotel.Point;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.repository.TourRepository;
import com.solvd.qaprotours.service.TourService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Varya Petrova
 */
@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    @PersistenceContext
    private EntityManager entityManager;

    private final TourRepository tourRepository;

    @Override
    public List<Tour> findAll(int currentPage, int pageSize, TourCriteria tourCriteria) {
        Sort ratingSort = Sort.by("rating").descending();
        Sort arrivalTimeSort = Sort.by("arrivalTime");
        Sort multipleSort = ratingSort.and(arrivalTimeSort);
        Pageable paging = PageRequest.of(currentPage, pageSize, multipleSort);
        Page<Tour> toursPage = tourRepository.findAll(paging);
        List<Tour> tours = toursPage.getContent();
        if (tourCriteria != null){
            tours = findAllByCriteria(tourCriteria);
        }
        return tours;
    }

    @Override
    public List<Tour> findAllByCriteria(TourCriteria tourCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> tourRoot = criteriaQuery.from(Tour.class);
        List<Predicate> predicates = new ArrayList<>();

        List<String> countries = tourCriteria.getCountries();
        List<Predicate> countryPredicates = new ArrayList<>();
        if(countries != null && !countries.isEmpty()){
            for(String country: countries){
                countryPredicates.add(criteriaBuilder.equal(tourRoot.get("country"), country));
            }
            Predicate countriesFinalPredicate = criteriaBuilder.or(countryPredicates.toArray(new Predicate[0]));
            predicates.add(countriesFinalPredicate);
        }

//        Double maxRadius = tourCriteria.getMaxRadius();
//        if(maxRadius != null){}

        List<Tour.TourType> tourTypes = tourCriteria.getTourTypes();
        List<Predicate> tourTypePredicates = new ArrayList<>();
        if(tourTypes != null && !tourTypes.isEmpty()){
            for(Tour.TourType type: tourTypes){
                tourTypePredicates.add(criteriaBuilder.equal(tourRoot.get("type"), type));
            }
            Predicate tourTypesFinalPredicate = criteriaBuilder.or(tourTypePredicates.toArray(new Predicate[0]));
            predicates.add(tourTypesFinalPredicate);
        }

        Integer stars = tourCriteria.getStars();
        if(stars != null){
            Predicate starsPredicate = criteriaBuilder.equal(tourRoot.get("hotel").<Integer>get("starsAmount"), stars);
            predicates.add(starsPredicate);
        }

        List<Tour.CateringType> cateringTypes = tourCriteria.getCateringTypes();
        List<Predicate> cateringTypesPredicates = new ArrayList<>();
        if(cateringTypes != null && !cateringTypes.isEmpty()){
            for(Tour.CateringType type: cateringTypes){
                cateringTypesPredicates.add(criteriaBuilder.equal(tourRoot.get("cateringType"), type));
            }
            Predicate cateringTypesFinalPredicate = criteriaBuilder.or(cateringTypesPredicates.toArray(new Predicate[0]));
            predicates.add(cateringTypesFinalPredicate);
        }

        List<Integer> coastLines = tourCriteria.getCoastLines();
        List<Predicate> coastLinePredicates = new ArrayList<>();
        if(coastLines != null && !coastLines.isEmpty()){
            for(Integer coastLine: coastLines){
                coastLinePredicates.add(criteriaBuilder.equal(tourRoot.get("hotel").<Integer>get("coastline"), coastLine));
            }
            Predicate coastLineFinalPredicate = criteriaBuilder.or(coastLinePredicates.toArray(new Predicate[0]));
            predicates.add(coastLineFinalPredicate);
        }

        Integer dayDuration = tourCriteria.getDayDuration();
        if(dayDuration != null){
            Predicate dayDurationPredicate = criteriaBuilder.equal(tourRoot.get("dayDuration"), dayDuration);
            predicates.add(dayDurationPredicate);
        }

        LocalDateTime arrivedAt = tourCriteria.getArrivedAt();
        if(arrivedAt != null){
            Predicate arrivedAtPredicate = criteriaBuilder.greaterThanOrEqualTo(tourRoot.get("arrivalTime"), arrivedAt);
            predicates.add(arrivedAtPredicate);
        }

        LocalDateTime leavedAt = tourCriteria.getLeavedAt();
        if(leavedAt != null){
            Predicate leavedAtPredicate = criteriaBuilder.lessThanOrEqualTo(tourRoot.get("departureTime"), leavedAt);
            predicates.add(leavedAtPredicate);
        }

        BigDecimal minCost = tourCriteria.getMinCost();
        if(minCost != null){
            Predicate minCostPredicate = criteriaBuilder.greaterThanOrEqualTo(tourRoot.get("price"), minCost);
            predicates.add(minCostPredicate);
        }

        BigDecimal maxCost = tourCriteria.getMaxCost();
        if(maxCost != null){
            Predicate maxCostPredicate = criteriaBuilder.lessThanOrEqualTo(tourRoot.get("price"), maxCost);
            predicates.add(maxCostPredicate);
        }

        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        criteriaQuery.where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public void save(Tour tour) {

    }

    @Override
    public Tour getById(Long tourId) {
        return null;
    }

    @Override
    public void delete(Long tourId) {

    }

    private Double getDistanceBetweenPoints(Point userLocation, Point tourLocation) {
        int earthRadiusKm = 6371;
        double latitudeDiff = degreeToRadian(tourLocation.getLatitude() - userLocation.getLatitude());
        double longitudeDiff = degreeToRadian(tourLocation.getLongitude() - userLocation.getLongitude());
        double a = Math.sin(latitudeDiff / 2) * Math.sin(latitudeDiff / 2) +
                Math.cos(degreeToRadian(userLocation.getLatitude())) *
                        Math.cos(degreeToRadian(tourLocation.getLatitude())) *
                        Math.sin(longitudeDiff / 2) * Math.sin(longitudeDiff / 2);
        var distance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusKm * distance;
    }

    private Double degreeToRadian(Double degree) {
        return degree * (Math.PI / 180);
    }

}
