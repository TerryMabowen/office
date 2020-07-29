package com.mbw.office.common.lang.conversion;

import lombok.Getter;

/**
 * @author Mabowen
 * @date 2020-07-15 10:35
 */
public abstract class AbstractColumnConvert<T> {
    @Getter
    protected T value;

    public abstract T convert(String value, Class<T> type);
}
