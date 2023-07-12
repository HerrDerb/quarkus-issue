package org.acme.issues;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class VertxRoutFilter {

    @Inject
    RequestScopedBean requestScopedBean;
    
    @RouteFilter(1000)
    public void filter(RoutingContext context) {
        String paramValue = context.request().getParam("myParam");
        requestScopedBean.setInfo(paramValue);
        log.warn("## value from field 'info' of request scoped bean: {} ", requestScopedBean.getInfo());
        context.next();
    }
}
