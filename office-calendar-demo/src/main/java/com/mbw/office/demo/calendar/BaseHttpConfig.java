package com.mbw.office.demo.calendar;

import lombok.Data;

import java.util.Map;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-15 15:35
 */
@Data
public class BaseHttpConfig {
    private String baseUrl = "http://opendata.baidu.com/";
    private Long timeout = 1000000L;

    private String resourceId = "6018";

    private String format = "json";

    /**
     * 请求头
     */
    private Map<String, String> requestHeaders;

    /**
     * 响应头
     */
    private Map<String, String> responseHeaders;
}
