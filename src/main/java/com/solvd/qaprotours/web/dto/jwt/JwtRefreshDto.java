package com.solvd.qaprotours.web.dto.jwt;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Ermakovich Kseniya
 */
@Data
public class JwtRefreshDto {

    @NotNull(message = "token is required")
    private String token;

}
