package com.oli.heasy.web.services.base;

import com.oli.heasy.web.services.HeasyWebApiPaths;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
@RestController
@Path(value = HeasyWebApiPaths.BASE)
public abstract class HeasyWebService {
    @GET
    @Path(value = HeasyWebApiPaths.HEALTH_METHOD)
    public Object health() {
        return "Ok";
    }
}
