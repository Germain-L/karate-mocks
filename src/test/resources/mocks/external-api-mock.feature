# This feature file defines the behavior of the mock server
# that simulates the external API (jsonplaceholder.typicode.com) during tests.
Feature: Mock for External API

Background:
  # Define a reusable JSON response structure for the /todos/1 endpoint.
  * def todoResponse = { "userId": 1, "id": 1, "title": "delectus aut autem", "completed": false }

# This scenario handles requests specifically matching the path '/todos/1'.
# Karate's mock server matches incoming requests against scenarios.
Scenario: pathMatches('/todos/1')
  # When a request matches '/todos/1', set the response body to 'todoResponse'.
  * def response = todoResponse
  # Set the HTTP status code for the response to 200 (OK).
  * def status = 200

# Add more scenarios here for other paths or methods if needed, e.g.:
# Scenario: pathMatches('/posts') && methodIs('post')
#  * def response = { id: 101, message: 'Created' }
#  * def status = 201
