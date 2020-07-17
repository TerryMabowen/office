package com.mbw.office.demo.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-17 17:27
 */
@ComponentScan(basePackages = "com.mbw.office.demo")
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class OfficeQiYeWeiXinApplication {
    public static void main(String[] args) {
        SpringApplication.run(OfficeQiYeWeiXinApplication.class, args);
    }
}
