package com.mbw.office.common.lang.conversion;

/**
 * @author Mabowen
 * @date 2020-07-15 10:40
 */
public class StringColumnConvert extends AbstractColumnConvert<String>{
    @Override
    public String convert(Object value) {
        if (value != null) {
            this.value = String.valueOf(value);
        }

        return this.value;
    }
}
