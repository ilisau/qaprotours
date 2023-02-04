package com.solvd.qaprotours.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Ermakovich Kseniya
 */
@Getter
@RequiredArgsConstructor
public enum City {

    GRODNO(53.6688, 23.8223),
    KAIR(30.0444, 31.2357),
    ALEXANDRIA(31.2001, 29.9187);

    private final double latitude;
    private final double longitude;

}
