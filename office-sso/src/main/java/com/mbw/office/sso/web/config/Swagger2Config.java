package com.mbw.office.sso.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Mabowen
 * @date 2020-07-16 09:52
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    //api接口包扫描路径
    private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.mbw.office.sso.web.controller";
    //api版本号
    public static final String VERSION = "1.0.0";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // API 接口扫描
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置文档的标题
                .title("单点登录系统 demo")
                // 设置文档的描述
                .description("单点登录系统 demo API 接口文档")
                // 设置文档的版本信息
                .version(VERSION)
                // 作者
                .contact(new Contact("小文", "https://me.csdn.net/TerryMabowen", "2636965214@qq.com"))
                // 服务条款
                .termsOfServiceUrl("NO terms of service")
                // 设置文档的License信息
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }
}
