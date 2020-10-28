package com.mbw.office.dingtalk.admin.app;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {"com.mbw.office.dingtalk", "ai.bell.dingtalk.gw"})
@EnableDubbo(scanBasePackages = {"ai.bell.dingtalk.gw.provider.spi"})
public class DingtalkProviderTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingtalkProviderTestApplication.class, args);
    }

}
