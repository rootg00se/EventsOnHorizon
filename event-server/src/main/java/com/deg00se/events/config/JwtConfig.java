package com.deg00se.events.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {
    private long refreshExpiration;
    private long accessExpiration;

    private String refreshSecret;
    private String accessSecret;
}
