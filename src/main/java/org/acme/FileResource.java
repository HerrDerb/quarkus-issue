package org.acme;

import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.models.ShareFileItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.IntStream;

@Path("/file")
@ApplicationScoped
public class FileResource {

  @Inject
  ShareClient shareClient;

  @GET
  @Path("/getFileNames")
  @Produces("application/json")

  public List<String> getFileNames() {
    return shareClient.getRootDirectoryClient().listFilesAndDirectories().stream()
        .map(ShareFileItem::getName).toList();
  }

  @POST
  @Path("/generateRandomFiles")
  public void generateRandomFiles(@QueryParam("count") int count) {
    IntStream.range(0, count).forEach(nullValue -> {
      shareClient.getRootDirectoryClient().createFile("file" + nullValue + ".txt", 1024);
    });
  }
}