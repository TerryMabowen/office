package com.mbw.office.common.lang.conversion;

import cn.hutool.core.util.StrUtil;

/**
 * @author Mabowen
 * @date 2020-07-15 10:40
 */
public class StringColumnConvert extends AbstractColumnConvert<String>{

    @Override
    public String convert(String value, Class<String> type) {
        if (StrUtil.isNotBlank(value) && !"null".equalsIgnoreCase(value)) {
            return value;
        }

        return null;
    }
}
