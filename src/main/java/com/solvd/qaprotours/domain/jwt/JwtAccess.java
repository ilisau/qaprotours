package com.solvd.qaprotours.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;


@AllArgsConstructor
@Data
public class JwtAccess {

    private String token;
    private Instant expirationTime;

}
