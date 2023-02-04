package com.solvd.qaprotours.web.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Ermakovich Kseniya
 */
@Getter
@RequiredArgsConstructor
public enum JwtTokenType {

    ACCESS("access"),
    REFRESH("refresh");

    private final String value;

}
