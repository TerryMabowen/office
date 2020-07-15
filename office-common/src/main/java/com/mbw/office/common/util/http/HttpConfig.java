package com.mbw.office.common.util.http;

import lombok.Data;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.springframework.stereotype.Component;

/**
 * @author Mabowen
 * @date 2020-07-15 20:02
 */
@Data
@Component
public class HttpConfig {
    private RequestConfig requestConfig;
    private String charset;
    private Header[] headers;
}
