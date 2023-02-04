package com.solvd.qaprotours.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class UserTourId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tour_id")
    private Long tourId;


}
