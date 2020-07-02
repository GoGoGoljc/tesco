package com.zkdj.orderitem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SJXY
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**
     *配置跨域
     * @param registry
     *
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST","GET","DELETE","PATCH","PUT")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}

