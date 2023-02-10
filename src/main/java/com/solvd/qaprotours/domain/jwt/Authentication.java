package com.solvd.qaprotours.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ermakovich Kseniya
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {

    private String email;
    private String password;

}
