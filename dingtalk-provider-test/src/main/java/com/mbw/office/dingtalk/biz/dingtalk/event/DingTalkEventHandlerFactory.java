/**
 *
 */
package com.mbw.office.dingtalk.biz.dingtalk.event;

import com.mbw.office.dingtalk.biz.dingtalk.EventHandler;
import com.smthit.lang.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bean
 *
 */
@Component
public class DingTalkEventHandlerFactory {
    private Map<String, EventHandler> handlers = new HashMap<String, EventHandler>();

    public void registEventHandler(EventHandler bpmsInstanceChangeEventHandler) {
        if (handlers.containsKey(bpmsInstanceChangeEventHandler.getName())) {
            throw new ServiceException("EventHandler " + bpmsInstanceChangeEventHandler.getName() + " 已经存在");
        }

        handlers.put(bpmsInstanceChangeEventHandler.getName(), bpmsInstanceChangeEventHandler);
    }

    public EventHandler getEventHandler(String eventName) {
        return handlers.get(eventName);
    }

    public void registCallback() {
        //TODO
    }
}