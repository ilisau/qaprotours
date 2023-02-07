package com.solvd.qaprotours.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ermakovich Kseniya
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTourId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tour_id")
    private Long tourId;


}
