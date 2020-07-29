package com.mbw.office.common.util.json;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

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

    public static String getJsonByKey(JSONObject jsonObject, String key) {
        return jsonObject.getString(key);
    }

    public static String getJsonByKey(String json, String key) {
        if (StrUtil.isNotBlank(json) && StrUtil.isNotBlank(key)) {
            JSONObject jsonObject = getJsonObject(json);
            return jsonObject.getString(key);
        }
        return StrUtil.EMPTY;
    }

    public static String beanToJson(Object obj) {
        if (obj != null) {
            return JSON.toJSONString(obj);
        }

        return StrUtil.EMPTY;
    }

    public static <T> T jsonToBean(String json, Class<T> clz) {
        return JSON.parseObject(json, clz);
    }

    public static <T> List<T> jsonToList(String json, Class<T> clz) {
        return JSONArray.parseArray(json, clz);
    }

    public static Map<String, Object> jsonToMap(String json) {
        return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
    }

    public static <T> Map<String, T> jsonToMap(String json, Class<T> clz) {
        return JSON.parseObject(json, new TypeReference<Map<String, T>>() {});
    }
}
