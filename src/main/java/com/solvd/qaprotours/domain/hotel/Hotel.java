package com.solvd.qaprotours.domain.hotel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "coastline")
    private Integer coastline;

    @Column(name = "stars_amount")
    private Integer starsAmount;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

}
