package com.solvd.qaprotours.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Entity
@Table(name = "countries")
public class Country {

    @Id
    private Long id;
    private String name;

}
