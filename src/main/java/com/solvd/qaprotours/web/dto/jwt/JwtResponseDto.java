package com.solvd.qaprotours.web.dto.jwt;

import lombok.Data;

/**
 * @author Ermakovich Kseniya
 */
@Data
public class JwtResponseDto {

    private String accessToken;
    private String refreshToken;

}
