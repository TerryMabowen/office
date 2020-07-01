package com.mbw.office.sso.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Mabowen
 * @date 14:42 2020-07-01
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.mbw.office"})
@EntityScan(basePackages = {"com.mbw.office.sso.entity"})
@EnableJpaRepositories(basePackages = {"com.mbw.office.sso.repositories"})
@EnableJpaAuditing
public class OfficeSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeSsoApplication.class, args);
    }

}
