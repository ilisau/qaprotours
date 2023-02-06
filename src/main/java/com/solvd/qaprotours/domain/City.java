package com.solvd.qaprotours.domain;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Entity
@Table(name = "cities")
public class City {

    @Id
    private Long id;
    private String name;
    private double latitude;
    private double longitude;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
