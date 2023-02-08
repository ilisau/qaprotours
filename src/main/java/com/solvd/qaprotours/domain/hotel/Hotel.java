package com.solvd.qaprotours.domain.hotel;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Integer coastline;

    @Column(name = "stars_amount")
    private Integer starsAmount;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

}