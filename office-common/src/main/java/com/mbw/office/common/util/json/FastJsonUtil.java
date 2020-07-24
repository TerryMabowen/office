package com.mbw.office.common.util.json;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * fastjson工具类
 *
 * @author Mabowen
 * @date 2020-07-24 18:39
 */
public class FastJsonUtil {

    public static String toJson(String str) {
        if (StrUtil.isNotBlank(str)) {
            return JSON.toJSONString(str);
        }

        return StrUtil.EMPTY;
    }

    public static JSONObject getJsonObject(String json) {
        return JSON.parseObject(json);
    }

    public static String getJsonByName(JSONObject jsonObject, String name) {
        return jsonObject.getString(name);
    }

    public static String beanToJson(Object obj) {
        if (obj != null) {
            return JSON.toJSONString(obj);
        }

        return StrUtil.EMPTY;
    }

    public static <T> T jsonToBean(String json, Class<T> clz) {
        if (StrUtil.isNotBlank(json) && clz != null) {
            return JSON.parseObject(json, clz);
        }

        return null;
    }

    public static <T> List<T> jsonToList(String json, Class<T> clz) {
        if (StrUtil.isNotBlank(json) && clz != null) {
            return JSON.parseObject(json, new TypeReference<List<T>>() {});
        }

        return Collections.emptyList();
    }

    public static <T> Set<T> jsonToSet(String json, Class<T> clz) {
        if (StrUtil.isNotBlank(json) && clz != null) {
            return JSON.parseObject(json, new TypeReference<Set<T>>() {});
        }

        return Collections.emptySet();
    }

    public static <T> Map<String, T> jsonToMap(String json, Class<T> clz) {
        if (StrUtil.isNotBlank(json) && clz != null) {
            return JSON.parseObject(json, new TypeReference<Map<String, T>>() {});
        }

        return Collections.emptyMap();
    }

    public static <T> List<Map<String, T>> jsonToListMap(String json, Class<T> clz) {
        if (StrUtil.isNotBlank(json) && clz != null) {
            return JSON.parseObject(json, new TypeReference<List<Map<String, T>>>() {});
        }

        return Collections.emptyList();
    }
}
