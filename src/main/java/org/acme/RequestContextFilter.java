package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@ApplicationScoped
@PreMatching
public class RequestContextFilter implements ContainerRequestFilter {
  private static final Logger log = LoggerFactory.getLogger(RequestContextFilter.class);
  @Inject
  RequestScopedBean requestScopedBean;

  @Override
  public void filter(ContainerRequestContext context) {
    log.info("ContainerRequestFilter is invoked");
    requestScopedBean.doSomething();
  }

}
