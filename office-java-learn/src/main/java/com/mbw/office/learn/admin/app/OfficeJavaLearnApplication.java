package com.mbw.office.learn.admin.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author mabowen
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.mbw.office.learn"})
public class OfficeJavaLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeJavaLearnApplication.class, args);
    }

}
