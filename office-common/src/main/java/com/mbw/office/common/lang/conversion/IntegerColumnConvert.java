package com.mbw.office.common.lang.conversion;

/**
 * @author Mabowen
 * @date 2020-07-15 10:43
 */
public class IntegerColumnConvert extends AbstractColumnConvert<Integer>{
    @Override
    public Integer convert(Object value) {
        if (value != null) {
            this.value = Integer.parseInt(String.valueOf(value));
        }

        return this.value;
    }
}
