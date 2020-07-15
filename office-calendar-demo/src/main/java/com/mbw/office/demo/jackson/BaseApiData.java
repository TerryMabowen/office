package com.mbw.office.demo.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-15 12:07
 */
@Data
@ToString
public class BaseApiData {
    private String status;

    private String t;

    @JsonProperty("set_cache_time")
    private String setCacheTime;

    private List<CalendarApiData> data;
}
