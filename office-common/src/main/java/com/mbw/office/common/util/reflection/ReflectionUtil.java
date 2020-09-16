package com.mbw.office.common.util.reflection;

import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.conversion.ColumnConvertUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

        Field[] fields = new Field[fieldList.size()];
        return fieldList.toArray(fields);
    }

    public static Field getFieldByName(Class<?> clz, String fieldName) {
        Field[] declaredFields = clz.getDeclaredFields();
        if (declaredFields.length > 0) {
            for (Field declaredField : declaredFields) {
                if (fieldName.equals(declaredField.getName())) {
                    return declaredField;
                }
            }

            Class<?> superclass = clz.getSuperclass();
            if (superclass != null) {
                return getFieldByName(superclass, fieldName);
            }
        }

        return null;
    }

    public static Method[] getAllMethods(Class<?> clz) {
        List<Method> methodList = new ArrayList<>();
        Method[] declaredMethods = clz.getDeclaredMethods();
        if (declaredMethods.length > 0) {
            methodList.addAll(Arrays.asList(declaredMethods));
        }

        Class<?> superclass = clz.getSuperclass();
        if (superclass != null) {
            Method[] superMethods = getAllMethods(superclass);
            if (superMethods.length > 0) {
                methodList.addAll(Arrays.asList(superMethods));
            }
        }

        Method[] methods = new Method[methodList.size()];
        return methodList.toArray(methods);
    }

    public static Method getMethodByName(Class<?> clz, String methodName) {
        Method[] declaredMethods = clz.getDeclaredMethods();
        if (declaredMethods.length > 0) {
            for (Method declaredMethod : declaredMethods) {
                if (methodName.equals(declaredMethod.getName())) {
                    return declaredMethod;
                }
            }
        }

        Class<?> superclass = clz.getSuperclass();
        if (superclass != null) {
            return getMethodByName(superclass, methodName);
        }

        return null;
    }

    public static Object getPropertyValue(Object obj, String fieldName) {
        try {
            Class<?> clz = obj.getClass();
            Field field = getFieldByName(clz, fieldName);
            if (field != null) {
                field.setAccessible(true);
                return field.get(obj);
            }

            return null;
        } catch (IllegalAccessException e) {
            throw new ServiceException(String.format("get %s field value error: %s", fieldName, e.getMessage()), e);
        }
    }

    public static void setPropertyValue(Object obj, String fieldName, Object value) {
        try {
            Class<?> clz = obj.getClass();
            Field field = getFieldByName(clz, fieldName);
            if (field != null) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                if (value != null) {
                    if (value instanceof Collection) {
                        //TODO 属性时集合时
                        field.set(obj, null);
                    } else {
                        Object convert = ColumnConvertUtil.convert(type, value);
                        field.set(obj, convert);
                    }
                } else {
                    field.set(obj, null);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ServiceException(String.format("set %s field value error: %s", fieldName, e.getMessage()), e);
        }
    }
}
