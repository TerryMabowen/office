package com.mbw.office.common.lang.okhttp.domain;

import lombok.Data;

/**
 * 登录授权响应数据--例子
 *
 * @author Mabowen
 * @date 2020-06-02 10:20
 */
@Data
public class LoginResponseData {
    private String accessToken;
    private Integer expire;
}
