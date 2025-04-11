// Standard Karate configuration file.
// This file is automatically loaded by Karate to set up the test environment.
function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev'; // default environment
  }

  // Base configuration object.
  var config = {
    // Define the base URL for API tests.
    // karate.properties reads Java System Properties.
    // 'server.port' is set dynamically in KarateTests.java before tests run,
    // pointing to the random port where the Spring Boot app is running.
    baseUrl: 'http://localhost:' + karate.properties['server.port']
  };

  // Configure connection and read timeouts (good practice).
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);

  // Return the configuration object.
  return config;
}
