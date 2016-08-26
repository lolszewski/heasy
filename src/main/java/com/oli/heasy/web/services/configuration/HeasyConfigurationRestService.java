package com.oli.heasy.web.services.configuration;

import com.oli.heasy.web.services.HeasyWebApiPaths;
import com.oli.heasy.web.services.base.HeasyWebService;
import javax.ws.rs.Path;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
@RestController
@Path(value = HeasyWebApiPaths.CONFIGURATION)
public class HeasyConfigurationRestService extends HeasyWebService {
}
