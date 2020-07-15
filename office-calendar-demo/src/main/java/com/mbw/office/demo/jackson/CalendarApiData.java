package com.mbw.office.demo.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-15 12:06
 */
@Data
public class CalendarApiData {
    @JsonProperty("StdStg")
    private Integer stdStg;

    @JsonProperty("StdStl")
    private Integer stdStl;

    @JsonProperty("_update_time")
    private String updateTime;

    @JsonProperty("cambrian_appid")
    private String cambrianAppId;

    @JsonProperty("almanac")
    private List<Almanac> almanacs;

    @JsonProperty("holiday")
    private List<Holiday> holidays;

    @JsonProperty("holidaylist")
    private List<HolidayList> holidayList;

    private String key;

    @JsonProperty("selectday")
    private String selectDay;

    private String url;

    private String loc;

    @JsonProperty("SiteId")
    private Integer siteId;

    @JsonProperty("_version")
    private Integer version;

    @JsonProperty("_select_time")
    private Long selectTime;

    @JsonProperty("clicklimit")
    private String clickLimit;

    @JsonProperty("ExtendedLocation")
    private String extendedLocation;

    @JsonProperty("OriginQuery")
    private String originQuery;

    private String tplt;

    @JsonProperty("resourceid")
    private String resourceId;

    @JsonProperty("fetchkey")
    private String fetchKey;

    @JsonProperty("role_id")
    private Integer roleId;

    @JsonProperty("disp_type")
    private Integer dispType;

    @JsonProperty("appinfo")
    private String appInfo;

    @Data
    private static class Almanac {
        private String avoid;

        private String date;

        private String suit;
    }

    @Data
    private static class Holiday {
        private String desc;

        private String festival;

        private List<DateList> list;

        @JsonProperty("list#num#baidu")
        private Integer listNumBaidu;

        private String name;

        private String rest;
    }

    @Data
    private static class DateList {
        private String date;

        private String status;
    }

    @Data
    private static class HolidayList {
        private String name;

        @JsonProperty("startday")
        private String startDay;
    }
}
