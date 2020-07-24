package com.mbw.office.demo.calendar;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("list#num#baidu")
    private Integer listNumBaidu;

    private String name;

    private String rest;
}
