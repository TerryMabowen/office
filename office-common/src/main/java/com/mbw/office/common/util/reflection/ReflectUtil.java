package com.mbw.office.common.util.reflection;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 反射工具类
 *
 * @author Mabowen
 * @date 2020-07-14 14:46
 */
@Slf4j
public class ReflectUtil {
    private static final String GET = "get";
    private static final String SET = "set";

    public static Object getFieldValue(Object instance, String fieldName) {
        try {
            if (instance != null && StrUtil.isNotBlank(fieldName)) {
                Field field = getField(instance.getClass(), fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    return convert(field.get(instance), field.getType());
                }
            }

            return null;
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static void setFieldValueIgnoreNullValue(Object instance, String fieldName, Object value) {
        setAllFieldValue(instance, fieldName, value, true);
    }

    public static void setFieldValueNotIgnoreNullValue(Object instance, String fieldName, Object value) {
        setAllFieldValue(instance, fieldName, value, false);
    }

    public static Field getField(Class<?> clz, String fieldName) {
        try {
            if (clz != null && StrUtil.isNotBlank(fieldName)) {
                Field field = clz.getDeclaredField(fieldName);
                if (field == null) {
                    Class<?> superclass = clz.getSuperclass();
                    return getField(superclass, fieldName);
                } else {
                    return field;
                }
            }

            return null;
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
            return null;
        } catch (SecurityException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static List<Field> getFiles(Class<?> clz) {
        List<Field> fields = new ArrayList<>();
        if (clz != null) {
            fields.addAll(Arrays.asList(clz.getDeclaredFields()));
        }

        return fields;
    }

    public static List<Field> getFieldsWithParents(Class<?> clz) {
        List<Field> fields = new ArrayList<>();
        while (clz != null) {
            fields.addAll(Arrays.asList(clz.getDeclaredFields()));
            clz = clz.getSuperclass();
        }

        return fields;
    }

    public static Method getMethod(Object instance, String fieldName, String type) {
        try {
            if (instance != null && StrUtil.isNotBlank(fieldName)) {
                String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                String methodName = type + name;
                return instance.getClass().getDeclaredMethod(methodName);
            }

            return null;
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T newInstance(Class<T> clz, Object... args) {
        return cn.hutool.core.util.ReflectUtil.newInstance(clz, args);
    }

    private static void setAllFieldValue(Object instance, String fieldName, Object value, boolean ignoreNullValue) {
        try {
            if (instance != null && StrUtil.isNotBlank(fieldName)) {
                Field field = getField(instance.getClass(), fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    Object fieldValue = convert(value, field.getType());
                    if (ignoreNullValue) {
                        if (fieldValue != null) {
                            Method setMethod = getMethod(instance, fieldName, SET);
                            if (setMethod != null) {
                                setMethod.invoke(instance, convert(fieldValue, field.getType()));
                            }
                        }
                    } else {
                        Method setMethod = getMethod(instance, fieldName, SET);
                        if (setMethod != null) {
                            setMethod.invoke(instance, convert(fieldValue, field.getType()));
                        }
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private static <T> T convert(Object value, Class<T> type) {
        if (value == null || type == null) {
            return null;
        }

        if (type.isInstance(value)) {
            return type.cast(value);
        }

//        String fieldType = type.toString();
//        if ("class java.lang.String".equals(fieldType)) {
//            return String.valueOf(value);
//        } else if ("class java.lang.Short".equals(fieldType)) {
//            return Short.parseShort(String.valueOf(value));
//        } else if ("class java.lang.Integer".equals(fieldType)) {
//            return Integer.parseInt(String.valueOf(value));
//        } else if ("class java.lang.Long".equals(fieldType)) {
//            return Long.parseLong(String.valueOf(value));
//        } else if ("class java.lang.Float".equals(fieldType)) {
//            return Float.parseFloat(String.valueOf(value));
//        } else if ("class java.lang.Double".equals(fieldType)) {
//            return Double.parseDouble(String.valueOf(value));
//        } else if ("class java.lang.Boolean".equals(fieldType)) {
//            return Boolean.parseBoolean(String.valueOf(value));
//        } else if ("class java.lang.Date".equals(fieldType)) {
//            return DateUtil.parseDefault(String.valueOf(value));
//        } else if ("class java.math.BigDecimal".equals(fieldType)) {
//            return new BigDecimal(String.valueOf(value));
//        }

        return null;
    }
}
