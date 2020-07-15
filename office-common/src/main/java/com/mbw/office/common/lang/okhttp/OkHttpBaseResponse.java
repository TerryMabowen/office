package com.mbw.office.common.lang.okhttp;

import com.google.gson.annotations.SerializedName;
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

    private String t;

    @SerializedName("set_cache_time")
    private String setCacheTime;

    private T data;
}
