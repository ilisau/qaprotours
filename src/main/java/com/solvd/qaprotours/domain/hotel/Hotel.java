package com.solvd.qaprotours.domain.hotel;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Table("hotels")
public class Hotel {

    @Id
    private Long id;
    private String name;
    private Integer coastline;
    private Integer starsAmount;
    private Address address;

}
