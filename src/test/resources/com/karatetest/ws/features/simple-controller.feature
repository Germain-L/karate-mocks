# This feature file contains integration tests for the SimpleController's API endpoint.
# It interacts with the running Spring Boot application, which in turn
# interacts with the mock external API during these tests.
Feature: Simple Controller API Tests

Background:
    # Set the base URL for all requests in this feature.
    # The 'baseUrl' variable is configured in karate-config.js
    # using the 'server.port' system property set in KarateTests.java.
    * url baseUrl

Scenario: Get data from simple controller
    # Test the primary endpoint.
    Given path '/api/data'
    When method GET
    Then status 200
    # Validate the overall structure of the response from our service.
    # Note that 'externalData' will contain the data from the mock server.
    And match response == 
    """
    {
        message: 'Hello from local service!',
        externalData: '#object', # Assert externalData is present and is an object
        timestamp: '#number'     # Assert timestamp is present and is a number
    }
    """
    # Explicitly check that the mocked data is not null.
    And match response.externalData != null

Scenario: Verify external API data structure
    # Specifically validate the structure of the data received from the (mocked) external API.
    Given path '/api/data'
    When method GET
    Then status 200
    # Check that the 'externalData' field contains the expected structure
    # as defined in the mock server (external-api-mock.feature).
    And match response.externalData contains
    """
    {
        userId: '#number',
        id: '#number',
        title: '#string',
        completed: '#boolean'
    }
    """
