package com.mbw.office.test.fastjson;

import cn.hutool.core.collection.CollUtil;
import com.mbw.office.common.util.json.FastJsonUtil;
import com.mbw.office.common.util.json.GsonUtil;
import com.mbw.office.demo.fastjson.SettlementDayService;
import com.mbw.office.demo.fastjson.model.SettlementDateDO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-24 19:20
 */
public class SettlementDayTest {
    private SettlementDayService service = new SettlementDayService();
    private static final String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};

    @Test
    public void f1() {
        List<SettlementDateDO> settlementDays = service.createSettlementDays("2019", "07");
        System.out.println(FastJsonUtil.beanToJson(settlementDays));
    }

    @Test
    public void f2() {
        createSettlementDays("2019");
//        createSettlementDays();
    }

    private void createSettlementDays(String year) {
//        String year = DateUtil.getYear();
//        System.out.println(year);

        List<SettlementDateDO> dos = new ArrayList<>();
        for (String month : months) {
            List<SettlementDateDO> settlementDays = service.createSettlementDays(year, month);
            if (CollUtil.isNotEmpty(settlementDays)) {
                dos.addAll(settlementDays);
            }
        }
        System.out.println(GsonUtil.beanToJson(dos));
    }
}
