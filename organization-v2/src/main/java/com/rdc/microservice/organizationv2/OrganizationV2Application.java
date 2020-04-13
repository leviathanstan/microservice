package com.rdc.microservice.organizationv2;

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
@EnableCircuitBreaker
public class OrganizationV2Application {

    public static void main(String[] args) {
        SpringApplication.run(OrganizationV2Application.class, args);
    }
}
