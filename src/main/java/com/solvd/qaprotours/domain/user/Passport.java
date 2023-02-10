package com.solvd.qaprotours.domain.user;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Ermakovich Kseniya
 */
@Data
@Entity
@Table(name = "passports")
public class Passport {

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "identity_number", nullable = false)
    private String identifyNumber;

}
