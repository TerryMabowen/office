package com.mbw.office.regex;

import com.mbw.office.common.constant.RegexConstants;
import org.junit.Test;

import java.util.regex.Matcher;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-03 11:39
 */
public class RegexTest {

    @Test
    public void f1() {
        String t1 = "2000-12-30";
        Matcher m1 = RegexConstants.YEAR_MONTH_DAY.matcher(t1);
        System.out.println(m1.matches());

        String t2 = "2020328";
        Matcher m2 = RegexConstants.YearMonthDay.matcher(t2);
        System.out.println(m2.matches());
    }
}
