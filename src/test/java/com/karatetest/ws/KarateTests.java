package com.karatetest.ws;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.core.MockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

/**
 * Main JUnit 5 test runner for Karate feature tests. This class is responsible for:
 * 1. Starting the Spring Boot application in a test environment
 * 2. Starting a Karate mock server to simulate the external API
 * 3. Configuring the application to use the mock instead of the real API
 * 4. Running the Karate test scenarios against the application
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Activate test profile to use application-test.properties
public class KarateTests {

    /**
     * The random port where the Spring Boot application is running.
     * This is injected by Spring's test framework.
     */
    @LocalServerPort
    private int port;
    
    /**
     * Holds the reference to the Karate mock server instance.
     * This mock server will simulate the external API during tests.
     */
    private static MockServer mockServer;

    /**
     * Executed once before any test runs.
     * Sets up the mock server using the provided feature file.
     */
    @BeforeAll
    static void beforeAll() {
        // Define and start the mock server before any tests run.
        // It uses the specified feature file to configure its behavior.
        mockServer = MockServer.feature("classpath:mocks/external-api-mock.feature").build();
        
        // Get the dynamically assigned port of the mock server.
        int mockPort = mockServer.getPort();
        System.out.println("Mock server started on port: " + mockPort);
        
        // Store the mock server's port as a System Property.
        // This property is then used by Spring Boot (via application-test.properties)
        // to configure the external.api.baseUrl for the application under test.
        System.setProperty("mock.server.port", String.valueOf(mockPort));
    }
    
    /**
     * Executed once after all tests have completed.
     * Ensures the mock server is properly shut down.
     */
    @AfterAll
    static void afterAll() {
        // Stop the mock server cleanly after all tests are finished.
        if (mockServer != null) {
            mockServer.stop();
        }
    }

    /**
     * Defines a test runner that will execute all Karate feature files.
     * 
     * @return Karate test runner configured with the application URL
     */
    @Karate.Test
    Karate testAll() {
        // Pass the application's actual random port (injected by @LocalServerPort)
        // as a System Property to Karate. This is used in karate-config.js
        // to set the 'baseUrl' for the tests.
        System.setProperty("server.port", String.valueOf(port));
        
        // Run all feature files found relative to this class.
        return Karate.run().relativeTo(getClass());
    }

    /**
     * Defines a test runner specifically for the simple-controller feature.
     * 
     * @return Karate test runner for the simple-controller feature
     */
    @Karate.Test
    Karate testSimpleController() {
        // Set the server port system property if not already set by testAll()
        System.setProperty("server.port", String.valueOf(port));
        
        // Run only the specified feature file
        return Karate.run("features/simple-controller").relativeTo(getClass());
    }
}
