package com.mbw.office.test.gson;

import com.mbw.office.common.lang.http.OkHttpClientFactory;
import com.mbw.office.common.util.json.GsonFactory;
import com.mbw.office.demo.gson.BaseApiData;
import org.junit.Test;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-15 17:50
 */
public class OkHttpTest {
    @Test
    public void f1() {
        String url = getUrl("2020", "4");
        String result = getResult(url);

        GsonFactory factory = GsonFactory.getInstance();
        BaseApiData baseApiData = factory.jsonToBean(result, BaseApiData.class);
        System.out.println(baseApiData);
    }

    private String getUrl(String year, String month) {
        String query = year + "年" + month + "月";
//        return "http://opendata.baidu.com/api.php?query=" + query + "&resource_id=6018&format=json";
        return "https://www.baidu.com//api.php?query=" + query + "&resource_id=6018&format=json";
    }

    private String getResult(String url) {
        OkHttpClientFactory clientFactory = OkHttpClientFactory.getInstance();
        clientFactory.setCharset("GBK");
        return clientFactory.doGet(url);
    }
}
