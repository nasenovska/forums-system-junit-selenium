package org.master.testing.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for authentication values in application.properties
 */
@Configuration
@Getter
public class JsonWebTokenConfiguration {

    @Value("${authentication.entry-point}")
    private String entryPoint;

    @Value("${authorization.header}")
    private String header;

    @Value("${authorization.type}")
    private String type;

    @Value("${authorization.secret}")
    private String secret;

    @Value("${authorization.expiration}")
    private Long expiration;
}
