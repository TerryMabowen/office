package com.mbw.office.common.http.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.mbw.office.common.exception.ServiceException;
import lombok.Getter;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * OkHttp事件工厂
 *
 * @author Mabowen
 * @date 2020-07-10 14:39
 */
public class OkHttpEventBusFactory {
    private static OkHttpEventBusFactory factory;

    private final String OKHTTP_DEFAULT_BUS = "OKHTTP_DEFAULT_BUS";

    private final String OKHTTP_DEFAULT_ASYNC_BUS = "OKHTTP_DEFAULT_ASYNC_BUS";

    private EventBus defaultEventBus;

    private EventBus defaultAsyncEventBus;

    @Getter
    private ExecutorService asyncThreadPoolExecutor;

    public static OkHttpEventBusFactory getInstance() {
        if (factory != null) {
            return factory;
        }

        synchronized (OkHttpEventBusFactory.class) {
            if (factory == null) {
                factory = new OkHttpEventBusFactory();
                factory.init();
            }
        }

        return factory;
    }

    private void init() {
        asyncThreadPoolExecutor = new ScheduledThreadPoolExecutor(12,
                new BasicThreadFactory
                        .Builder()
                        .namingPattern("ok-event-pool-%d")
                        .daemon(true)
                        .build());

        defaultEventBus = new EventBus(OKHTTP_DEFAULT_BUS);
        defaultAsyncEventBus = new AsyncEventBus(OKHTTP_DEFAULT_ASYNC_BUS, asyncThreadPoolExecutor);
    }

    /**
     * 注册消息总线
     *
     * @param listener
     */
    public void registerDefaultEventBus(Object listener) {
        defaultEventBus.register(listener);
    }

    public void registerDefaultAsyncEventBus(Object listener) {
        defaultAsyncEventBus.register(listener);
    }

    /**
     * 注册指定的消息总线
     *
     * @param eventBusName
     * @param listener
     */
    public void registerEventBus(String eventBusName, Object listener) {
        if (OKHTTP_DEFAULT_BUS.equalsIgnoreCase(eventBusName)) {
            defaultEventBus.register(listener);
            return;
        }

        if (OKHTTP_DEFAULT_ASYNC_BUS.equalsIgnoreCase(eventBusName)) {
            defaultAsyncEventBus.register(this);
            return;
        }

        throw new ServiceException(MessageFormat.format("{0}消息中线不存在", eventBusName));
    }

    /**
     * 从默认总线上下线
     *
     * @param listener
     */
    public void unregisterDefaultEventBus(Object listener) {
        defaultEventBus.unregister(listener);
    }

    /**
     * 从默认总线上下线
     *
     * @param listener
     */
    public void unregisterDefaultAsyncEventBus(Object listener) {
        defaultAsyncEventBus.unregister(listener);
    }

    /**
     * 从指定的总线上下线
     *
     * @param eventBusName
     * @param listener
     */
    public void unregisterEventBus(String eventBusName, Object listener) {
        if (OKHTTP_DEFAULT_BUS.equalsIgnoreCase(eventBusName)) {
            defaultEventBus.unregister(listener);
            return;
        }

        if (OKHTTP_DEFAULT_ASYNC_BUS.equalsIgnoreCase(eventBusName)) {
            defaultAsyncEventBus.unregister(listener);
            return;
        }

        throw new ServiceException(MessageFormat.format("{0}消息中线不存在", eventBusName));
    }

    /**
     * 获取默认总线
     *
     * @return
     */
    public EventBus getDefaultEventBus() {
        return defaultEventBus;
    }

    /**
     * 获取默认的异步消息总线
     *
     * @return
     */
    public EventBus getDefaultAsyncEventBus() {
        return defaultAsyncEventBus;
    }

    /**
     * 获取指定的总线
     *
     * @param eventBusName
     * @return
     */
    public EventBus getEventBus(String eventBusName) {
        if (OKHTTP_DEFAULT_BUS.equalsIgnoreCase(eventBusName)) {
            return defaultEventBus;
        }

        if (OKHTTP_DEFAULT_ASYNC_BUS.equalsIgnoreCase(eventBusName)) {
            return defaultAsyncEventBus;
        }

        throw new ServiceException(MessageFormat.format("{0}消息中线不存在", eventBusName));
    }
}
