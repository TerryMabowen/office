package com.mbw.office.demo.web.config;

import com.mbw.office.common.helper.ApplicationContextHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mabowen
 * @date 2020-07-01 11:42
 */
@Configuration
public class AppConfig {
    @Bean
    public ApplicationContextHelper applicationContextHelper() {
        return new ApplicationContextHelper();
    }
 }
