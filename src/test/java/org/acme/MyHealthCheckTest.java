package org.acme;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class MyHealthCheckTest {



    @Test
    void executeHealthCheck() {
        String info = "testvalue";
        HealthCheckData healthCheckData = given()
                .queryParam("info", "testvalue")
                .get("/q/health")
                .then()
                .log().ifError()
                .statusCode(200)
                .extract()
                .as(HealthCheckData.class);
        assertTrue(healthCheckData.checks.stream().anyMatch(response -> response.getName().equals(info)));
    }

    @Test
    void executeHealthCheckAgain() {
        String info = "testvalue";
        HealthCheckData healthCheckData = given()
                .queryParam("info", "testvalue")
                .get("/q/health")
                .then()
                .log().ifError()
                .statusCode(200)
                .extract()
                .as(HealthCheckData.class);
        assertTrue(healthCheckData.checks.stream().anyMatch(response -> response.getName().equals(info)));
    }

    static class HealthCheckData {
        public HealthCheckResponse.Status status;
        public List<HealthCheckResponse> checks;
    }
}
