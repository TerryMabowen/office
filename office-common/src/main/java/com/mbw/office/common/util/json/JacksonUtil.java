package com.mbw.office.common.util.json;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Jackson 工具类
 *
 * @author Mabowen
 * @date 2020-05-20 17:08
 */
@Slf4j
public class JacksonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper()
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String beanToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Object to json string error: {}", e.getMessage(), e);
            return StrUtil.EMPTY;
        }
    }

    public static <T> T jsonToBean(String jsonStr, Class<T> clz) {
        try {
            return objectMapper.readValue(jsonStr, clz);
        } catch (IOException e) {
            log.error("json string to Object error: {}", e.getMessage(), e);
            return clz.cast(new Object());
        }
    }

    public static <T> List<T> jsonToList(String jsonStr, Class<T> clz) {
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<List<T>>(){});
        } catch (IOException e) {
            log.error("json string to List error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public static <T> Set<T> jsonToSet(String jsonStr, Class<T> clz) {
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<Set<T>>(){});
        } catch (IOException e) {
            log.error("json string to Set error: {}", e.getMessage(), e);
            return Collections.emptySet();
        }
    }

    public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clz) {
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>(){});
        } catch (IOException e) {
            log.error("json string to Map error: {}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    public static <T> List<Map<String, T>> jsonToListMap(String jsonStr, Class<T> clz) {
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<List<Map<String, T>>>(){});
        } catch (IOException e) {
            log.error("json string to List<Map> error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public static <T> T jsonToComplicatedBean(String jsonStr, JavaType javaType) {
        try {
            return objectMapper.readValue(jsonStr, javaType);
        } catch (Exception e) {
            log.error("json string to List<Map> error: {}", e.getMessage(), e);
            return javaType.getTypeHandler();
        }
    }

    public static JavaType getConstructParametricJavaType(Class<?> parametrized, Class<?>... parameterClasses) {
        return objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }
}
