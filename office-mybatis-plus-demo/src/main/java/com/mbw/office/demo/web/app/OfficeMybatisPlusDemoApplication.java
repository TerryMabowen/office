package com.mbw.office.demo.web.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mbw.office.demo"})
@MapperScan(basePackages = {"com.mbw.office.demo.mapper"})
public class OfficeMybatisPlusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeMybatisPlusDemoApplication.class, args);
    }

}
