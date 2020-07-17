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
        StringBuilder strb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = underlineStr.charAt(i);
            if (c == UNDERLINE_CHAR && (++i) < len) {
                c = underlineStr.charAt(i);
                strb.append(Character.toUpperCase(c));
            } else {
                strb.append(c);
            }
        }
        return strb.toString();
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
        StringBuilder strb = new StringBuilder(len + len >> 1);
        for (int i = 0; i < len; i++) {
            char c = camelStr.charAt(i);
            if (Character.isUpperCase(c)) {
                strb.append(UNDERLINE_CHAR);
                strb.append(Character.toLowerCase(c));
            } else {
                strb.append(c);
            }
        }
        return strb.toString();
    }
}
