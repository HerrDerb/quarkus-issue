package org.acme;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class VertxFilter {
    
    @Inject
    RequestInfo requestInfo;

    @RouteFilter(1)
    public void filter(RoutingContext routingContext){
        String info = routingContext.request().getParam("info");
        requestInfo.setInfo(info);
        routingContext.next();
    }
}
