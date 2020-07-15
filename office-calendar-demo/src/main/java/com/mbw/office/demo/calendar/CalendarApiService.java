package com.mbw.office.demo.calendar;

import retrofit2.Call;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-07-15 15:28
 */
public class CalendarApiService extends BaseApiManager {

    public void getCalendar(String query) {
        BaseHttpConfig httpConfig = new BaseHttpConfig();
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Content-Type", "application/json;charset=gbk");
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "application/json;charset=gbk");
        httpConfig.setRequestHeaders(requestHeaders);
        httpConfig.setResponseHeaders(responseHeaders);
        setHttpConfig(httpConfig);

        Call<BaseApiResponse<List<CalendarApiData>>> call = createApiProxy().getCalendar(query, httpConfig.getResourceId(), httpConfig.getFormat());
        BaseApiResponse<List<CalendarApiData>> response = execute(call);
        if ("0".equals(response.getStatus())) {
            System.out.println(response.getData());
        }
    }
}
