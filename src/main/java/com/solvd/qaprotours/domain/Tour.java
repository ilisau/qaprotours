package com.solvd.qaprotours.domain;

import com.solvd.qaprotours.domain.*;
import com.solvd.qaprotours.domain.user.UserTour;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tours")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(name = "city")
    private City city;

    @ManyToOne
    @JoinColumn(name = "airport_from_id")
    private Airport airportFrom;

    @ManyToOne
    @JoinColumn(name = "airport_to_id")
    private Airport airportTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
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

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "tour", orphanRemoval = true)
    private List<UserTour> userTours;

    private enum TourType { HEALTH, CULTURE }

}
