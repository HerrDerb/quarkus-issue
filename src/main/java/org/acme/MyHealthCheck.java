package org.acme;

import io.smallrye.health.api.Wellness;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Wellness
@ApplicationScoped
public class MyHealthCheck implements HealthCheck {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    RequestInfo requestInfo;

    @Override
    public HealthCheckResponse call() {
        logger.info("HealthCheck with request info '{}'", requestInfo.getInfo());
        return HealthCheckResponse.builder().up().name(requestInfo.getInfo()).build();
    }

}
