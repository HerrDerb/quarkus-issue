package org.acme;

import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareClientBuilder;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientProvider {

  @Inject
  FileShareConfig config;
  
  @Produces
  ShareClient shareClient(){
    log.info("Creating ShareClient with connectionstring: {}", config.connectionstring());
          return new ShareClientBuilder()
                .connectionString(config.connectionstring())
                .shareName(config.name())
                .buildClient();
  }
}
