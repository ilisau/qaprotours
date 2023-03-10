package com.solvd.qaprotours.service.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Lisov Ilya
 */
@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private final String bucket;
    private final String url;
    private final String accessKey;
    private final String secretKey;

}
