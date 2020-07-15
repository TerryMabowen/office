package com.mbw.office.common.util.json;

import cn.hutool.core.util.StrUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-07-06 09:37
 */
@Slf4j
public class GsonFactory {
    private static GsonFactory factory;

    @Getter
    private Gson gson;

    public static GsonFactory getInstance() {
        if (factory != null) {
            return factory;
        }

        synchronized (GsonFactory.class) {
            if (factory == null) {
                factory = new GsonFactory();
                
                factory.init();
            }
        }

        return factory;
    }

    public <T> String beanToJson(T t) {
        try {
            return gson.toJson(t);
        } catch (Exception e) {
            log.error("Object to json string error: {}", e.getMessage(), e);
            return StrUtil.EMPTY;
        }
    }

    public <T> T jsonToBean(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, clz);
        } catch (Exception e) {
            log.error("json string to Object error: {}", e.getMessage(), e);
            return clz.cast(new Object());
        }
    }

    public <T> List<T> jsonToList(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<List<T>>() {}.getType());
        } catch (Exception e) {
            log.error("json string to List error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {}.getType());
        } catch (Exception e) {
            log.error("json string to Map error: {}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    public <T> List<Map<String, T>> jsonToListMap(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<List<Map<String, T>>>() {}.getType());
        } catch (Exception e) {
            log.error("json string to List<Map> error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
    
    private void init() {
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setDateFormat("yyyy-MM-dd")
                .create();
    }
}
