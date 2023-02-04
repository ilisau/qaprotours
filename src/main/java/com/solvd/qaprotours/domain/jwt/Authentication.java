package com.solvd.qaprotours.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ermakovich Kseniya
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {

    private String email;
    private String password;

}
