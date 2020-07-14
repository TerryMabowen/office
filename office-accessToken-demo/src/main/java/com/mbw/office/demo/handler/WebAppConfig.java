package com.mbw.office.demo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-14 20:24
 */
@Configuration
public class WebAppConfig {
    @Autowired
    private AccessTokenInterceptor accessTokenInterceptor;

    @Bean
    public WebMvcConfigurer WebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(accessTokenInterceptor).addPathPatterns("/openApi/*");
            };
        };
    }
}
