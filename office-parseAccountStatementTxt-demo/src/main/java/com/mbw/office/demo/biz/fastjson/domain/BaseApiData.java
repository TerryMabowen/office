package com.mbw.office.demo.biz.fastjson.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * @author Mabowen
 * @date 2020-07-15 12:07
 */
@Data
@ToString
public class BaseApiData<T> {
    private Integer status;

    private String t;

    @JSONField(name = "set_cache_time")
    private String setCacheTime;

    private T data;
}
