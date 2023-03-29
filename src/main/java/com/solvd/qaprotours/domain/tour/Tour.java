package com.solvd.qaprotours.domain.tour;

import com.solvd.qaprotours.domain.hotel.Hotel;
import com.solvd.qaprotours.domain.hotel.Point;
import com.solvd.qaprotours.domain.user.Ticket;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "tours")
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "type")
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

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "day_duration")
    private Integer dayDuration;

    @Column(name = "is_draft")
    private boolean isDraft;

    @Column(name = "image_url")
    @ElementCollection
    @CollectionTable(name = "tour_image", joinColumns = @JoinColumn(name = "tour_id"))
    private List<String> imageUrls;

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
