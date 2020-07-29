package com.mbw.office.demo.biz.fastjson;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.common.util.json.FastJsonUtil;
import com.mbw.office.common.util.validate.AssertUtil;
import com.mbw.office.demo.biz.fastjson.domain.Almanac;
import com.mbw.office.demo.biz.fastjson.domain.BaseApiData;
import com.mbw.office.demo.biz.fastjson.domain.DateList;
import com.mbw.office.demo.biz.fastjson.domain.Holiday;
import com.mbw.office.demo.biz.fastjson.model.SettlementDateDO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-24 19:05
 */
@Slf4j
@Service
public class SettlementDayService {
    private static final String CHARSET = "GBK";

    public List<SettlementDateDO> createSettlementDays(String year, String month) {
        String result = getResult(year, month);
        List<Almanac> almanacs = listAlmanacs(result);
        List<Holiday> holidays = listHolidays(result);

        if (CollUtil.isNotEmpty(almanacs)) {
            List<String> dates = almanacs.stream().map(Almanac::getDate).collect(Collectors.toList());
            List<DateList> holidayList = new ArrayList<>();
            if (CollUtil.isNotEmpty(holidays)) {
                List<DateList> restDays = new ArrayList<>();
                holidays.forEach(h -> {
                    restDays.addAll(h.getList());
                });
                //去重
                holidayList = restDays.stream()
                        .collect(Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(
                                        Comparator.comparing(DateList::getDate)
                                )),
                                ArrayList::new
                                )
                        );
            }
            Map<String, DateList> dateListMap = holidayList.stream().collect(Collectors.toMap(DateList::getDate, Function.identity()));

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
                    if (CollUtil.isNotEmpty(dateListMap)) {
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
                    }

                    dos.add(dateDO);
                }
            });

            return dos;
        }

        return Collections.emptyList();
    }

    private List<Almanac> listAlmanacs(String result) {
        try {
            List<Almanac> almanacs = new ArrayList<>();
            String data = FastJsonUtil.getJsonByKey(result, "data");
            if (StrUtil.isNotBlank(data) && !"[]".equals(data)) {
                Map<String, Object> jsonToMap = FastJsonUtil.jsonToMap(data, Object.class);
                if (CollUtil.isNotEmpty(jsonToMap)) {
                    for (Map.Entry<String, Object> entry : jsonToMap.entrySet()) {
                        if ("almanac".equals(entry.getKey()) && entry.getValue() != null) {
                            String value = String.valueOf(entry.getValue());
                            if (value.startsWith("[")) {
                                almanacs = FastJsonUtil.jsonToList(value, Almanac.class);
                            }
                        }
                    }
                }
            }
            return almanacs;
        } catch (Exception e) {
            log.error("listAlmanacs error: " + e.getMessage());
            throw new ServiceException("listAlmanacs error: " + e.getMessage(), e);
        }
    }

    private List<Holiday> listHolidays(String result) {
        try {
            List<Holiday> holidays = new ArrayList<>();
            String data = FastJsonUtil.getJsonByKey(result, "data");
            if (StrUtil.isNotBlank(data) && !"[]".equals(data)) {
                Map<String, Object> jsonToMap = FastJsonUtil.jsonToMap(data, Object.class);
                if (CollUtil.isNotEmpty(jsonToMap)) {
                    for (Map.Entry<String, Object> entry : jsonToMap.entrySet()) {
                        if ("holiday".equals(entry.getKey()) && entry.getValue() != null) {
                            String value = String.valueOf(entry.getValue());
                            if (value.startsWith("[")) {
                                holidays = FastJsonUtil.jsonToList(value, Holiday.class);
                            } else if (value.startsWith("{")) {
                                holidays.add(FastJsonUtil.jsonToBean(value, Holiday.class));
                            }
                        }
                    }
                }
            }

            return holidays;
        } catch (Exception e) {
            log.error("listHolidays error: " + e.getMessage());
            throw new ServiceException("listHolidays error: " + e.getMessage(), e);
        }
    }

    private String getResult(String year, String month) {
//        AssertUtil.assertValidateYear(year, "年份格式不正确");
//        AssertUtil.assertValidateMonth(month, "月份格式不正确");

        String query = year + "年" + month + "月";
        String url = "http://opendata.baidu.com/api.php?query=" + query + "&resource_id=6018&format=json";

        String result = doGet(url);
        BaseApiData baseApiData = FastJsonUtil.jsonToBean(result, BaseApiData.class);
        if (baseApiData != null && 0 == baseApiData.getStatus()) {
            return result;
        } else {
            AssertUtil.assertNotNull(baseApiData, "请求失败，返回为null");
            throw new ServiceException(baseApiData.toString());
        }
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
