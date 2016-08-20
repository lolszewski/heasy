package com.oli.heasy.configuration;

import com.oli.heasy.HeasyNames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Lukasz Olszewski <lsolszewski@gmail.com>
 */
@Configuration
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = {HeasyNames.HEASY_MAIN_PACKAGE_NAME})
public class SpringConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
