package com.solvd.qaprotours.web.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "access token info")
public class JwtAccessDto {

    @Schema(description = "access token")
    private String token;

    @Schema(description = "expiration time for access token. Time is in UTC")
    private Instant expirationTime;

}
