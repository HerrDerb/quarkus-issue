package org.acme;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class VertxTenantRequestFilter {
  private static final Logger log = LoggerFactory.getLogger(VertxTenantRequestFilter.class);
  @Inject
  RequestScopedBean requestScopedBean;

  @RouteFilter(1)
  public void filter(RoutingContext context) {
    log.info("Invoking VertxTenantRequestFilter");
    requestScopedBean.doSomething();
    context.next();
  }
}