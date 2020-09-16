package com.mbw.office.bean;

import com.mbw.office.common.lang.helper.ApplicationContextHelper;
import com.mbw.office.common.util.bean.BeanUtil;
import com.mbw.office.common.util.conversion.ColumnConvertUtil;
import com.mbw.office.common.util.json.JacksonUtil;
import com.mbw.office.common.util.reflection.ReflectUtil;
import me.chanjar.weixin.cp.api.WxCpUserService;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-28 10:54
 */
public class BeanUtilTest {

    @Test
    public void f1() {
        Map<String, Object> map = getMap();
        JlBillDetail jlBillDetail = ReflectUtil.mapToBeanNotIgnoreNullValue(map, JlBillDetail.class);
        System.out.println(jlBillDetail);
    }

    /**
     * 通过ApplicationContext获取spring容器中的类
     * @author Mabowen
     * @date 2020-08-14 16:55
     */
    @Test
    public void f2() {
        Object userService = ApplicationContextHelper.getApplicationContext()
                .getAutowireCapableBeanFactory()
                .getBean("UserService");

        Object userService1 = ApplicationContextHelper.getApplicationContext()
                .getBean("UserService");

        WxCpUserService wxCpUserService = ApplicationContextHelper.getApplicationContext()
                .getBean(WxCpUserService.class);
    }

    @Test
    public void f3() {
        BigInteger integer = new BigInteger("123");
        System.out.println(integer.toString());

        BigDecimal decimal = new BigDecimal("2300.50");
        System.out.println(decimal.toString());

        Date date = new Date();
        System.out.println(date.toString());

        Integer ss = new Integer("234");
        System.out.println(ss.toString());

        Boolean b = Boolean.FALSE;
        System.out.println(b.toString());

        Date date1 = ColumnConvertUtil.parseDateStrFormat("2012.25.15");
    }

    @Test
    public void f4() {
        Map<String, Object> map = getMap();
        JlBillDetail source = new JlBillDetail();
        BeanUtil.copyMapToBeanIgnoreNullValue(map, source);
        System.out.println(JacksonUtil.beanToJson(source));

        JlBillDetail target = new JlBillDetail();
        BeanUtil.copyBeanToBeanNotIgnoreNullValue(source, target);
        System.out.println(JacksonUtil.beanToJson(target));

        Map<String, Object> toMap = new HashMap<>();
        BeanUtil.copyBeanToMap(target, toMap);
        toMap.forEach((key, value) -> System.out.println(key + " = " + value));

        JlBillDetail billDetail = new JlBillDetail();
        BeanUtil.copyMapToBeanNotIgnoreNullValue(toMap, billDetail);
        System.out.println(JacksonUtil.beanToJson(billDetail));
    }

    private Map<String, Object> getMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("batchNo", null);
        result.put("mchId", "84951018299K01U");
        result.put("authCode", null);
        result.put("orderNo", "8001155120190605153050123511");
        result.put("payWay", "31");
        result.put("channelFee", "100000");
        result.put("nonStandardMerchant", null);
        result.put("retCode", "10000");
        result.put("sysRefNo", null);
        result.put("liquidationDate", "20190605");
        result.put("extraCommissions", "0");
        result.put("serialNo", null);
        result.put("tradeMoney", "100000");
        result.put("tradeTime", "20190605153050");
        result.put("channelCommissions", "200");
        result.put("originOrderNo", null);
        result.put("commissions", "350");
        result.put("feeFlag", "1");
        result.put("tradeCardNo", null);
        result.put("termNo", "76767676");
        result.put("signPasswordFlag", null);
        result.put("processType", "0");
        result.put("customerOrderNo", "0012oggf5tt0wlroggf5tt0wjrq9");
        result.put("tradeType", "1551");

        return result;
    }
}
