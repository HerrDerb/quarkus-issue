package org.acme;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareClientBuilder;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class FileResourceTest {

  private ShareClient testShareClient;

  @BeforeEach
  void setup() {
    Config config = ConfigProvider.getConfig();
    String shareName = config.getValue("file-share.name", String.class);
    String connectionstring = config.getValue("file-share.connectionstring", String.class);
    testShareClient = new ShareClientBuilder()
        .connectionString(connectionstring)
        .shareName(shareName)
        .buildClient();

    testShareClient.createIfNotExists();
  }

  @AfterEach
  public void cleanup() {
    testShareClient.deleteIfExists();
  }

  @Test
  void getFileNames() {
    // generate some files
    given()
        .when().log().all().queryParam("count", 2).post("/file/generateRandomFiles").then().log().all().statusCode(204);

    // verify that files were created
    var fileCount = testShareClient.getRootDirectoryClient().listFilesAndDirectories().stream().count();
    assertEquals(2, fileCount);

    // listFileNames
    var fileNames = given()
        .when().get("/file/getFileNames").then().extract().as(new TypeRef<List<String>>() {
        });
    assertEquals(2, fileNames.size());

  }

}