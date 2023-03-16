package com.solvd.qaprotours.service.impl;

import com.solvd.qaprotours.domain.exception.ResourceDoesNotExistException;
import com.solvd.qaprotours.domain.tour.Tour;
import com.solvd.qaprotours.domain.tour.TourCriteria;
import com.solvd.qaprotours.repository.TourRepository;
import com.solvd.qaprotours.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Varya Petrova, Lisov Ilya
 */
@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    //TODO this is not working with R2dbc
//    @PersistenceContext
//    private final EntityManager entityManager;

    private final TourRepository tourRepository;
    private final int PAGE_SIZE = 20;

    @Override
    @Transactional(readOnly = true)
    public Flux<Tour> getAll(Integer currentPage, Integer pageSize, TourCriteria tourCriteria) {
//        List<Tour> tours;
//        if (currentPage == null || pageSize == null) {
//            currentPage = 0;
//            pageSize = PAGE_SIZE;
//        }
//        if (tourCriteria != null) {
//            return getAllByCriteria(tourCriteria, currentPage, pageSize);
//        } else {
//            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//            CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
//            Root<Tour> tourRoot = criteriaQuery.from(Tour.class);
//            Order arrivalTimeOrder = criteriaBuilder.asc(tourRoot.get("arrivalTime"));
//            Order ratingOrder = criteriaBuilder.desc(tourRoot.get("rating"));
//            criteriaQuery.orderBy(arrivalTimeOrder, ratingOrder);
//            Pageable pageable = PageRequest.of(currentPage, pageSize);
//            tours = entityManager.createQuery(criteriaQuery)
//                    .setFirstResult((int) pageable.getOffset())
//                    .setMaxResults(pageable.getPageSize())
//                    .getResultList();
//        }
//        return Flux.just(tours.toArray(new Tour[0]));
        return Flux.just(new Tour[0]);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Tour> getAllByCriteria(TourCriteria tourCriteria, int currentPage, int pageSize) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
//        Root<Tour> tourRoot = criteriaQuery.from(Tour.class);
//        List<Predicate> predicates = new ArrayList<>();
//
//        List<String> countries = tourCriteria.getCountries();
//        List<Predicate> countryPredicates = new ArrayList<>();
//        if (countries != null && !countries.isEmpty()) {
//            for (String country : countries) {
//                countryPredicates.add(criteriaBuilder.equal(tourRoot.get("country"), country));
//            }
//            Predicate countriesFinalPredicate = criteriaBuilder.or(countryPredicates.toArray(new Predicate[0]));
//            predicates.add(countriesFinalPredicate);
//        }
//
//        if (userLocationAndMaxRadiusNotNull(tourCriteria)) {
//            Expression<Double> distance = criteriaBuilder.function("calculate_distance", Double.class,
//                    tourRoot.get("coordinates").get("latitude"),
//                    tourRoot.get("coordinates").get("longitude"),
//                    criteriaBuilder.literal(tourCriteria.getUserLocation().getLatitude()),
//                    criteriaBuilder.literal(tourCriteria.getUserLocation().getLongitude())
//            );
//            Predicate userLocationPredicate = criteriaBuilder.lessThanOrEqualTo(distance, tourCriteria.getMaxRadius());
//            predicates.add(userLocationPredicate);
//        }
//
//        List<Tour.TourType> tourTypes = tourCriteria.getTourTypes();
//        List<Predicate> tourTypePredicates = new ArrayList<>();
//        if (tourTypes != null && !tourTypes.isEmpty()) {
//            for (Tour.TourType type : tourTypes) {
//                tourTypePredicates.add(criteriaBuilder.equal(tourRoot.get("type"), type));
//            }
//            Predicate tourTypesFinalPredicate = criteriaBuilder.or(tourTypePredicates.toArray(new Predicate[0]));
//            predicates.add(tourTypesFinalPredicate);
//        }
//
//        Integer stars = tourCriteria.getStars();
//        if (stars != null) {
//            Predicate starsPredicate = criteriaBuilder.equal(tourRoot.get("hotel").<Integer>get("starsAmount"), stars);
//            predicates.add(starsPredicate);
//        }
//
//        List<Tour.CateringType> cateringTypes = tourCriteria.getCateringTypes();
//        List<Predicate> cateringTypesPredicates = new ArrayList<>();
//        if (cateringTypes != null && !cateringTypes.isEmpty()) {
//            for (Tour.CateringType type : cateringTypes) {
//                cateringTypesPredicates.add(criteriaBuilder.equal(tourRoot.get("cateringType"), type));
//            }
//            Predicate cateringTypesFinalPredicate = criteriaBuilder.or(cateringTypesPredicates.toArray(new Predicate[0]));
//            predicates.add(cateringTypesFinalPredicate);
//        }
//
//        List<Integer> coastLines = tourCriteria.getCoastLines();
//        List<Predicate> coastLinePredicates = new ArrayList<>();
//        if (coastLines != null && !coastLines.isEmpty()) {
//            for (Integer coastLine : coastLines) {
//                coastLinePredicates.add(criteriaBuilder.equal(tourRoot.get("hotel").<Integer>get("coastline"), coastLine));
//            }
//            Predicate coastLineFinalPredicate = criteriaBuilder.or(coastLinePredicates.toArray(new Predicate[0]));
//            predicates.add(coastLineFinalPredicate);
//        }
//
//        Integer dayDuration = tourCriteria.getDayDuration();
//        if (dayDuration != null) {
//            Predicate dayDurationPredicate = criteriaBuilder.equal(tourRoot.get("dayDuration"), dayDuration);
//            predicates.add(dayDurationPredicate);
//        }
//
//        LocalDateTime arrivedAt = tourCriteria.getArrivedAt();
//        if (arrivedAt == null || arrivedAt.isBefore(LocalDateTime.now())) {
//            arrivedAt = LocalDateTime.now();
//        }
//        Predicate arrivedAtPredicate = criteriaBuilder.greaterThanOrEqualTo(tourRoot.get("arrivalTime"), arrivedAt);
//        predicates.add(arrivedAtPredicate);
//
//        LocalDateTime leavedAt = tourCriteria.getLeavedAt();
//        if (leavedAt != null && leavedAt.isBefore(LocalDateTime.now())) {
//            leavedAt = LocalDateTime.now();
//        }
//        if(leavedAt != null) {
//            Predicate leavedAtPredicate = criteriaBuilder.lessThanOrEqualTo(tourRoot.get("departureTime"), leavedAt);
//            predicates.add(leavedAtPredicate);
//        }
//
//        BigDecimal minCost = tourCriteria.getMinCost();
//        if (minCost != null) {
//            Predicate minCostPredicate = criteriaBuilder.greaterThanOrEqualTo(tourRoot.get("price"), minCost);
//            predicates.add(minCostPredicate);
//        }
//
//        BigDecimal maxCost = tourCriteria.getMaxCost();
//        if (maxCost != null) {
//            Predicate maxCostPredicate = criteriaBuilder.lessThanOrEqualTo(tourRoot.get("price"), maxCost);
//            predicates.add(maxCostPredicate);
//        }
//
//        Predicate finalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        criteriaQuery.where(finalPredicate);
//
//        Order arrivalTimeOrder = criteriaBuilder.asc(tourRoot.get("arrivalTime"));
//        Order ratingOrder = criteriaBuilder.desc(tourRoot.get("rating"));
//
//        criteriaQuery.orderBy(arrivalTimeOrder, ratingOrder);
//
//        Pageable pageable = PageRequest.of(currentPage, pageSize);
//        List<Tour> tours = entityManager.createQuery(criteriaQuery)
//                .setFirstResult((int) pageable.getOffset())
//                .setMaxResults(pageable.getPageSize())
//                .getResultList();
//        return Flux.just(tours.toArray(new Tour[0]));
        return Flux.just(new Tour[0]);
    }

    private boolean userLocationAndMaxRadiusNotNull(TourCriteria tourCriteria) {
        return tourCriteria.getUserLocation() != null && tourCriteria.getUserLocation().getLatitude() != null && tourCriteria.getUserLocation().getLongitude() != null && tourCriteria.getMaxRadius() != null;
    }

    @Override
    @Transactional
    public Mono<Tour> save(Tour tour) {
        return tourRepository.save(tour);
    }

    @Override
    @Transactional
    public Mono<Tour> publish(Tour tour) {
        return Mono.just(tour)
                .map(t -> {
                    t.setDraft(false);
                    return t;
                })
                .flatMap(tourRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Tour> getById(Long tourId) {
        Mono<Tour> error = Mono.error(new ResourceDoesNotExistException("tour with id " + tourId + " does not exist"));
        return tourRepository.findById(tourId)
                .switchIfEmpty(error);
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long tourId) {
        return tourRepository.deleteById(tourId);
    }

    @Override
    public Mono<Void> addImage(Long tourId, String fileName) {
        return getById(tourId)
                .map(tour -> {
                    tour.getImageUrls().add(fileName);
                    return tour;
                })
                .flatMap(tourRepository::save)
                .then();
    }

}
