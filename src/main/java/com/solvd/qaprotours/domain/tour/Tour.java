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
    private Long id;
    private String name;
    private String description;
    private String country;
    private String city;
    private TourType type;
    private CateringType cateringType;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @JoinColumn(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @JoinColumn(name = "departure_time")
    private LocalDateTime departureTime;
    private Integer placesAmount;
    private BigDecimal price;
    private BigDecimal rating;
    private Integer dayDuration;
    private boolean isDraft;

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
