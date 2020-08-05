package com.mbw.office.common.lang.conversion;

import cn.hutool.core.util.StrUtil;

/**
 * @author Mabowen
 * @date 2020-07-15 10:43
 */
public class IntegerColumnConvert extends AbstractColumnConvert<Integer>{

    @Override
    public Integer convert(String value, Class<Integer> type) {
        if (StrUtil.isNotBlank(value) && !"null".equalsIgnoreCase(value)) {
            return Integer.parseInt(value);
        }

        return null;
    }
}
