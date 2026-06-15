package br.unilasalle.livraria_doacoes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PathMatchConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Force legacy Ant-based path matching to avoid conflicts with springdoc swagger-ui patterns
        // This is equivalent to setting 'spring.mvc.pathmatch.matching-strategy=ant_path_matcher'
        configurer.setPatternParser(null);
    }
}

