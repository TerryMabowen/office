package com.mbw.office.learn.biz.utils.validate;


import cn.hutool.core.util.StrUtil;
import com.mbw.office.learn.biz.lang.exception.AssertException;
import com.mbw.office.learn.spi.enums.BaseEnumStatus;
import com.mbw.office.learn.spi.utils.enums.EnumUtil;

import java.util.Collection;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2019-12-20 17:02
 */
public class AssertUtil {
    public AssertUtil() {
    }

    public static void assertNotNull(String value, String tips) {
        if (null == value) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 字符串不能为空" : tips);
        }
    }

    public static void assertNotNull(String value) {
        assertNotNull((String)value, (String)null);
    }

    public static void assertNotEmpty(String value, String tips) {
        if (value == null || value.isEmpty() || StrUtil.isBlank(value)) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 字符串不能为空" : tips);
        }
    }

    public static void assertNotEmpty(String value) {
        assertNotEmpty(value, (String)null);
    }

    public static void assertValidateTel(String value, String tips) {
        assertNotNull(value, tips);
        if (!RegexUtil.isTel(value)) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 不是一个有效的手机/电话号码" : tips);
        }
    }

    public static void assertValidateTel(String value) {
        assertValidateTel(value, (String)null);
    }

    public static void assertValidateEmail(String value, String tips) {
        assertNotNull(value, tips);
        if (!RegexUtil.isEmail(value)) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 不是一个有效的邮箱地址" : tips);
        }
    }

    public static void assertValidateEmail(String value) {
        assertValidateEmail(value, (String)null);
    }

    public static void assertValidateMobile(String value, String tips) {
        if (!RegexUtil.isMobile(value)) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 不是一个有效的手机号" : tips);
        }
    }

    public static void assertValidateMobile(String value) {
        assertValidateMobile(value, (String)null);
    }

    public static void assertGT0(Number value, String tips) {
        if (value == null || value.longValue() <= 0L) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 必须大于0" : tips);
        }
    }

    public static void assertGT0(Number value) {
        assertGT0(value, (String)null);
    }

    public static void assertGE0(Number value, String tips) {
        if (null == value || value.doubleValue() < 0.0D) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 必须大于等于0" : tips);
        }
    }

    public static void assertNotNull(Object obj, String tips) {
        if (null == obj) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 对象不存在" : tips);
        }
    }

    public static void assertNotNull(Object obj) {
        assertNotNull((Object)obj, (String)null);
    }

    public static void assertEnumExist(int status, Class<? extends BaseEnumStatus> cls, String tips) {
        BaseEnumStatus value = EnumUtil.getEnumByValue(cls, status);
        if (null == value) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 枚举类型不存在" : tips);
        }
    }

    public static void assertEnumExist(int status, Class<? extends BaseEnumStatus> cls) {
        assertEnumExist(status, cls);
    }

    public static void assertEqual(Object val, Object val2, String tips) {
        if (val != null && val2 != null) {
            if (!val.equals(val2)) {
                throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 两个值不相等" : tips);
            }
        } else {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 两个值不相等" : tips);
        }
    }

    public static void assertEqual(Object val, Object val2) {
        assertEqual(val, val2);
    }

    public static void assertScope(Number start, Number end, Number value, String tips) {
        if (value == null || value.doubleValue() < start.doubleValue() || value.doubleValue() > end.doubleValue()) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 数值不再范围内" : tips);
        }
    }

    public static void assertScope(Number start, Number end, Number value) {
        assertScope(start, end, value);
    }

    public static void assertTrue(Boolean value, String tips) {
        assertNotNull((Object)value);
        if (!value) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 比如为True" : tips);
        }
    }

    public static void assertTrue(Boolean value) {
        assertTrue(value, (String)null);
    }

    public static <E> void assertCollectionNotEmpty(Collection<E> collection, String tips) {
        if (collection == null || collection.size() == 0) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 集合不能为空" : tips);
        }
    }

    public static <E> void assertCollectionNotEmpty(Collection<E> collection) {
        assertCollectionNotEmpty(collection, (String)null);
    }

    public static <K, V> void assertMapNotEmpty(Map<K, V> map, String tips) {
        if (map == null || map.size() == 0) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 集合不能为空" : tips);
        }
    }

    public static <K, V> void assertMapNotEmpty(Map<K, V> map) {
        assertMapNotEmpty(map, (String)null);
    }

    public static void assertValidateYear(String year, String tips) {
        assertNotEmpty(year, "年份不能为空");
        if (!RegexUtil.isYear(year)) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 年份格式不正确" : tips);
        }
    }

    public static void assertValidateMonth(String month, String tips) {
        assertNotEmpty(month, "月份不能为空");
        if (!RegexUtil.isMonth(month)) {
            throw new AssertException(StrUtil.isBlank(tips) ? "验证失败, 月份格式不正确" : tips);
        }
    }
}
