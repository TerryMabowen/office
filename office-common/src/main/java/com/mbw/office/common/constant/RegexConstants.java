package com.mbw.office.common.constant;

import java.util.regex.Pattern;

/**
 * 正则表达式常量
 *
 * @author Mabowen
 * @date 2021-02-03 11:53
 */
public class RegexConstants {
    /**
     * yyyy-MM-dd 格式的年月日字符串正则匹配格式
     */
    public static final Pattern YEAR_MONTH_DAY = Pattern.compile("^(2[0-9]{3})-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|[1-2][0-9]|3[0-1])$");

    /**
     * yyyyMMdd 格式的年月日字符串正则匹配格式
     */
    public static final Pattern YearMonthDay = Pattern.compile("^(2[0-9]{3})([1-9]|0[1-9]|1[0-2])([1-9]|0[1-9]|[1-2][0-9]|3[0-1])$");
}
