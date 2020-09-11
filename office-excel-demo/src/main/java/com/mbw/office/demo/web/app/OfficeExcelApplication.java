package com.mbw.office.demo.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Mabowen
 * @date 2020-09-11 16:35
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {"com.mbw.office.demo"})
public class OfficeExcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeExcelApplication.class, args);
    }
}

