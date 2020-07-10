package com.mbw.office.common.util.json;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-07-03 10:53
 */
@Slf4j
public class JacksonFactory {
    private static JacksonFactory factory;

    @Getter
    private ObjectMapper objectMapper;

    public static JacksonFactory getInstance() {
        if (factory != null) {
            return factory;
        }

        synchronized (JacksonFactory.class) {
            if (factory == null) {
                factory = new JacksonFactory();

                factory.init();
            }
        }

        return factory;
    }

    public String beanToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Object to json string error: {}", e.getMessage(), e);
            return StrUtil.EMPTY;
        }
    }

    public <T> T jsonToBean(String jsonStr, Class<T> clz) {
        try {
            return objectMapper.readValue(jsonStr, clz);
        } catch (IOException e) {
            log.error("json string to Object error: {}", e.getMessage(), e);
            return clz.cast(new Object());
        }
    }

    public <T> List<T> jsonToList(String jsonStr, Class<T> clz) {
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<List<T>>(){});
        } catch (IOException e) {
            log.error("json string to List error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clz) {
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<Map<String, T>>(){});
        } catch (IOException e) {
            log.error("json string to Map error: {}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    public <T> List<Map<String, T>> jsonToListMap(String jsonStr, Class<T> clz) {
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<List<Map<String, T>>>(){});
        } catch (IOException e) {
            log.error("json string to List<Map> error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public <T> T jsonToComplicatedBean(String jsonStr, JavaType javaType) {
        try {
            return objectMapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            log.error("json string to List<Map> error: {}", e.getMessage(), e);
            return javaType.getTypeHandler();
        }
    }

    public final JavaType getConstructParametricJavaType(Class<?> parametrized, Class<?>... parameterClasses) {
        return objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    private void init() {
        objectMapper = new ObjectMapper();
    }
}
