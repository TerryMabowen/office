package com.mbw.office.sso.zookeeper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * zookeeper
 *
 * @author Mabowen
 * @date 2020-05-06 11:24
 */
@Data
@Component
public class ZkConfig {
    @Value("${office.sso.zookeeper.address}")
    private String address;

    @Value("${office.sso.zookeeper.baseSleepTimeMs}")
    private int baseSleepTimeMs;

    @Value("${office.sso.zookeeper.maxRetry}")
    private int maxRetry;

    @Value("${office.sso.zookeeper.sessionTimeout}")
    private int sessionTimeout;

    @Value("${office.sso.zookeeper.connectTimeout}")
    private int connectTimeout;

    @Value("${office.sso.zookeeper.namespace}")
    private String namespace;
}
