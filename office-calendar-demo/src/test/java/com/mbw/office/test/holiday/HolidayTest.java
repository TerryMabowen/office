package com.mbw.office.test.holiday;

import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.common.util.json.GsonUtil;
import com.mbw.office.demo.holiday.HolidayService;
import com.mbw.office.demo.holiday.model.SettlementDateDO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-24 15:16
 */
public class HolidayTest {
    private HolidayService service = new HolidayService();
    private static final String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};

    @Test
    public void f1() {
        createSettlementDays();
    }

    private void createSettlementDays() {
        String year = DateUtil.getYear();
        System.out.println(year);

        List<SettlementDateDO> dos = new ArrayList<>();
        for (String month : months) {
            dos.addAll(service.createSettlementDays(year, month));
        }
        System.out.println(GsonUtil.beanToJson(dos));
    }
}
