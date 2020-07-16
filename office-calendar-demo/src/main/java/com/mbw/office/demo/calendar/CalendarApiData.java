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
    /**
     *
     */
    @SerializedName("StdStg")
    private String stdStg;

    /**
     *
     */
    @SerializedName("StdStl")
    private String stdStl;

    /**
     * 更新时间
     */
    @SerializedName("_update_time")
    private String updateTime;

    /**
     *
     */
    @SerializedName("cambrian_appid")
    private String cambrianAppId;

    /**
     * 年历
     */
    @SerializedName("almanac")
    private List<Almanac> almanacs;

    /**
     * 节假日
     */
    @SerializedName("holiday")
    private List<Holiday> holidays;

    /**
     * 节假日列表
     */
    @SerializedName("holidaylist")
    private List<HolidayList> holidayList;

    /**
     * 关键字
     */
    private String key;

    /**
     * 选定日
     */
    @SerializedName("selectday")
    private String selectDay;

    /**
     * url
     */
    private String url;

    /**
     * 通信线路
     */
    private String loc;

    /**
     * 站点ID
     */
    @SerializedName("SiteId")
    private Integer siteId;

    /**
     * 版本
     */
    @SerializedName("_version")
    private Integer version;

    /**
     * 选择时间
     */
    @SerializedName("_select_time")
    private Long selectTime;

    /**
     * 点击限制
     */
    @SerializedName("clicklimit")
    private String clickLimit;

    /**
     * 扩展位
     */
    @SerializedName("ExtendedLocation")
    private String extendedLocation;

    /**
     * 起始查询
     */
    @SerializedName("OriginQuery")
    private String originQuery;

    /**
     *
     */
    private String tplt;

    /**
     * 资源ID
     */
    @SerializedName("resourceid")
    private String resourceId;

    /**
     * 读取关键字
     */
    @SerializedName("fetchkey")
    private String fetchKey;

    /**
     * 角色ID
     */
    @SerializedName("role_id")
    private Integer roleId;

    /**
     *
     */
    @SerializedName("disp_type")
    private Integer dispType;

    /**
     * 应用信息
     */
    @SerializedName("appinfo")
    private String appInfo;

    @Data
    private static class Almanac {
        /**
         * 避免做什么
         */
        private String avoid;

        /**
         * 日期
         */
        private Date date;

        /**
         * 适合做什么
         */
        private String suit;
    }

    @Data
    private static class Holiday {
        /**
         * 描述
         */
        private String desc;

        /**
         * 节假日
         */
        private Date festival;

        /**
         * 日期列表
         */
        private List<DateList> list;

        /**
         *
         */
        @SerializedName("list#num#baidu")
        private Integer listNumBaidu;

        /**
         * 节日名称
         */
        private String name;

        /**
         * 休息
         */
        private String rest;
    }

    @Data
    private static class DateList {
        /**
         * 日期
         */
        private Date date;

        /**
         * 状态
         */
        private Integer status;
    }

    @Data
    private static class HolidayList {
        /**
         * 节日名称
         */
        private String name;

        /**
         * 开始日期
         */
        @SerializedName("startday")
        private Date startDay;
    }
}
