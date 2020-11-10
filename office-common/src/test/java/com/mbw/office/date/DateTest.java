package com.mbw.office.date;

import cn.hutool.core.collection.CollUtil;
import com.mbw.office.common.util.date.DateUtil;
import org.junit.Test;

import java.util.Date;
import java.util.List;

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

    @Test
    public void f3() {
        String date = "2004-02-15";
        String monthBegin = DateUtil.getMonthBegin(DateUtil.parseShort(date));
        String monthEnd = DateUtil.getMonthEnd(DateUtil.parseShort(date), "yyyy-MM");
        System.out.println(monthBegin + " ~ " + monthEnd);
    }

    @Test
    public void f4() {
        String date = "2000-02-15";
        String yearBegin = DateUtil.getYearBegin(DateUtil.parseShort(date));
        String yearEnd = DateUtil.getYearEnd(DateUtil.parseShort(date));
        System.out.println(yearBegin + " ~ " + yearEnd);
    }

    @Test
    public void f5() {
        String date = "2000-02-15";
        String dayBegin = DateUtil.getDayBegin(DateUtil.parseShort(date));
        String dayEnd = DateUtil.getDayEnd(DateUtil.parseShort(date));
        System.out.println(dayBegin + " ~ " + dayEnd);
    }

    @Test
    public void f6() {
        String begin = "2019-10-12";
        String end = "2020-12-15";
        List<Date> dates = DateUtil.getMonthDateBetween(DateUtil.parseShort(begin), DateUtil.parseShort(end));
        if (CollUtil.isNotEmpty(dates)) {
            dates.forEach(d -> System.out.println(DateUtil.formatShort(d)));
        }
    }

    @Test
    public void f7() {
        String begin = "2019-10-12";
        String end = "2020-12-15";

//        List<String> months = DateUtil.getMonthStrBetween(DateUtil.parseShort(begin), DateUtil.parseShort(end), "MM");
        List<String> months = DateUtil.getMonthStrBetween(DateUtil.parseShort(begin), DateUtil.parseShort(end), null);
        if (CollUtil.isNotEmpty(months)) {
            months.forEach(System.out::println);
        }
    }

    @Test
    public void f8() {
        String dateStr = "2000-02-15";
        Date date = DateUtil.parseShort(dateStr);

        String dayBegin = DateUtil.getDayBegin(date);
        String dayEnd = DateUtil.getDayEnd(date);
        System.out.println(dayBegin + " ~ " + dayEnd);

        String monthBegin = DateUtil.getMonthBegin(date);
        String monthEnd = DateUtil.getMonthEnd(date, "yyyy-MM");
        System.out.println(monthBegin + " ~ " + monthEnd);
    }

    @Test
    public void f9() {
        String dateStr = "2020-10-15 12:05:46";
        Date date = DateUtil.parseDefault(dateStr);

        System.out.println(date.getTime());
    }
}
