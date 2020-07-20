package com.mbw.office.demo.calendar;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Mabowen
 * @date 2020-07-15 15:39
 */
@Data
public class BaseApiResponse<T> {
    private String status;

    private String t;

    @SerializedName("set_cache_time")
    private String setCacheTime;

    private T data;
}
