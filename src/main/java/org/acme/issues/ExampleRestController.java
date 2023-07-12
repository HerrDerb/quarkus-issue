package org.acme.issues;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Path("api")
public class ExampleRestController {

    @Inject
    RequestScopedBean requestScopedBean;

    @GET
    public String getRequetScopedInfo(){
            log.warn("## Handeling getRequetScopedInfo");
            String info = requestScopedBean.getInfo();
            log.warn("## value from field 'info' of request scoped bean: {}", info);
            return info;
    }
    
}
