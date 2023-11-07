package org.acme;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import java.net.URI;
import java.util.Set;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;

@QuarkusTest
public class GreetingRessourceTest {

  @ConfigProperty(name = "client.quarkus.oidc.auth-server-url")
  String url;

  @Test
  void test() {
    var keycloak = Keycloak.getInstance("http://" + URI.create(url).getAuthority(), "master", "admin", "admin",
        "admin-cli");
    var realmRessource = keycloak.realm("testrealm");
    var keycloakHelper = new KeycloakUserHelperUtil(realmRessource);
    keycloakHelper.addOrReplaceUser("testuser", Set.of());
    String accessToken = new KeycloakTestClient().getRealmAccessToken("testrealm", "testuser", "testuser", "my-client");

    var answer = given().auth()
        .oauth2(accessToken).queryParam("name", "quarkus").get("hello").then().statusCode(200).extract().asString();
        
    assertEquals("hello quarkus", answer);

  }
}
