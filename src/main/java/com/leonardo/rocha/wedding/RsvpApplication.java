package com.leonardo.rocha.wedding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class RsvpApplication {
	private static final Logger logger = LoggerFactory.getLogger(RsvpApplication.class);

	public static void main(String[] args) {
		logger.info("Updated code");
		SpringApplication.run(RsvpApplication.class, args);
	}

}
