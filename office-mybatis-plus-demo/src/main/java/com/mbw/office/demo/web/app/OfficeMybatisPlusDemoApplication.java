package com.mbw.office.demo.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mbw.office.demo"})
@EnableTransactionManagement
public class OfficeMybatisPlusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeMybatisPlusDemoApplication.class, args);
    }

}
