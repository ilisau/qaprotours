package com.solvd.qaprotours.domain.hotel;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Varya Petrova
 */
@Embeddable
@Getter
@Setter
public class Point {

    private Double latitude;
    private Double longitude;

}