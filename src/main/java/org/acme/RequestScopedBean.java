package org.acme;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class RequestScopedBean {

  private static final Logger log = LoggerFactory.getLogger(RequestScopedBean.class);
  private UUID id;

  @PostConstruct
  public void init() {
    id = UUID.randomUUID();
  }
  public void doSomething() {
    log.info("Doing something in RequestScopedBean {}", id);
  }
}
