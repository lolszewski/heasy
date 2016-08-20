package com.oli.heasy.configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
public class SpringContextLoaderListener implements ServletContextListener {

    private final AnnotationConfigWebApplicationContext context;

    public SpringContextLoaderListener(AnnotationConfigWebApplicationContext context) {
        super();
        this.context = context;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
        context.setServletContext(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}