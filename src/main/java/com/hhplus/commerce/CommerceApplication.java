package com.hhplus.commerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CommerceApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommerceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CommerceApplication.class, args);
        logger.info("Application started");

    }

}
