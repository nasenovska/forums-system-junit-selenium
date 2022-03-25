package org.master.testing.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration of CORS mapping using {@link CorsRegistry}
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private static final long MAX_SEC_AGE = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
                .maxAge(MAX_SEC_AGE);
    }
}
