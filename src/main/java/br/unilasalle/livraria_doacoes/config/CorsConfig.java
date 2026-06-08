package br.unilasalle.livraria_doacoes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Read allowed origins from environment variable APP_CORS_ALLOWED_ORIGINS (comma separated)
        // If not set, default to allowing all origins (same as previous behavior)
        String allowed = System.getenv("APP_CORS_ALLOWED_ORIGINS");
        String[] origins = allowed != null && !allowed.isBlank() ? allowed.split(",") : new String[] {"*"};

        registry.addMapping("/**")
                .allowedOrigins(origins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}

