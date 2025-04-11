package com.karatetest.ws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Spring configuration class that provides application-wide beans.
 * This class is important for the mocking example as it provides
 * the RestTemplate that will be used to make calls to the external API
 * (or the mock server during tests).
 */
@Configuration
public class AppConfig {

    /**
     * Creates a RestTemplate bean that will be used for making HTTP requests.
     * This is the component that will be redirected to the mock server during tests
     * thanks to the configuration in application-test.properties.
     *
     * @return A RestTemplate instance for making HTTP requests
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
