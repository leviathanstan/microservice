package com.rdc.microservice.license;

import com.rdc.microservice.license.events.models.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

/**
 * @author asce
 * @since 2020/3/13
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableResourceServer
@EnableOAuth2Sso
@EnableBinding(Sink.class)
public class LicenseApplication {

    private static final Logger logger = LoggerFactory.getLogger(LicenseApplication.class);

    @StreamListener(Sink.INPUT)
    public void loggerSink(OrganizationChangeModel organizationChangeModel) {
        System.out.println("i get the message");
        logger.debug("Received an event for organization id {}", organizationChangeModel.getOrganizationId());
    }

   @LoadBalanced
   @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(LicenseApplication.class, args);
    }

}
