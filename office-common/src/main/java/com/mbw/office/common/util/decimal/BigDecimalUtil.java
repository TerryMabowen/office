package com.mbw.office.common.util.decimal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * BigDecimal工具类
 *
 * @author Mabowen
 * @date 2020-07-23 10:14
 */
public class BigDecimalUtil {
    /**
     * 默认金额保留两位小数
     */
    private static final int DEFAULT_MONEY_POINT = 2;

    /**
     * 格式化金额
     */
    public static BigDecimal format(double money, int point) {
        BigDecimal bigDecimal = new BigDecimal(money);
        return bigDecimal.setScale(point, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal pointsToYuan(BigDecimal money) {
        if (money == null) {
            return BigDecimal.ZERO;
        }

        return money.divide(BigDecimal.valueOf(100), DEFAULT_MONEY_POINT, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal pointsToYuan(long money) {
        return BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), DEFAULT_MONEY_POINT);
    }

    public static BigDecimal yuanToPoints(BigDecimal money) {
        if (money == null) {
            return BigDecimal.ZERO;
        }

        return money.multiply(BigDecimal.valueOf(100));
    }

    public static BigDecimal yuanToPoints(double money) {
        return BigDecimal.valueOf(money).multiply(BigDecimal.valueOf(100));
    }

    /**
     *
     */
    public static Double formatRoundUp(double v, int point) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置四舍五入
        nf.setRoundingMode(RoundingMode.HALF_UP);
        //设置最小保留几位小数
        nf.setMinimumFractionDigits(point);
        //设置最大保留几位小数
        nf.setMaximumFractionDigits(point);
        return Double.valueOf(nf.format(v));
    }

    /**
     * 格式化金额。带千位符
     */
    public static String moneyFormat(BigDecimal money) {
        if (money == null) {
            return "0.00";
        }
        return new DecimalFormat("###,##0.00").format(money);
    }

    /**
     * 格式化金额。带千位符
     */
    public static String moneyFormat(double money) {
        return new DecimalFormat("###,##0.00").format(money);
    }

    /**
     * 带小数的显示小数。不带小数的显示整数
     */
    public static String doubleTrans(Double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d.doubleValue());
        }
        return String.valueOf(d);
    }

    /**
     * BigDecimal 相加
     */
    public static BigDecimal add(double v1, double v2) {
        BigDecimal n1 = BigDecimal.valueOf(v1);
        BigDecimal n2 = BigDecimal.valueOf(v2);
        return n1.add(n2);
    }

    /**
     * BigDecimal 相减
     */
    public static BigDecimal subtract(double v1, double v2) {
        BigDecimal n1 = BigDecimal.valueOf(v1);
        BigDecimal n2 = BigDecimal.valueOf(v2);
        return n1.subtract(n2);
    }

    /**
     * BigDecimal 相乘
     * @return double
     */
    public static BigDecimal multiply(double v1, double v2) {
        BigDecimal n1 = BigDecimal.valueOf(v1);
        BigDecimal n2 = BigDecimal.valueOf(v2);
        return n1.multiply(n2);
    }

    /**
     * BigDecimal 相除
     * @return double
     */
    public static BigDecimal divide(double v1, double v2) {
        BigDecimal n1 = BigDecimal.valueOf(v1);
        BigDecimal n2 = BigDecimal.valueOf(v2);
        return n1.divide(n2, 10, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 比较大小 小于0：v1 < v2 大于0：v1 > v2 等于0：v1 = v2
     */
    public static int compare(double v1, double v2) {
        BigDecimal n1 = BigDecimal.valueOf(v1);
        BigDecimal n2 = BigDecimal.valueOf(v2);
        return n1.compareTo(n2);
    }
}
