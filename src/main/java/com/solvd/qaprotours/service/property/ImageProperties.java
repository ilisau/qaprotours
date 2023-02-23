package com.solvd.qaprotours.service.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Lisov Ilya
 */
@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "image")
public class ImageProperties {

    private List<Integer> thumbnails;
    private List<String> extensions;

}
