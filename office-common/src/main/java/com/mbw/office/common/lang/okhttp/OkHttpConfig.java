package com.mbw.office.common.lang.okhttp;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * OkHttp 配置
 *
 * @author Mabowen
 * @date 2020-06-02 09:14
 */
@Data
@Component
public class OkHttpConfig {
    /**
     * 基本url
     */
    private String baseUrl;

    /**
     *
     */
    private String appId;

    /**
     * Secret 密钥
     */
    private String appSecret;

    /**
     * 请求超时时间，单位ms
     */
    private Integer timeout;

    /**
     * 字符集
     */
    private String charset;

    /**
     * 请求头
     */
    private Map<String, String> requestHeaders;

    /**
     * 响应头
     */
    private Map<String, String> responseHeaders;
}
