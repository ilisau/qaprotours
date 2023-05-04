package com.solvd.qaprotours.repository;

import com.solvd.qaprotours.domain.user.Ticket;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

/**
 * @author Lisov Ilya
 */
public interface TicketRepository extends ReactiveCrudRepository<Ticket, Long> {

    /**
     * Find all tickets by user id.
     * @param userId id of user
     * @return list of tickets
     */
    Flux<Ticket> findAllByUserId(String userId);

    /**
     * Find all tickets by tour arrival time is before particular time
     * and ticket status.
     *
     * @param time   specific time to search tickets before it
     * @param status status of ticket
     * @return list of tickets
     */
    Flux<Ticket> findAllByTourArrivalTimeIsBeforeAndStatus(
            LocalDateTime time, Ticket.Status status
    );

    /**
     * Find all tickets by tour arrival time is after particular time
     * and ticket status.
     *
     * @param start  specific time to search tickets after it
     * @param end    specific time to search tickets before it
     * @param status status of ticket
     * @return list of tickets
     */
    @Query("""
            SELECT tickets.id as id,
                   tickets.user_id as user_id,
                   tickets.order_time as order_time,
                   tickets.status as status,
                   tickets.clients_amount as clients_amount,
                   tickets.tour_id as tour_id,
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
                   ti.image_url as image_url
            FROM tickets
            JOIN tours on tours.id = tickets.tour_id
            LEFT JOIN tour_image ti on tours.id = ti.tour_id
            WHERE tours.arrival_time BETWEEN :start
            AND :end
            AND tickets.status = :status
            """)
    Flux<Ticket> findAllSoonTickets(
            @Param(value = "start") LocalDateTime start,
            @Param(value = "end") LocalDateTime end,
            @Param(value = "status") Ticket.Status status
    );

}
