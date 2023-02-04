package com.solvd.qaprotours.web.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * @author Ermakovich Kseniya
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAccessDto {

    private String token;
    private Instant expirationTime;

}
