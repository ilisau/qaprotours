package com.solvd.qaprotours.web.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author Ermakovich Kseniya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAccessDto {

    private String token;
    private Instant expirationTime;

}
