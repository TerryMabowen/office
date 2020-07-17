package com.mbw.office.common.util.string;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 *
 * @author Mabowen
 * @date 2020-07-17 11:04
 */
public class StringUtil {
    public static final char UNDERLINE_CHAR = '_';

    /**
     * 下划线转驼峰
     *
     * @param underlineStr
     * @return
     */
    public static String underline2Camel(String underlineStr) {
        if (StrUtil.isBlank(underlineStr)) {
            return StrUtil.EMPTY;
        }

        int len = underlineStr.length();
        StringBuilder builder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = underlineStr.charAt(i);
            if (c == UNDERLINE_CHAR && (++i) < len) {
                c = underlineStr.charAt(i);
                builder.append(Character.toUpperCase(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param camelStr
     * @return
     */
    public static String camel2Underline(String camelStr) {
        if (StrUtil.isBlank(camelStr)) {
            return StrUtil.EMPTY;
        }

        int len = camelStr.length();
        StringBuilder builder = new StringBuilder(len + len >> 1);
        for (int i = 0; i < len; i++) {
            char c = camelStr.charAt(i);
            if (Character.isUpperCase(c)) {
                builder.append(UNDERLINE_CHAR);
                builder.append(Character.toLowerCase(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
