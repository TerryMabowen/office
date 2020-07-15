package com.mbw.office.demo.calendar;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-15 12:06
 */
@Data
public class CalendarApiData {
    @SerializedName("StdStg")
    private String stdStg;

    @SerializedName("StdStl")
    private String stdStl;

    @SerializedName("_update_time")
    private String updateTime;

    @SerializedName("cambrian_appid")
    private String cambrianAppId;

    @SerializedName("almanac")
    private List<Almanac> almanacs;

    @SerializedName("holiday")
    private List<Holiday> holidays;

    @SerializedName("holidaylist")
    private List<HolidayList> holidayList;

    private String key;

    @SerializedName("selectday")
    private String selectDay;

    private String url;

    private String loc;

    @SerializedName("SiteId")
    private Integer siteId;

    @SerializedName("_version")
    private Integer version;

    @SerializedName("_select_time")
    private Long selectTime;

    @SerializedName("clicklimit")
    private String clickLimit;

    @SerializedName("ExtendedLocation")
    private String extendedLocation;

    @SerializedName("OriginQuery")
    private String originQuery;

    private String tplt;

    @SerializedName("resourceid")
    private String resourceId;

    @SerializedName("fetchkey")
    private String fetchKey;

    @SerializedName("role_id")
    private Integer roleId;

    @SerializedName("disp_type")
    private Integer dispType;

    @SerializedName("appinfo")
    private String appInfo;

    @Data
    private static class Almanac {
        private String avoid;

        private Date date;

        private String suit;
    }

    @Data
    private static class Holiday {
        private String desc;

        private Date festival;

        private List<DateList> list;

        @SerializedName("list#num#baidu")
        private Integer listNumBaidu;

        private String name;

        private String rest;
    }

    @Data
    private static class DateList {
        private Date date;

        private Integer status;
    }

    @Data
    private static class HolidayList {
        private String name;

        @SerializedName("startday")
        private Date startDay;
    }
}
