package com.mbw.office.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-09-24 10:40
 */
public class CompareTest {

    @Test
    public void f1() {
        BigDecimal income = new BigDecimal("0.00");

        BigDecimal outflow = new BigDecimal("278283.86");

        int result = income.compareTo(outflow);
        if (result > 0) {
            System.out.println("收入 大于 支出");
        } else if (result < 0) {
            System.out.println("收入 小于 支出");
        } else {
            System.out.println("收入 等于 支出");
        }
    }

    @Test
    public void f2() {
        BigDecimal netValue = new BigDecimal("-32500.00");

        BigDecimal maxMonthCash = new BigDecimal("-30000.00");

        BigDecimal maxAddCash = maxMonthCash.add(new BigDecimal("10000"));

        if (netValue.compareTo(maxMonthCash) <= 0) {
            System.out.println("危险");
        } else if (netValue.compareTo(maxMonthCash) > 0 && netValue.compareTo(maxAddCash) <= 0) {
            System.out.println("告警");
        } else if (netValue.compareTo(maxAddCash) > 0) {
            System.out.println("正常");
        } else {
            System.out.println("其他");
        }
    }
}
