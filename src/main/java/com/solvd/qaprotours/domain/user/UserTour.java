package com.solvd.qaprotours.domain.user;

import com.solvd.qaprotours.domain.Tour;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Entity
@Table(name = "user_tours")
public class UserTour {

    @EmbeddedId
    private UserTourId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tourId")
    private Tour tour;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderTourStatus status;

    @Column(name = "client_amount", nullable = false)
    private Integer clientsAmount;

    private enum OrderTourStatus { ORDERED, CONFIRMED }

}
