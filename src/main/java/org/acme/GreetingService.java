package org.acme;

import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.ContextNotActiveException;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Path("/api")
public class GreetingService {
  private static final Logger log = LoggerFactory.getLogger(GreetingService.class);

  @Inject
  EntityManager entityManager;
  @Inject
  RequestScopedBean requestScopedBean;

  private final AtomicBoolean exceptionThrown = new AtomicBoolean(false);

  @Path("/greeting")
  @GET
  public String getGreeting() {
    exceptionThrown.set(false);
    log.info("Entering GreetingService.getGreeting");
    requestScopedBean.doSomething();
    // do uninterruptable long work
    QuarkusTransaction.requiringNew()
        .run(() -> entityManager.createNativeQuery("select pg_sleep(2);").getFirstResult());
    log.info("Finished waiting in GreetingService.getGreeting");

    try {
      // access request scoped bean againF
      requestScopedBean.doSomething();
    } catch (ContextNotActiveException ex) {
      exceptionThrown.set(true);
      throw ex;
    }
    return "Hi";
  }

  @Path("/exceptionThrown")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public boolean isExceptionThrown() {
    return exceptionThrown.get();
  }
}
