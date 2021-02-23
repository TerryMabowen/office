package com.mbw.office.cloud.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Mabowen
 * @date 2021-01-21 14:01
 */
@SpringBootApplication(scanBasePackages = {"com.mbw.office.cloud"})
@EnableDiscoveryClient
public class OfficeCloudDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeCloudDemoApplication.class, args);
    }

}
