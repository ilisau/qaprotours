package com.solvd.qaprotours;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class QaprotoursApplication {

	public static void main(String[] args) {
		SpringApplication.run(QaprotoursApplication.class, args);
	}

}
