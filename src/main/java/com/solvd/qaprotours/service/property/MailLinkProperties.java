package com.solvd.qaprotours.service.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Lisov Ilya
 */
@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "mail.links")
public class MailLinkProperties {

    private String activation;

    private String restore;

}
