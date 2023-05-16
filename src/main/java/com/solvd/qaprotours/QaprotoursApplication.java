package com.solvd.qaprotours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
public class QaprotoursApplication {

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(QaprotoursApplication.class, args);
    }

}
