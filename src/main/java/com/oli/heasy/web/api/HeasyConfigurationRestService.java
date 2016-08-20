package com.oli.heasy.web.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
@RestController
@Path(value = HeasyWebApiPaths.CONFIGURATION_PATH)
public class HeasyConfigurationRestService {

    @GET
    @Path(value = "/health")
    public Object health() {
        return "Ok";
    }
}
