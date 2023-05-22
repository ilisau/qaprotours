package com.solvd.qaprotours.domain.tour;

import com.solvd.qaprotours.domain.hotel.Hotel;
import com.solvd.qaprotours.domain.hotel.Point;
import com.solvd.qaprotours.domain.user.Ticket;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Varya Petrova
 */
@Data
@Table("tours")
public class Tour {

    @Id
    private Long id;
    private String name;
    private String description;
    private String country;
    private String city;
    private TourType type;
    private CateringType cateringType;
    private Hotel hotel;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private Integer placesAmount;
    private BigDecimal price;
    private BigDecimal rating;
    private Integer dayDuration;
    private boolean isDraft;
    private List<String> imageUrls;
    private Point coordinates;
    private List<Ticket> tickets;

    public enum CateringType {

        BREAKFAST,
        ALL_INCLUSIVE

    }

    public enum TourType {

        HEALTH,
        CULTURE

    }

}
