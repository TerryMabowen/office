package com.mbw.office.xxx;

import com.fasterxml.jackson.databind.JavaType;
import com.mbw.office.common.lang.http.OkHttpClientFactory;
import com.mbw.office.common.util.json.JacksonFactory;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-10 15:07
 */
public class HttpTest {

    @Test
    public void f1() {
        try {
            URI uri = new DefaultUriBuilderFactory().builder().build();
            ClientHttpRequest request = new OkHttp3ClientHttpRequestFactory()
                    .createRequest(uri, HttpMethod.GET);
            ClientHttpResponse response = request.execute();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void f2() throws IOException {
//        String url = "https://suggest.taobao.com/sug?code=utf-8&q=球鞋";
        String url = "http://www.kuaidi100.com/query?type=yuantong&postid=11111111111";
        String result = OkHttpClientFactory.getInstance().doGet(url);
        TestResponse<List<Result>> response = (TestResponse<List<Result>>) JacksonFactory.getInstance().jsonToBean(result, TestResponse.class);
        List<Result> results = response.getData();
        System.out.println(response);
        System.out.println(Arrays.toString(results.toArray()));
    }

    @Test
    public void f3() throws IOException {
//        String url = "https://suggest.taobao.com/sug?code=utf-8&q=球鞋";
        String url = "http://www.kuaidi100.com/query?type=yuantong&postid=11111111111";
        String result = OkHttpClientFactory.getInstance().doGet(url);
        JavaType javaType = JacksonFactory.getInstance().getConstructParametricJavaType(TestResponse.class, List.class);
        TestResponse<List<Result>> response = JacksonFactory.getInstance().jsonToComplicatedBean(result, javaType);
        List<Result> results = response.getData();
        System.out.println(response);
        System.out.println(Arrays.toString(results.toArray()));
    }
}
