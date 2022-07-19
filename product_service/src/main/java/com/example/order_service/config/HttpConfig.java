package com.example.order_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.websocket.OnError;


@Configuration
public class HttpConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://star.pro.com")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                .allowedHeaders("*");
    }
//    public HttpConfig() {
//    }
//
//    @Bean
//    @Order(-101)
//    public CorsFilter corsFilter() {
//        // 1. 添加cors配置信息
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("*");
//        // 设置是否发送cookie信息
//        config.setAllowCredentials(true);
//        // 设置允许请求的方式
//        config.addAllowedMethod("*");
//        // 设置允许的header
//        config.addAllowedHeader("*");
//        // 2. 为url添加映射路径
//        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
//        corsSource.registerCorsConfiguration("/**", config);
//        // 3. 返回重新定义好的corsSource
//        return new CorsFilter(corsSource);
//    }
}