package com.mbw.office.cloud.web.config;

import com.mbw.office.cloud.web.ctl.exception.CustomExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 18:14
 */
@Configuration
@EnableConfigurationProperties({ServerProperties.class, ResourceProperties.class})
public class ErrorHandlerConfig {
    private ServerProperties serverProperties;

    private ApplicationContext applicationContext;

    private ResourceProperties resourceProperties;

    private ServerCodecConfigurer serverCodecConfigurer;

    private ObjectProvider<List<ViewResolver>> viewResolversProvider;

    private List<ViewResolver> viewResolvers;

    public ErrorHandlerConfig(ServerProperties serverProperties,
                              ApplicationContext applicationContext,
                              ResourceProperties resourceProperties,
                              ServerCodecConfigurer serverCodecConfigurer,
                              ObjectProvider<List<ViewResolver>> viewResolversProvider,
                              List<ViewResolver> viewResolvers) {
        this.serverProperties = serverProperties;
        this.applicationContext = applicationContext;
        this.resourceProperties = resourceProperties;
        this.serverCodecConfigurer = serverCodecConfigurer;
        this.viewResolversProvider = viewResolversProvider;
        this.viewResolvers = viewResolvers;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes) {
        CustomExceptionHandler exceptionHandler = new CustomExceptionHandler(
                errorAttributes,
                this.resourceProperties,
                this.serverProperties.getError(),
                this.applicationContext);
        exceptionHandler.setViewResolvers(this.viewResolvers);
        exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
        return exceptionHandler;
    }
}
