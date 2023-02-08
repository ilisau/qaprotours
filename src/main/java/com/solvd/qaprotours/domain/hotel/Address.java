package com.solvd.qaprotours.domain.hotel;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String city;

    @Column(name = "street", nullable = false)
    private String streetName;

    @Column(name = "house_number", nullable = false)
    private Integer houseNumber;

}
