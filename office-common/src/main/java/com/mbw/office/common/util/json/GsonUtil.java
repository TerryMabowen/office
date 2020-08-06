package com.mbw.office.common.util.json;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mabowen
 * @date 2020-07-03 16:17
 */
@Slf4j
public class GsonUtil {
    private static Gson gson;

    static {
        gson = new GsonBuilder()
                //设置日期格式化
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                //使@Expose注解生效，序列化和反序列化时忽略某字段
                .excludeFieldsWithoutExposeAnnotation()
                //下划线和驼峰转换
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

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
        } catch (Exception e) {
            log.error("json string to Object error: {}", e.getMessage(), e);
            return clz.cast(new Object());
        }
    }

    public static <T> List<T> jsonToList(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<List<T>>() {}.getType());
        } catch (Exception e) {
            log.error("json string to List<T> error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public static <T> Set<T> jsonToSet(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<Set<T>>() {}.getType());
        } catch (Exception e) {
            log.error("json string to Set<T> error: {}", e.getMessage(), e);
            return Collections.emptySet();
        }
    }

    public static Map<String, Object> jsonToMap(String jsonStr) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {}.getType());
        } catch (Exception e) {
            log.error("json string to Map<String, Object> error: {}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {}.getType());
        } catch (Exception e) {
            log.error("json string to Map<String, T> error: {}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    public static  <T> List<Map<String, T>> jsonToListMap(String jsonStr, Class<T> clz) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<List<Map<String, T>>>() {}.getType());
        } catch (Exception e) {
            log.error("json string to List<Map<String, T>> error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public static List<Map<String, Object>> jsonToListMap(String jsonStr) {
        try {
            return gson.fromJson(jsonStr, new TypeToken<List<Map<String, Object>>>() {}.getType());
        } catch (Exception e) {
            log.error("json string to List<Map<String, Object>> error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
