package com.mbw.office.string;

import org.junit.Test;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-01-25 11:50
 */
public class StrTest {

    @Test
    public void f1() {
        StringBuffer buffer = new StringBuffer();
        System.out.println(buffer.toString().length());
        if (!buffer.toString().contains("aaa")) {
            buffer.append("aaa");
        }
        System.out.println(buffer.toString());
    }
}
