package com.solvd.qaprotours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableScheduling
@EnableDiscoveryClient
public class QaprotoursApplication {

    public static void main(String[] args) {
        SpringApplication.run(QaprotoursApplication.class, args);
    }

}
