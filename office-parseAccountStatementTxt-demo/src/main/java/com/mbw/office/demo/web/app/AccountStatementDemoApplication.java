package com.mbw.office.demo.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Mabowen
 * @date 2020-07-17 14:45
 */
@ComponentScan(basePackages = "com.mbw.office.demo")
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class AccountStatementDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountStatementDemoApplication.class, args);
    }
}
