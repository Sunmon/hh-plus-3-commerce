package com.hhplus.commerce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
public class CommerceApplication {

    public static void main(String[] args) {
        log.info("Application Started {}", LocalDateTime.now());
        SpringApplication.run(CommerceApplication.class, args);
    }

}
