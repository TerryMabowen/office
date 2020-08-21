package com.mbw.office.common.util.reflection;

import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.collection.CollectionUtil;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.common.util.validate.AssertUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 反射工具类
 *
 * @author Mabowen
 * @date 2020-07-14 14:46
 */
@Slf4j
@Deprecated
public class ReflectUtil {
    private static final String GET = "get";
    private static final String SET = "set";

    public static Object getFieldValue(Object instance, String fieldName) {
        try {
            if (instance != null && StrUtil.isNotBlank(fieldName)) {
                Field field = getField(instance.getClass(), fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    return convertValueByType(field.get(instance), field.getType());
                }
            }

            return null;
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static void setAttributeValueIgnoreNullValue(Object instance, String fieldName, Object value) {
        setAttributeValue(instance, fieldName, value, true);
    }

    public static void setAttributeValueNotIgnoreNullValue(Object instance, String fieldName, Object value) {
        setAttributeValue(instance, fieldName, value, false);
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

    public static List<Field> getFields(Class<?> clz) {
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

    public static Method[] getMethods(Object instance) {
        return instance.getClass().getDeclaredMethods();
    }

    public static Method getMethod(Object instance, String fieldName, String type) {
        if (StrUtil.isNotBlank(fieldName)) {
            String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            String methodName = type + name;
            Method[] methods = getMethods(instance);
            if (methods.length > 0) {
                for (Method method : methods) {
                    if (methodName.equals(method.getName())) {
                        return method;
                    }
                }
            }
        }

        return null;
    }

    public static <T> T newInstance(Class<T> clz, Object... args) {
        return cn.hutool.core.util.ReflectUtil.newInstance(clz, args);
    }

    public static boolean judgeAllFieldsIsNull(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return true;
        }

        Class<?> clz = obj.getClass();
        List<Field> fields = getFields(clz);
        if (CollectionUtil.isNotEmpty(fields)) {
            for (Field field : fields) {
                field.setAccessible(false);
                if (null != getFieldValue(obj, field.getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean judgeAllFieldsIsNotNull(Object obj) throws IllegalAccessException {
        return !judgeAllFieldsIsNull(obj);
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clz, boolean ignoreNullValue) {
        AssertUtil.assertMapNotEmpty(map, "map cannot be empty");

        try {
            List<Field> fields = getFields(clz);
            for (Field field : fields) {
                field.setAccessible(true);
                // 获取带_下划线的名称的value值
                Object value = map.get(field.getName());

                if (ignoreNullValue) {
                    setAttributeValueIgnoreNullValue(newInstance(clz), field.getName(), value);
                } else {
                    setAttributeValueNotIgnoreNullValue(newInstance(clz), field.getName(), value);
                }
            }

            return newInstance(clz);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static <T> T mapToBeanNotIgnoreNullValue(Map<String, Object> map, Class<T> clz) {
        return mapToBean(map, clz, false);
    }

    public static <T> T mapToBeanIgnoreNullValue(Map<String, Object> map, Class<T> clz) {
        return mapToBean(map, clz, true);
    }

    private static void setAttributeValue(Object instance, String fieldName, Object value, boolean ignoreNullValue) {
        try {
            if (instance != null && StrUtil.isNotBlank(fieldName)) {
                Field field = getField(instance.getClass(), fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    Method setMethod = getMethod(instance, fieldName, SET);
                    if (ignoreNullValue) {
                        if (value != null) {
                            if (setMethod != null) {
                                setMethod.invoke(instance, convertValueByType(value, field.getType()));
                            }
                        }
                    } else {
                        if (setMethod != null) {
                            setMethod.invoke(instance, convertValueByType(value, field.getType()));
                        }
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private static <T> T convertValueByType(Object value, Class<T> type) {
        if (value == null || type == null) {
            return null;
        }

        String fieldType = type.toString();
        switch (fieldType) {
            case "class java.lang.String":
                return type.cast(String.valueOf(value));
            case "class java.lang.Short":
                return type.cast(Short.parseShort(String.valueOf(value)));
            case "class java.lang.Integer":
                return type.cast(Integer.parseInt(String.valueOf(value)));
            case "class java.lang.Long":
                return type.cast(Long.parseLong(String.valueOf(value)));
            case "class java.lang.Float":
                return type.cast(Float.parseFloat(String.valueOf(value)));
            case "class java.lang.Double":
                return type.cast(Double.parseDouble(String.valueOf(value)));
            case "class java.lang.Boolean":
                return type.cast(Boolean.parseBoolean(String.valueOf(value)));
            case "class java.lang.Date":
                return type.cast(DateUtil.parseDefault(String.valueOf(value)));
            case "class java.math.BigDecimal":
                return type.cast(new BigDecimal(String.valueOf(value)));
            default:
                return type.cast(value);
        }
    }
}
