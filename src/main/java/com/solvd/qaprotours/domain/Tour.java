package com.solvd.qaprotours.domain;

import com.solvd.qaprotours.domain.user.Ticket;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ermakovich Kseniya
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

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

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

    @OneToMany(mappedBy = "tour", orphanRemoval = true)
    private List<Ticket> tickets;

}
