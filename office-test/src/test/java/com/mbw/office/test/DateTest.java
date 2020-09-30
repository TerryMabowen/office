package com.mbw.office.test;

import com.mbw.office.common.util.date.DateUtil;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-09-30 16:50
 */
public class DateTest {

    @Test
    public void f1() {
        Date yearBeginTime = DateUtil.getYearBeginTime(new Date());
        Date beforeDay = DateUtil.getBeforeDay(new Date(), 1);
        List<String> dates = DateUtil.listTwoDateStrBetween(yearBeginTime, beforeDay, "yyyy-MM-dd");
        dates.forEach(System.out::println);
    }
}
