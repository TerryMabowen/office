package com.mbw.office.common.util.conversion;

import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.lang.conversion.AbstractColumnConvert;
import com.mbw.office.common.lang.conversion.IntegerColumnConvert;
import com.mbw.office.common.lang.conversion.StringColumnConvert;
import com.mbw.office.common.util.date.DateUtil;
import lombok.Getter;
import org.hibernate.service.spi.ServiceException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Mabowen
 * @date 2020-07-15 10:44
 */
public class ColumnConvertFactory {
    private static ColumnConvertFactory factory;

    @Getter
    private StringColumnConvert stringColumnConvert;

    @Getter
    private IntegerColumnConvert integerColumnConvert;

    public static ColumnConvertFactory getInstance() {
        if (factory != null) {
            return factory;
        }

        synchronized (ColumnConvertFactory.class) {
            if (factory == null) {
                factory = new ColumnConvertFactory();

                factory.init();
            }
        }

        return factory;
    }

    public <T> AbstractColumnConvert getColumnConvert() {
        return new AbstractColumnConvert<T>() {
            @Override
            public T convert(String value, Class<T> clz) {
                if (StrUtil.isNotBlank(value) && !"null".equalsIgnoreCase(value)) {
                    try {
                        if (String.class.equals(clz)) {
                            this.value = clz.cast(value);
                        } else if (Short.class.equals(clz)) {
                            this.value = clz.cast(Short.parseShort(value));
                        } else if (Integer.class.equals(clz)) {
                            this.value = clz.cast(Integer.parseInt(value));
                        } else if (Long.class.equals(clz)) {
                            this.value = clz.cast(Long.parseLong(value));
                        } else if (Float.class.equals(clz)) {
                            this.value = clz.cast(Float.parseFloat(value));
                        } else if (Double.class.equals(clz)) {
                            this.value = clz.cast(Double.parseDouble(value));
                        } else if (Boolean.class.equals(clz)) {
                            if ("true".equalsIgnoreCase(value)) {
                                this.value = clz.cast(Boolean.TRUE);
                            } else {
                                this.value = clz.cast(Boolean.FALSE);
                            }
                        } else if (Date.class.equals(clz)) {
                            //TODO 如果value不是yyyy-MM-dd HH:mm:ss的格式，解析会报错，这里需要根据字符串日期格式使用相应的解析方式
                            this.value = clz.cast(DateUtil.parseDefault(value));
                        } else if (BigInteger.class.equals(clz)) {
                            this.value = clz.cast(new BigInteger(value));
                        } else if (BigDecimal.class.equals(clz)) {
                            this.value = clz.cast(new BigDecimal(value));
                        }
                    } catch (NumberFormatException | ClassCastException e) {
                        throw new ServiceException(String.format("%s转换为%s异常，异常原因是%s", value, clz.getTypeName(), e.getMessage()), e);
                    }
                }
                return this.value;
            }
        };
    }

    private void init() {
        stringColumnConvert = new StringColumnConvert();
        integerColumnConvert = new IntegerColumnConvert();
    }
}