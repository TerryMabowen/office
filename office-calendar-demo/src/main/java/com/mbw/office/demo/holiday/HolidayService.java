package com.mbw.office.demo.holiday;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.common.util.json.GsonUtil;
import com.mbw.office.common.util.validate.AssertUtil;
import com.mbw.office.demo.gson.*;
import com.mbw.office.demo.holiday.model.SettlementDateDO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Mabowen
 * @date 2020-07-24 13:55
 */
@Slf4j
public class HolidayService {
    private static final String CHARSET = "GBK";

    public List<SettlementDateDO> createSettlementDays(String year, String month) {
        List<CalendarApiData> calendarApiData = listHolidays(year, month);
        AssertUtil.assertCollectionNotEmpty(calendarApiData, "calendarApiData cannot be null");

        if (calendarApiData.get(0) != null) {
            List<Almanac> almanacs = calendarApiData.get(0).getAlmanacs();
            if (CollUtil.isNotEmpty(almanacs)) {
                List<String> dates = almanacs.stream().map(Almanac::getDate).collect(Collectors.toList());
//        System.out.println(GsonUtil.beanToJson(dates));

                List<DateList> restDays = new ArrayList<>();
                List<Holiday> holidays = calendarApiData.get(0).getHolidays();

                if (CollUtil.isNotEmpty(holidays)) {
                    holidays.forEach(h -> {
                        restDays.addAll(h.getList());
                    });
                    //去重
                    List<DateList> collect = restDays.stream()
                            .collect(Collectors.collectingAndThen(
                                    Collectors.toCollection(() -> new TreeSet<>(
                                            Comparator.comparing(DateList::getDate)
                                    )),
                                    ArrayList::new
                                    )
                            );
//        System.out.println(GsonUtil.beanToJson(collect));
                    Map<String, DateList> dateListMap = collect.stream().collect(Collectors.toMap(DateList::getDate, Function.identity()));

                    List<SettlementDateDO> dos = new ArrayList<>();
                    dates.forEach(d -> {
                        Date date = DateUtil.parseShort(d);
                        String dayOfWeek = DateUtil.getDayOfWeek(date);
                        if (StrUtil.isNotBlank(dayOfWeek)) {
                            SettlementDateDO dateDO = new SettlementDateDO();
                            dateDO.setCalendarDate(date);
                            dateDO.setDayOfWeek(dayOfWeek);
                            dateDO.setSourceType(1);//自动

                            /**
                             * 首先把周六日置成非结算日，周一至周五置成结算日
                             */
                            if ("星期六".equals(dayOfWeek) || "星期日".equals(dayOfWeek)) {
                                dateDO.setSettlementDay(2);
                                dateDO.setSettlementDayDesc("休息日");
                            } else {
                                dateDO.setSettlementDay(1);
                                dateDO.setSettlementDayDesc("结算日");
                            }

                            /**
                             * 检测接口返回的节假日列表，根据节假日和工作日重新设置是否结算日
                             */
                            DateList dateList = dateListMap.get(d);
                            if (dateList != null && dateDO.getCalendarDate().compareTo(DateUtil.parseShort(dateList.getDate())) == 0) {
                                if (1 == dateList.getStatus()) {
                                    dateDO.setSettlementDay(2);
                                    dateDO.setSettlementDayDesc("休息日");
                                } else {
                                    dateDO.setSettlementDay(1);
                                    dateDO.setSettlementDayDesc("结算日");
                                }
                            }

                            dos.add(dateDO);
                        }
                    });
//        System.out.println(GsonUtil.beanToJson(dos));

                    return dos;
                }
            }
        }

        return Collections.emptyList();
    }

    private List<CalendarApiData> listHolidays(String year, String month) {
        AssertUtil.assertNotEmpty(year, "年份不能为空");
        AssertUtil.assertNotEmpty(month, "月份不能为空");

        String query = year + "年" + month + "月";
        String url = "http://opendata.baidu.com/api.php?query=" + query + "&resource_id=6018&format=json";

        String result = doGet(url);

        BaseApiData baseApiData = GsonUtil.jsonToBean(result, BaseApiData.class);
        if (0 == baseApiData.getStatus()) {
            return baseApiData.getData();
        }

        return Collections.emptyList();
    }

    private String doGet(String url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().source().readString(Charset.forName(CHARSET));
            } else {
                log.error(response.toString());
                throw new ServiceException(response.toString());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
