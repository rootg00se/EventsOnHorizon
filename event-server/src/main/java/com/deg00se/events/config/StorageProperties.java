package com.deg00se.events.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@ConfigurationProperties(prefix = "file-storage")
public record StorageProperties(
        String root,
        Paths paths,
        Set<String> allowedMimeTypes
) {
    public record Paths(
            String avatars,
            String banners
    ) {}
}
