package com.mbw.office.convert;

import com.mbw.office.common.lang.conversion.AbstractColumnConvert;
import com.mbw.office.common.lang.conversion.IntegerColumnConvert;
import com.mbw.office.common.util.conversion.ColumnConvertFactory;
import org.junit.Test;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-15 10:54
 */
public class ConvertTest {

    @Test
    public void f1() {
        IntegerColumnConvert columnConvert = ColumnConvertFactory.getInstance()
                .getIntegerColumnConvert();
        Integer convert = columnConvert.convert("12");
        System.out.println(convert + ", " + convert.getClass());
    }

    @Test
    public void f2() {
        AbstractColumnConvert columnConvert = ColumnConvertFactory.getInstance().getColumnConvert(Integer.class);
        Integer convert = (Integer) columnConvert.convert("12");
        System.out.println(convert + ", " + convert.getClass());
    }

    @Test
    public void f3() {
        AbstractColumnConvert columnConvert = ColumnConvertFactory.getInstance().getColumnConvert(Double.class);
        Double convert = (Double) columnConvert.convert("12");
        System.out.println(convert + ", " + convert.getClass());
    }

    @Test
    public void f4() {
        try {
            AbstractColumnConvert columnConvert = ColumnConvertFactory.getInstance().getColumnConvert(Boolean.class);
            Boolean convert = (Boolean) columnConvert.convert("sssfe");
            System.out.println(convert + ", " + convert.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
