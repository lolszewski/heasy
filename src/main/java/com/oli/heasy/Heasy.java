package com.oli.heasy;

import com.codahale.metrics.health.HealthCheck;
import com.oli.heasy.configuration.ErrorMessageTextPlainBodyWriter;
import com.oli.heasy.configuration.HeasyConfiguration;
import com.oli.heasy.configuration.SpringConfiguration;
import com.oli.heasy.configuration.SpringContextLoaderListener;
import com.sun.jersey.api.json.JSONConfiguration;
import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.servlets.tasks.Task;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.Map;
import javax.servlet.ServletRegistration;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereFramework;
import org.atmosphere.cpr.AtmosphereServlet;
import org.springframework.beans.BeansException;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
public class Heasy extends Application<HeasyConfiguration> {

    @Override
    public void initialize(Bootstrap<HeasyConfiguration> btstrp) {
    }

    @Override
    public void run(HeasyConfiguration heasyConfiguration, Environment dropWizardEnvironment) throws Exception {

        AnnotationConfigWebApplicationContext applicationContext = this.creteContext(heasyConfiguration);

        this.registerAllHealthChecks(applicationContext, dropWizardEnvironment);
        this.registerResourcesAccessPaths(applicationContext, dropWizardEnvironment);
        this.registerTasks(applicationContext, dropWizardEnvironment);
        this.registerProviders(applicationContext, dropWizardEnvironment);

        ServletEnvironment servletEnvironment = dropWizardEnvironment.servlets();
        servletEnvironment.addServletListeners(new SpringContextLoaderListener(applicationContext));

        this.registerJerseyEnvironment(dropWizardEnvironment);
        this.registerAtmosphereServlet(dropWizardEnvironment);
    }

    private void registerAtmosphereServlet(Environment dropWizardEnvironment) {
        AtmosphereServlet atmospeherServlet = new AtmosphereServlet();
        AtmosphereFramework atmosphereFramework = atmospeherServlet.framework();
        atmosphereFramework.addInitParameter("com.sun.jersey.config.property.packages", HeasyNames.HEASY_MAIN_PACKAGE_NAME);
        atmosphereFramework.addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/json");
        atmosphereFramework.addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");
        atmosphereFramework.addInitParameter(ApplicationConfig.HEARTBEAT_INTERVAL_IN_SECONDS, "10");

        ServletRegistration.Dynamic servletHolder = dropWizardEnvironment.servlets().addServlet(HeasyNames.HEASY_ATMOSPHERE_SERVLET_NAME, atmospeherServlet);
        servletHolder.setAsyncSupported(true);
        servletHolder.setLoadOnStartup(0);

        servletHolder.addMapping("/websocket/*");
    }

    private void registerJerseyEnvironment(Environment dropWizardEnvironment) {
        JerseyEnvironment jerseyEnvironment = dropWizardEnvironment.jersey();
        jerseyEnvironment.setUrlPattern("/*");
        jerseyEnvironment.packages(HeasyNames.HEASY_MAIN_PACKAGE_NAME);
        jerseyEnvironment.enable(JSONConfiguration.FEATURE_POJO_MAPPING);
        jerseyEnvironment.register(new ErrorMessageTextPlainBodyWriter());
    }

    private void registerProviders(AnnotationConfigWebApplicationContext applicationContext, Environment dropWizardEnvironment) throws BeansException {
        Map<String, Object> providers = applicationContext.getBeansWithAnnotation(Provider.class);
        for (Map.Entry<String, Object> entry : providers.entrySet()) {
            dropWizardEnvironment.jersey().register(entry.getValue());
        }
    }

    private void registerTasks(AnnotationConfigWebApplicationContext applicationContext, Environment dropWizardEnvironment) throws BeansException {
        Map<String, Task> tasks = applicationContext.getBeansOfType(Task.class);
        for (Map.Entry<String, Task> entry : tasks.entrySet()) {
            dropWizardEnvironment.admin().addTask(entry.getValue());
        }
    }

    private void registerResourcesAccessPaths(AnnotationConfigWebApplicationContext applicationContext, Environment dropWizardEnvironment) throws BeansException {
        Map<String, Object> resources = applicationContext.getBeansWithAnnotation(Path.class);
        for (Map.Entry<String, Object> entry : resources.entrySet()) {
            dropWizardEnvironment.jersey().register(entry.getValue());
        }
    }

    private void registerAllHealthChecks(AnnotationConfigWebApplicationContext applicationContext, Environment dropWizardEnvironment) throws BeansException {
        Map<String, HealthCheck> healthChecks = applicationContext.getBeansOfType(HealthCheck.class);
        for (Map.Entry<String, HealthCheck> entry : healthChecks.entrySet()) {
            dropWizardEnvironment.healthChecks().register(entry.getKey(), entry.getValue());
        }
    }

    private AnnotationConfigWebApplicationContext creteContext(HeasyConfiguration configuration) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        AnnotationConfigWebApplicationContext parentContext = new AnnotationConfigWebApplicationContext();

        parentContext.refresh();
        parentContext.getBeanFactory().registerSingleton(HeasyNames.HEASY_SPRING_PARENT_APPLICATION_BEAN_CONFIGURATION_SINGLETON_NAME, configuration);
        parentContext.registerShutdownHook();
        parentContext.start();

        context.setParent(parentContext);
        context.register(SpringConfiguration.class);
        context.registerShutdownHook();
        context.refresh();
        context.start();

        return context;
    }

    public static void main(String[] args) throws Exception {
        new Heasy().run(args);
    }
}
