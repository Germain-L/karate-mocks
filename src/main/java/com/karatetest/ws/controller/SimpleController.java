package com.karatetest.ws.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * SimpleController provides a REST API endpoint that demonstrates calling an external API.
 * This controller serves as the core component in our example, showing how external
 * dependencies can be mocked during testing with Karate.
 */
@RestController
public class SimpleController {

    /**
     * Injected RestTemplate used for making HTTP calls to the external API.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * The base URL of the external API to call.
     * Default value points to a public JSON placeholder API.
     * This value is overridden during tests by application-test.properties
     * to point to the Karate mock server.
     */
    @Value("${external.api.baseUrl:https://jsonplaceholder.typicode.com}")
    private String externalApiBaseUrl;
    
    /**
     * The specific path/endpoint to call on the external API.
     * Default value is "/todos/1" which returns a single todo item.
     */
    @Value("${external.api.path:/todos/1}")
    private String externalApiPath;    /**
     * REST endpoint that returns data from both this service and an external API.
     * During tests, the external API call is intercepted by the Karate mock server.
     *
     * @return A Map containing local data, external data, and a timestamp
     */
    @GetMapping("/api/data")
    public Map<String, Object> getData() {
        // Construct the full URL for the external API call.
        // During tests, externalApiBaseUrl points to the mock server.
        String externalApiUrl = externalApiBaseUrl + externalApiPath;
        
        // Make the HTTP GET request. In tests, this hits the mock server.
        Object externalData = restTemplate.getForObject(externalApiUrl, Object.class);

        // Prepare the combined response for the client.
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from local service!");
        response.put("externalData", externalData);  // Data from external API (or mock in tests)
        response.put("timestamp", System.currentTimeMillis());

        return response;
    }
}
