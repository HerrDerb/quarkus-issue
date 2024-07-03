package org.acme;

import io.quarkus.runtime.Startup;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriteService {

  @Startup
  void writeFile() throws IOException {
    Files.createDirectories(Path.of("test"));
  }
  
}
