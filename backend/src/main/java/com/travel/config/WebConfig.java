package com.travel.config;

import com.travel.service.StorageService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Sert les images du dossier uploads/ sous l'URL /uploads/**. */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final StorageService storage;
    public WebConfig(StorageService storage) { this.storage = storage; }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(storage.getDossier().toUri().toString());
    }
}
