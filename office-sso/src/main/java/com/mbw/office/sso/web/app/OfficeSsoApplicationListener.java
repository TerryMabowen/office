package com.mbw.office.sso.web.app;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author Mabowen
 * @date 2020-07-01 14:08
 */
@Service
public class OfficeSsoApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
