package com.htt.ecourse.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8082") // Cho phép origin này
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Cho phép các phương thức
                .allowedHeaders("*");
    }
}
