package org.acme;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
class GreetingServiceTest {
  private static final Logger log = LoggerFactory.getLogger(GreetingServiceTest.class);

  @Test
  void callGreetingEndpoint_whenCancelRequest_shouldNotThrowNoActiveContextExceptionInBackend() {
    setupRestAssured(100);
    // make requests that times out
    try {
      given()
          .when().get("/api/greeting")
          .then()
          .statusCode(200);
    } catch (Exception e) {
      log.info("Request timed out and therefor canceled");
    }

    // wait for backend code to complete
    Uni.createFrom().voidItem().emitOn(Infrastructure.getDefaultExecutor()).onItem().delayIt().by(Duration.ofSeconds(3))
        .await().indefinitely();

    // check if exception was thrown in application
    setupRestAssured(10000);
    var exceptionThrown = given()
        .when().get("/api/exceptionThrown")
        .then().extract().body().as(Boolean.class);

    // exception should not have been thrown
    assertFalse(exceptionThrown);
  }

  private void setupRestAssured(int timeoutMs) {
    RestAssured.config = RestAssuredConfig.config()
        .httpClient(HttpClientConfig.httpClientConfig()
            .setParam("http.socket.timeout", timeoutMs)
            .setParam("http.connection.timeout", timeoutMs));
  }
}
