package com.mbw.office.demo.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.common.lang.http.OkHttpClientFactory;
import com.mbw.office.common.util.json.JacksonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-15 11:52
 */
@Slf4j
@Component
public class CalendarApiService {

    public void queryCalendar(String year, String month) {
        try {
            String query = year + URLEncoder.encode("年", "UTF-8") + month + URLEncoder.encode("月", "UTF-8");
            String url = "http://opendata.baidu.com/api.php?query=" + query + "&resource_id=6018&format=json";

            String result = OkHttpClientFactory.getInstance()
                    .doGet(url);
            System.out.println(result);

            JacksonFactory jacksonFactory = JacksonFactory.getInstance();
            JavaType javaType = jacksonFactory.getConstructParametricJavaType(BaseApiData.class, List.class);
            BaseApiData apiData = jacksonFactory.jsonToComplicatedBean(result, javaType);
            System.out.println(apiData.toString());

            if ("0".equals(apiData.getStatus()) && apiData.getData() != null && !apiData.getData().isEmpty()) {
                List<CalendarApiData> calendarApiData = jacksonFactory.jsonToList(jacksonFactory.beanToJson(apiData.getData()), CalendarApiData.class);
                System.out.println(Arrays.toString(calendarApiData.toArray()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
