package com.mbw.office.bean;

import com.mbw.office.common.util.reflection.ReflectUtil;
import org.junit.Test;

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
