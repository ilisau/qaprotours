package com.solvd.qaprotours.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ermakovich Kseniya
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtRefresh {

    private String token;

}
