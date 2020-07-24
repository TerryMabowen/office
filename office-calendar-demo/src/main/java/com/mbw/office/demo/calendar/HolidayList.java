package com.mbw.office.demo.calendar;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-24 14:39
 */
@Data
public class HolidayList {
    private String name;

    @SerializedName("startday")
    private String startDay;
}
