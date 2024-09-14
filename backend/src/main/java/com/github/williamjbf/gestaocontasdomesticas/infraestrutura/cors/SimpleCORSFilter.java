package com.github.williamjbf.gestaocontasdomesticas.infraestrutura.cors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class SimpleCORSFilter {

    public final CORSSettingsBeans corsSettingsBeans;

    @Autowired
    public SimpleCORSFilter(final CORSSettingsBeans corsSettingsBeans) {
        this.corsSettingsBeans = corsSettingsBeans;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                if (corsSettingsBeans.isCORSFilterEnable()) {
                    registry.addMapping("/api/**").allowedOrigins("*");
                }
            }
        };
    }

}