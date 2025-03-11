package com.bayzdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main entry point of the BayzDeliveryApplication.
 * <p>
 * This is a Spring Boot application that leverages the following features:
 * - @SpringBootApplication: Indicates a configuration class that declares one or more @Bean methods
 * and triggers auto-configuration and component scanning.
 * - @EnableScheduling: Enables support for scheduled tasks.
 * - @EnableAsync: Enables Spring's asynchronous method execution capability.
 * <p>
 * The application starts by invoking the main method, which initializes the Spring Application Context
 * and triggers the application execution.
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class BayzDeliveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(BayzDeliveryApplication.class, args);
    }
}
