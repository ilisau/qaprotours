package com.solvd.qaprotours.service.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ermakovich Kseniya
 */
@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private final long access;
    private final long refresh;
    private final String secret;

}