# Example: Mocking External APIs with Karate and Spring Boot

This repository demonstrates how to use Karate's mock server feature to test a Spring Boot application that depends on an external REST API. The approach shown here helps you isolate your service under test for reliable and fast integration tests.

## Technology Stack

- Java
- Spring Boot
- Maven
- Karate
- JUnit 5

## How It Works (Conceptual Flow)

1. The Spring Boot application (`SimpleController`) normally calls a real external API (`jsonplaceholder.typicode.com`).
2. During tests (`KarateTests`), a Karate mock server is started *before* the tests run.
3. The mock server is configured using a Karate feature file (`external-api-mock.feature`) to respond to specific paths (e.g., `/todos/1`).
4. Spring Boot's test configuration (`application-test.properties`) is activated.
5. This test configuration overrides the base URL of the external API to point to the *mock server's* address and port.
6. When Karate tests (`simple-controller.feature`) hit the application's endpoint (`/api/data`), the application internally tries to call the "external" API.
7. This call is routed to the mock server, which provides a predefined response.
8. The Karate test then validates the application's response, which now includes the mocked data.

### Flow Diagram (Normal vs. Test)

```ascii
Normal Flow:
+-----------------+       +-------------------+
| Spring Boot App |------>| Real External API |
| SimpleController|       | (jsonplaceholder) |
+-----------------+       +-------------------+

Test Flow:
+--------+       +-----------------+       +-------------------+
| Karate |------>| Spring Boot App |------>| Karate Mock Server|
| Test   |       | SimpleController|       | (local)           |
+--------+       +-----------------+       +-------------------+
```

## Repository Structure

- `src/main` - Contains the Spring Boot application code
- `src/test` - Contains test code and configurations
  - `java/.../KarateTests.java` - JUnit 5 test runner that starts the mock server
  - `resources/mocks/external-api-mock.feature` - Defines how the mock server responds
  - `resources/.../features/simple-controller.feature` - Karate tests for the API
  - `resources/application-test.properties` - Test configuration that redirects API calls to the mock
- `pom.xml` - Maven project configuration with dependencies

## How to Run

### Prerequisites

- JDK 11 or higher
- Maven (or use the included Maven wrapper)

### Running Tests

```bash
./mvnw test
```

This command will:

1. Compile the application
2. Start the Karate mock server
3. Start the Spring Boot application
4. Run the Karate tests against the application (which uses the mock)
5. Shut down everything when tests complete

## Key Files for Understanding Mocking

To understand the mocking setup, focus on these files:

- `src/test/java/com/karatetest/ws/KarateTests.java` - Test runner, mock server setup
- `src/test/resources/mocks/external-api-mock.feature` - Mock definition
- `src/test/resources/application-test.properties` - Configuration override
- `src/main/java/com/karatetest/ws/controller/SimpleController.java` - Application code using the external URL
- `src/test/resources/com/karatetest/ws/features/simple-controller.feature` - The actual test
- `src/test/resources/karate-config.js` - Karate configuration
