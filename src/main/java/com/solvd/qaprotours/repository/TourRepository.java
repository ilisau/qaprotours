package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.tour.Tour;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Varya Petrova
 */
public interface TourRepository extends ReactiveCrudRepository<Tour, Long> {

    /**
     * Find tour by id.
     *
     * @param id tour id
     * @return tour or empty response
     */
    @Query("""
            SELECT tours.id as id,
                   tours.latitude as latitude,
                   tours.longitude as longitude,
                   tours.name as name,
                   tours.description as description,
                   tours.arrival_time as arrival_time,
                   tours.departure_time as departure_time,
                   tours.place_amount as places_amount,
                   tours.price as price,
                   tours.type as tour_type,
                   tours.country as country,
                   tours.city as city,
                   tours.type as type,
                   tours.catering_type as catering_type,
                   tours.is_draft as is_draft,
                   tours.rating as rating,
                   tours.day_duration as day_duration,
                   ti.image_url as image_url,
                   h.id as hotel_id,
                   h.name as hotel_name,
                   h.coastline as coastline,
                   h.stars_amount as stars_amount,
                   a.id as address_id,
                   a.country as address_country,
                   a.city as address_city,
                   a.street as street,
                   a.house_number as house_number
            FROM tours
            LEFT JOIN tour_image ti on tours.id = ti.tour_id
            JOIN hotels h on h.id = tours.hotel_id
            JOIN addresses a on h.address_id = a.id
            WHERE tours.id = :id
            """)
    Mono<Tour> findById(Long id);

    /**
     * Find all tours.
     *
     * @param sort sort type
     * @return sorted list of tours
     */
    Flux<Tour> findAll(Sort sort);

}
