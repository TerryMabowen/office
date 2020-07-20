package com.mbw.office.test.jackson;

import com.mbw.office.common.util.http.OkHttpClientFactory;
import com.mbw.office.common.util.json.GsonFactory;
import com.mbw.office.common.util.json.JacksonFactory;
import com.mbw.office.demo.calendar.BaseApiResponse;
import com.mbw.office.demo.jackson.BaseApiData;
import com.mbw.office.demo.jackson.CalendarApiData;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-15 16:30
 */
public class OkHttpTest {
    @Test
    public void f1() throws IOException {
        String url = getUrl("2020", "05");
        String result = getResult(url);

        JacksonFactory factory = JacksonFactory.getInstance();
        BaseApiData baseApiData = factory.jsonToBean(result, BaseApiData.class);
        if ("0".equals(baseApiData.getStatus())) {
            System.out.println(baseApiData);
        }
    }

    @Test
    public void f2() {
        String url = getUrl("2020", "4");
        String result = getResult(url);

        GsonFactory factory = GsonFactory.getInstance();
        BaseApiResponse baseApiResponse = factory.jsonToBean(result, BaseApiResponse.class);
        if ("0".equals(baseApiResponse.getStatus())) {
            System.out.println(baseApiResponse);
            System.out.println(baseApiResponse.getData().toString());
            List<CalendarApiData> calendarApiData = factory.jsonToList(baseApiResponse.getData().toString(), CalendarApiData.class);
            System.out.println(calendarApiData);
        }
    }

    private String getUrl(String year, String month) {
        String query = year + "年" + month + "月";
        return "http://opendata.baidu.com/api.php?query=" + query + "&resource_id=6018&format=json";
    }

    private String getResult(String url) {
        OkHttpClientFactory clientFactory = OkHttpClientFactory.getInstance();
        clientFactory.setCHARSET("GBK");
        return clientFactory.doGet(url);
    }
}
