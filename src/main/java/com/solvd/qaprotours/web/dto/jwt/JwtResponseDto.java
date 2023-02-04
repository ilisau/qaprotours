package com.solvd.qaprotours.web.dto.jwt;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ermakovich Kseniya
 */
@Getter
@Setter
public class JwtResponseDto {

    private JwtAccessDto accessDto;
    private RefreshDto refreshDto;

}
