package com.mbw.office.cloud.common.constant;

/**
 * @author Mabowen
 * @date 2020-08-04 10:02
 */
public class StringInfoConstants {
    //空数据提示
    public static final String NA = "N/A";
    public static final String WU_STR = "无";
    public static final String NULL = "null";
    public static final String EMPTY_STR = "";

    //标点符号
    public static final String ENGLISH_COMMA = ",";
    public static final String CHINESE_COMMA = "，";
    public static final String SEMICOLON = ";";
    public static final String UNDERLINE = "_";
    public static final String DASH = "-";
    public static final String VERTICAL_BAR = "|";
    public static final String LEFT_SLASH = "/";
    public static final String RIGHT_SLASH = "\\";
    /**
     * 在regex中"\\"表示一个"\"，在java中一个"\"也要用"\\"表示。
     * 这样，前一个"\\"代表regex中的"\"，后一个"\\"代表java中的"\"，
     * 所以字符串转义一次，正则转义一次，那么一个斜扛要写4个
     */
    public static final String BACKSLASH = "\\\\";

    public static final String TRUE = "true";
    public static final String FALSE = "false";
}
