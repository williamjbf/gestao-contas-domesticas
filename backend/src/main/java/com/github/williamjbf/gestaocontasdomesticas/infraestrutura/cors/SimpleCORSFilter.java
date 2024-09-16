package com.github.williamjbf.gestaocontasdomesticas.infraestrutura.cors;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.Optional;

/**
 * A `SimpleCORSFilter` é uma implementação personalizada de filtro para configurar
 * o CORS (Cross-Origin Resource Sharing) em uma aplicação web.
 */
@Component
public class SimpleCORSFilter implements Filter {

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
                    registry.addMapping("/api/**")
                            .allowedOriginPatterns("*")
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                            .allowedHeaders("Access-Control-Allow-Headers", "Authorization", "Content-Type", "Accept", "Refreshed-Token", "Content-Disposition")
                            .allowCredentials(true);
                }
            }
        };
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        if (!corsSettingsBeans.isCORSFilterEnable()) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final Optional<String> origin = Optional.ofNullable(request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Origin", origin.orElse(""));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD, PATCH, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, Refreshed-Token, Content-Disposition");
        response.setHeader("Access-Control-Expose-Headers", "Authorization, Content-Type, Accept, Refreshed-Token, Content-Disposition");

        final String requestedResourcePath = request.getRequestURI().substring(request.getContextPath().length());

        /**
         * Esses headers (allowCredentials, allowOrigin) não são aceitos pelo Sockjs,
         * então devem não devem ser adicionados caso a requisição seja para
         * a URI destinada à websockets
         */
        if (!requestedResourcePath.endsWith("/info")) {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }

        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }

}