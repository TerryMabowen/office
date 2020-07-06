package com.mbw.office.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-07-06 09:37
 */
public class GsonFactory {
    private static GsonFactory single;

    @Getter
    private Gson gson;

    public static GsonFactory getInstance() {
        if (single != null) {
            return single;
        }

        synchronized (GsonFactory.class) {
            if (single == null) {
                single = new GsonFactory();
            }

            single.init();
        }

        return single;
    }

    public <T> String beanToJson(T t) {
        return gson.toJson(t);
    }

    public <T> T jsonToBean(String jsonStr, Class<T> clz) {
        return gson.fromJson(jsonStr, clz);
    }

    public <T> List<T> jsonToList(String jsonStr, Class<T> clz) {
        return gson.fromJson(jsonStr, new TypeToken<List<T>>() {}.getType());
    }

    public <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clz) {
        return gson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {}.getType());
    }

    public <T> List<Map<String, T>> jsonToListMap(String jsonStr, Class<T> clz) {
        return gson.fromJson(jsonStr, new TypeToken<List<Map<String, T>>>() {}.getType());
    }

    private void init() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }
}
