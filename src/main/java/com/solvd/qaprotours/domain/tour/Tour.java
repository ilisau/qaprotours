package com.solvd.qaprotours.domain.tour;

import com.solvd.qaprotours.domain.hotel.Hotel;
import com.solvd.qaprotours.domain.hotel.Point;
import com.solvd.qaprotours.domain.user.Ticket;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Varya Petrova
 */
@Data
@Entity
@Table(name = "tours")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private String country;

    private String city;

    @Enumerated(EnumType.STRING)
    private TourType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "catering_type")
    private CateringType cateringType;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @JoinColumn(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @JoinColumn(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "place_amount")
    private Integer placesAmount;

    private BigDecimal price;

    private BigDecimal rating;

    @Column(name = "day_duration")
    private Integer dayDuration;

    private boolean isDraft;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "longitude"))
    })
    private Point coordinates;

    @OneToMany(mappedBy = "tour")
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
