package com.mbw.office.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-07-03 16:17
 */
@Deprecated
public class GsonUtil {
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static Gson getGson() {
        return gson;
    }

    public static <T> String beanToJson(T t) {
        return gson.toJson(t);
    }

    public static <T> T jsonToBean(String jsonStr, Class<T> clz) {
        return gson.fromJson(jsonStr, clz);
    }

    public static <T> List<T> jsonToList(String jsonStr, Class<T> clz) {
        return gson.fromJson(jsonStr, new TypeToken<List<T>>() {}.getType());
    }

    public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clz) {
        return gson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {}.getType());
    }

    public static <T> List<Map<String, T>> jsonToListMap(String jsonStr, Class<T> clz) {
        return gson.fromJson(jsonStr, new TypeToken<List<Map<String, T>>>() {}.getType());
    }
}
