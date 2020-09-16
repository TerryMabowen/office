package com.mbw.office.common.util.bean;

import com.google.common.collect.Maps;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.reflection.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * bean工具类
 *
 * @author Mabowen
 * @date 2020-09-16 09:56
 */
public class BeanUtil {

    public static void copyBeanToBeanNotIgnoreNullValue(Object sourceBean, Object targetBean) {
        copyBeanToBean(sourceBean, targetBean, false);
    }

    public static void copyBeanToBeanIgnoreNullValue(Object sourceBean, Object targetBean) {
        copyBeanToBean(sourceBean, targetBean, true);
    }

    private static void copyBeanToBean(Object sourceBean, Object targetBean, boolean ignoreNullValue) {
        if (sourceBean != null && targetBean != null) {
            Field[] sourceFields = ReflectionUtil.getAllFields(sourceBean.getClass());
            if (sourceFields.length > 0) {
                for (Field sourceField : sourceFields) {
                    Field targetField = ReflectionUtil.getFieldByName(targetBean.getClass(), sourceField.getName());
                    if (targetField != null && sourceField.getType().equals(targetField.getType())) {
                        Object value = ReflectionUtil.getPropertyValue(sourceBean, sourceField.getName());
                        if (ignoreNullValue) {
                            if (value != null) {
                                ReflectionUtil.setPropertyValue(targetBean, targetField.getName(), value);
                            }
                        } else {
                            ReflectionUtil.setPropertyValue(targetBean, targetField.getName(), value);
                        }
                    }
                }
            }
        } else {
            throw new ServiceException("sourceBean and targetBean can not be null");
        }
    }

    public static void copyBeanToMap(Object bean, Map<String, Object> map) {
        if (map == null) {
            map = Maps.newHashMap();
        }

        if (bean != null) {
            Field[] fields = ReflectionUtil.getAllFields(bean.getClass());
            if (fields.length > 0) {
                for (Field field : fields) {
                    Object value = ReflectionUtil.getPropertyValue(bean, field.getName());
                    map.put(field.getName(), value);
                }
            }
        } else {
            throw new ServiceException("bean can not be null");
        }
    }

    public static void copyMapToBeanNotIgnoreNullValue(Map<String, Object> map, Object bean) {
        copyMapToBean(map, bean, false);
    }

    public static void copyMapToBeanIgnoreNullValue(Map<String, Object> map, Object bean) {
        copyMapToBean(map, bean, true);
    }

    private static void copyMapToBean(Map<String, Object> map, Object bean, boolean ignoreNullValue) {
        if (MapUtils.isNotEmpty(map) && bean != null) {
            map.forEach((key, value) -> {
                if (ignoreNullValue) {
                    if (value != null) {
                        ReflectionUtil.setPropertyValue(bean, key, value);
                    }
                } else {
                    ReflectionUtil.setPropertyValue(bean, key, value);
                }
            });
        } else {
            throw new ServiceException("map and bean can not be empty");
        }
    }
}
