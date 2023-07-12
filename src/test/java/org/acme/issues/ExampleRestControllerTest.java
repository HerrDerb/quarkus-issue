package org.acme.issues;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ExampleRestControllerTest {

    @Test
    void shouldReturnParamValue(){
        String paramValue = "testParamValue";
        var rawResponse = given().basePath("api").queryParam("myParam", paramValue).get().then().statusCode(200).extract().asByteArray();
        String response = new String(rawResponse);

        assertEquals(paramValue, response);
    }
}
