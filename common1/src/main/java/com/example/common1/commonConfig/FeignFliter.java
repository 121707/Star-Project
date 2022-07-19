package com.example.common1.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//@Configuration
public class FeignFliter {
        @Bean("requestInterceptor")
        public RequestInterceptor requestInterceptor(){
            return new RequestInterceptor() {
                @Override
                public void apply(RequestTemplate requestTemplate) {
//                通过请求上下文获取到初始请求
                    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//                通过拦截feign请求
                    HttpServletRequest request = requestAttributes.getRequest();
//                将初始请求的请求头同步到feign的请求中
                    requestTemplate.header("Cookie",request.getHeader("Cookie"));
//                System.out.println("ces1");
                }
            };
        }
}
