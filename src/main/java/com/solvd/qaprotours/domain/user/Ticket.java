package com.solvd.qaprotours.domain.user;

import com.solvd.qaprotours.domain.tour.Tour;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Table(name = "tickets")
public class Ticket {

    @Id
    private Long id;
    private String userId;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    private Status status;
    private Integer clientsAmount;

    public enum Status {

        ORDERED,
        CONFIRMED,
        CANCELED

    }

}
