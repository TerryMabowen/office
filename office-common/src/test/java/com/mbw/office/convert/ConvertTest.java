package com.mbw.office.convert;

import cn.hutool.core.util.CharsetUtil;
import com.mbw.office.common.lang.conversion.AbstractColumnConvert;
import com.mbw.office.common.lang.conversion.IntegerColumnConvert;
import com.mbw.office.common.util.conversion.ColumnConvertFactory;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
        Integer convert = columnConvert.convert("12", Integer.class);
        System.out.println(convert + ", " + convert.getClass());
    }

    @Test
    public void f2() {
        AbstractColumnConvert columnConvert = ColumnConvertFactory.getInstance().getColumnConvert();
        Integer convert = (Integer) columnConvert.convert("12", Integer.class);
        System.out.println(convert + ", " + convert.getClass());
    }

    @Test
    public void f3() {
        AbstractColumnConvert columnConvert = ColumnConvertFactory.getInstance().getColumnConvert();
        Double convert = (Double) columnConvert.convert("12", Double.class);
        System.out.println(convert + ", " + convert.getClass());
    }

    @Test
    public void f4() {
        try {
            AbstractColumnConvert columnConvert = ColumnConvertFactory.getInstance().getColumnConvert();
            Boolean convert = (Boolean) columnConvert.convert("sssfe", Boolean.class);
            System.out.println(convert + ", " + convert.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void f5() {
        try {
            String sr = "\u767B\u5F55\u9875\u8BF4\u660E";
            String src = "\u51FA\u56FD\u8BC1\u660E\u6750\u6599\uFF1A\u63D0\u4F9B\u7528\u4E8E\u7533\u8BF7\u7559\u5B66\u548C\u79FB\u6C11\u7684\u4E2D\u82F1\u6587\u6210\u7EE9\u5355\u3001\u4E2D\u82F1\u6587\u6BD5\u4E1A\u8BC1\u3001\u4E2D\u82F1\u6587\u5B66\u4F4D\u8BC1\u7684\u7FFB\u8BD1\u76D6\u7AE0\u3002 \u8865\u529E\u6863\u6848\u6750\u6599\uFF1A \u63D0\u4F9B\u7528\u4EBA\u5355\u4F4D\u8981\u6C42\u8865\u529E\u7684\u4E2A\u4EBA\u5B66\u7C4D\u6863\u6848\u7F3A\u5931\u6750\u6599\u3002  \u6210\u7EE9\u4E0E\u5B66\u5386\u5B66\u4F4D\u8BA4\u8BC1\uFF1A \u63D0\u4F9B\u5404\u8BA4\u8BC1\u673A\u6784\u3001\u7528\u4EBA\u5355\u4F4D\u5BF9\u5B66\u751F\u6210\u7EE9\u3001\u5B66\u5386\u3001\u5B66\u4F4D\u7684\u6838\u5B9E\u4E1A\u52A1\u3002   \u539F\u59CB\u6210\u7EE9\u548C\u5404\u7C7B\u540D\u518C\u67E5\u8BE2\uFF1A  \u63D0\u4F9B\u5404\u7C7B\u6BD5\u4E1A\u751F\u6210\u7EE9\u5355\u3001\u6BD5\u4E1A\u540D\u518C\u3001\u5F55\u53D6\u540D\u518C\u7684\u590D\u5236\u4EF6\u3002";
            byte[] gbks = sr.getBytes("GBK");
            String s = new String(gbks, "GBK");
            System.out.println(s);

            String convert = CharsetUtil.convert(sr, StandardCharsets.ISO_8859_1, StandardCharsets.UTF_8);
            System.out.println(convert);

            String s2 = new String(convert.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            System.out.println(s2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void f6() {
        System.out.println(Charset.defaultCharset());
    }
}
