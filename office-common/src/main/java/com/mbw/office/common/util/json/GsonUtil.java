package com.mbw.office.common.util.json;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-07-03 16:17
 */
@Slf4j
@Deprecated
public class GsonUtil {
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static Gson getGson() {
        return gson;
    }

    public static <T> String beanToJson(T t) {
        try {
            return gson.toJson(t);
        } catch (Exception e) {
            log.error("Object to json string error: {}", e.getMessage(), e);
            return StrUtil.EMPTY;
        }
    }

    public static <T> T jsonToBean(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, clz);
        } catch (JsonSyntaxException e) {
            log.error("json string to Object error: {}", e.getMessage(), e);
            return clz.cast(new Object());
        }
    }

    public static <T> List<T> jsonToList(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<List<T>>() {}.getType());
        } catch (JsonSyntaxException e) {
            log.error("json string to List error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {}.getType());
        } catch (JsonSyntaxException e) {
            log.error("json string to Map error: {}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    public static  <T> List<Map<String, T>> jsonToListMap(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<List<Map<String, T>>>() {}.getType());
        } catch (JsonSyntaxException e) {
            log.error("json string to List<Map> error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
