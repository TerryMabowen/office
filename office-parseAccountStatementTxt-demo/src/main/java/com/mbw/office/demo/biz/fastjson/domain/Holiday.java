package com.mbw.office.demo.biz.fastjson.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-24 14:38
 */
@Data
public class Holiday {
    private String desc;

    private String festival;

    private List<DateList> list;

    @JSONField(name = "list#num#baidu")
    private Integer listNumBaidu;

    private String name;

    private String rest;
}
