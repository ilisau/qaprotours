package com.solvd.qaprotours.domain.hotel;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Table("addresses")
public class Address {

    @Id
    private Long id;
    private String country;
    private String city;
    private String streetName;
    private Integer houseNumber;

}
