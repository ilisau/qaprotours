package com.solvd.qaprotours.domain.user;

import com.solvd.qaprotours.domain.tour.Tour;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * @author Ermakovich Kseniya
 */
@Data
@org.springframework.data.relational.core.mapping.Table(name = "tickets")
public class Ticket {

    @Id
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "client_amount", nullable = false)
    private Integer clientsAmount;

    public enum Status {

        ORDERED,
        CONFIRMED,
        CANCELED

    }

}
