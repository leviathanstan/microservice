package com.rdc.microservice.sproute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author asce
 * @since 2020/3/16
 */
@SpringBootApplication
@EnableEurekaClient
public class SprouteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SprouteApplication.class, args);
    }
}
