package com.mbw.office.date;

import com.mbw.office.common.util.date.DateUtil;
import org.junit.Test;

import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-17 13:56
 */
public class DateTest {

    @Test
    public void f1() {
        String s1 = "20190605214043";
        Date date1 = DateUtil.parse(s1, "yyyyMMddHHmmss");
        System.out.println(DateUtil.formatDefault(date1));
    }

    @Test
    public void f2() {
        String s2 = "20190605" + "000000";
        Date date2 = DateUtil.parse(s2, "yyyyMMddHHmmss");
        System.out.println(DateUtil.formatDefault(date2));
    }
}
