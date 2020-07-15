package com.mbw.office.common.lang.okhttp;

import lombok.Data;
import lombok.ToString;

/**
 * OkHttp response
 *
 * @author Mabowen
 * @date 2020-06-02 09:36
 */
@Data
@ToString
public class OkHttpBaseResponse<T> {
    private Integer status;

    private String message;

    private T data;
}
