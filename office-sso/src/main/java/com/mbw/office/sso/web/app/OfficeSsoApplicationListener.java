package com.mbw.office.sso.web.app;

import com.mbw.office.sso.spi.zookeeper.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author Mabowen
 * @date 2020-07-01 14:08
 */
@Service
public class OfficeSsoApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ZkClient zkClient;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        zkClient.buildZookeeperClient();
    }
}
