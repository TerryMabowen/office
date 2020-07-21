package com.mbw.office.test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mabowen
 * @date 2020-07-21 09:58
 */
public class PatternTest {
    private static Pattern BUSINESS_PATTERN = Pattern.compile("^20[0-9]{19}$");

    @Test
    public void f1() {
        String s = "202006181552000435632";
        Matcher matcher = BUSINESS_PATTERN.matcher(s);
        if (matcher.matches()) {
            System.out.println("匹配成功");
        } else {
            System.out.println("匹配失败");
        }
    }

    @Test
    public void f2() {
        double n = Math.ceil(Math.sin(0.5));
        System.out.println(n);

//        double m = Math.ceil(Math.asin(0.5));
        double m = Math.asin(1.0);
        System.out.println(m);
    }
}
