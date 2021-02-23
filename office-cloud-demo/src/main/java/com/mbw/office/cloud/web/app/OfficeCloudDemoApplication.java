package com.mbw.office.cloud.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Mabowen
 * @date 2021-01-21 14:01
 */
@SpringBootApplication(scanBasePackages = {"com.mbw.office.cloud"})
@EnableDiscoveryClient
@EnableTransactionManagement
public class OfficeCloudDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeCloudDemoApplication.class, args);
    }

}
