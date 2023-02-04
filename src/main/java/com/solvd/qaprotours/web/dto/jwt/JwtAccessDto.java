package com.solvd.qaprotours.web.dto.jwt;

import lombok.*;

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
