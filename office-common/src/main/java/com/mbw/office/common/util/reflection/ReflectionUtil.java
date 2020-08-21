package com.mbw.office.common.util.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 反射工具类
 *
 * @author Mabowen
 * @date 2020-08-21 17:32
 */
public class ReflectionUtil {
    private static final String GET = "get";
    private static final String SET = "set";

    public static Field[] getAllFields(Class<?> clz) {
        List<Field> fieldList = new ArrayList<>();
        Field[] declaredFields = clz.getDeclaredFields();
        if (declaredFields.length > 0) {
            fieldList.addAll(Arrays.asList(declaredFields));
        }

        Class<?> superclass = clz.getSuperclass();
        if (superclass != null) {
            Field[] superFields = getAllFields(superclass);
            if (superFields.length > 0) {
                fieldList.addAll(Arrays.asList(superFields));
            }
        }

        Field[] fields = new Field[fieldList.size()]
        return fieldList.toArray(fields);
    }
}
