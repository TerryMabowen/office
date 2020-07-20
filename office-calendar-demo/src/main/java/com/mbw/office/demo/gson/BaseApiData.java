package com.mbw.office.demo.gson;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-15 12:07
 */
@Data
@ToString
public class BaseApiData {
    private Integer status;

    private String t;

    @SerializedName("set_cache_time")
    private String setCacheTime;

    private List<CalendarApiData> data;
}
