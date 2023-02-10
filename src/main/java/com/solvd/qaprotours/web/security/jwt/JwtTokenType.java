package com.solvd.qaprotours.web.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Ermakovich Kseniya, Lisov Ilya
 */
@Getter
@RequiredArgsConstructor
public enum JwtTokenType {

    ACCESS,
    REFRESH,
    ACTIVATION,
    RESET

}
