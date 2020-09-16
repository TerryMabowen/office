package com.mbw.office.common.util.conversion;

import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.constant.StringInfoConstants;
import com.mbw.office.common.util.date.DateUtil;
import org.hibernate.service.spi.ServiceException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 属性转换工具类
 *
 * @author Mabowen
 * @date 2020-09-16 10:42
 */
public class ColumnConvertUtil {

    public static Object convert(Class<?> type, Object value) {
        if (value != null && StrUtil.isNotBlank(value.toString()) && !StringInfoConstants.NULL.equalsIgnoreCase(value.toString())) {
            try {
                if (String.class.equals(type)) {
                    return value.toString();
                } else if (Short.class.equals(type)) {
                    if (value instanceof Short) {
                        return value;
                    }
                    return Short.parseShort(value.toString());
                } else if (Integer.class.equals(type)) {
                    if (value instanceof Integer) {
                        return value;
                    }
                    return Integer.parseInt(value.toString());
                } else if (Long.class.equals(type)) {
                    if (value instanceof Long) {
                        return value;
                    }
                    return Long.parseLong(value.toString());
                } else if (Float.class.equals(type)) {
                    if (value instanceof Float) {
                        return value;
                    }
                    return Float.parseFloat(value.toString());
                } else if (Double.class.equals(type)) {
                    if (value instanceof Double) {
                        return value;
                    }
                    return Double.parseDouble(value.toString());
                } else if (Boolean.class.equals(type)) {
                    if (value instanceof Boolean) {
                        return value;
                    }

                    if (StringInfoConstants.TRUE.equalsIgnoreCase(value.toString())) {
                        return Boolean.TRUE;
                    } else if (StringInfoConstants.FALSE.equalsIgnoreCase(value.toString())) {
                        return Boolean.FALSE;
                    }
                } else if (Date.class.equals(type)) {
                    if (value instanceof Date) {
                        return value;
                    } else if (value instanceof String) {
                        return parseDateStrFormat(value.toString());
                    }
                } else if (BigInteger.class.equals(type)) {
                    if (value instanceof BigInteger) {
                        return value;
                    }
                    return new BigInteger(value.toString());
                } else if (BigDecimal.class.equals(type)) {
                    if (value instanceof BigDecimal) {
                        return value;
                    }
                    return new BigDecimal(value.toString());
                } else {
                    return value;
                }
            } catch (NumberFormatException | ClassCastException e) {
                throw new ServiceException(String.format("%s转换为%s异常，异常原因是%s", value, type.getTypeName(), e.getMessage()), e);
            }
        }

        return null;
    }

    public static Date parseDateStrFormat(String dateStr) {
        if (StrUtil.isNotBlank(dateStr)) {
            String str = dateStr.replaceAll("-", "")
                    .replaceAll(" ", "")
                    .replaceAll(":", "")
                    .replaceAll("\\.", "")
                    .replace("年", "")
                    .replace("月", "")
                    .replace("日", "")
                    .replace("时", "")
                    .replace("分", "")
                    .replace("秒", "")
                    .replace("毫秒", "");

            if (1 == str.length() || 2 == str.length()) {
                //MM或dd或HH或mm或ss, 默认是月份
                return DateUtil.parse(str, DateUtil.MM);
            } else if (4 == str.length()) {
                //yyyy或yyyyMM或MMdd或HHmm或mmss，默认是年，其次是年月
                try {
                    return DateUtil.parse(str, DateUtil.yyyy);
                } catch (Exception e) {
                    return DateUtil.parse(str, DateUtil.yyyyMM);
                }
            } else if (8 == str.length()) {
                //yyyyMMdd
                return DateUtil.parse(str, DateUtil.yyyyMMdd);
            } else if (6 == str.length()) {
                //HHmmss
                return DateUtil.parse(str, DateUtil.HHmmss);
            } else if (12 == str.length()) {
                //yyyyMMddHHmm
                return DateUtil.parse(str, DateUtil.yyyyMMddHHmm);
            } else if (14 == str.length()) {
                //yyyyMMddHHmmss
                return DateUtil.parse(str, DateUtil.yyyyMMddHHmmss);
            } else if (str.length() > 14) {
                //yyyyMMddHHmmssSSS
                return DateUtil.parse(str, DateUtil.TIMESTAMP_PATTERN);
            } else {
                throw new ServiceException("未知格式的日期字符串");
            }
        }

        return null;
    }
}
