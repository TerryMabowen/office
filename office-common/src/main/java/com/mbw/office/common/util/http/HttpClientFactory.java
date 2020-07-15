package com.mbw.office.common.util.http;

import cn.hutool.core.io.IoUtil;
import com.mbw.office.common.lang.exception.ServiceException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.Map;

/**
 * HttpClient工具类
 *
 * @author Mabowen
 * @date 2020-05-26 20:48
 */
@Slf4j
public class HttpClientFactory {
    private static HttpClientFactory factory;

    @Setter
    private static String CHARSET = "UTF-8";

    @Autowired
    @Getter @Setter
    private HttpConfig httpRequestConfig;

    private CloseableHttpClient httpClient;

    private CloseableHttpResponse response;

    public static HttpClientFactory getInstance() {
        if (factory != null) {
            return factory;
        }

        synchronized (HttpClientFactory.class) {
            if (factory == null) {
                factory = new HttpClientFactory();

                factory.init();
            }
        }

        return factory;
    }

    /**
     * http get请求
     *
     * @param url    请求url
     * @param params 请求参数
     * @return @{String}
     * @author Mabowen
     * @date 14:03 2020-06-15
     */
    public String doGet(String url, Map<String, String> params) {
        String result = "";
        HttpGet httpGet = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            URI uri = builder.build();
            log.info(String.format("RequestBody: %s", uri.getQuery()));
            // 创建http GET请求
            httpGet = new HttpGet(uri);
            httpGet.setConfig(httpRequestConfig.getRequestConfig());
            httpGet.setHeaders(httpRequestConfig.getHeaders());
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            // 返回200，请求成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(responseEntity, CHARSET);
                log.info(String.format("ResponseBody: %s", result));
            } else {
                log.error("请求失败！" + response.toString());
            }
        } catch (Exception e) {
            throw new ServiceException("doGet请求错误: " + e.getMessage() + ", 请求url为" + url, e);
        } finally {
            close();
        }

        return result;
    }

    private void init() {
        httpClient = HttpClientBuilder.create().build();
    }

    private void close() {
        IoUtil.close(this.httpClient);
        IoUtil.close(this.response);
//        try {
//            // 释放资源
//            if (this.httpClient != null) {
//                this.httpClient.close();
//            }
//
//            if (this.response != null) {
//                this.response.close();
//            }
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
    }
}
