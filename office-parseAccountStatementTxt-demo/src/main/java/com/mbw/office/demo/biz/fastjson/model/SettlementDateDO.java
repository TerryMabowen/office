package com.mbw.office.demo.biz.fastjson.model;

import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-24 13:55
 */
@Data
public class SettlementDateDO {
    /**
     * 日期,唯一
     */
    private Date calendarDate;

    /**
     * 星期几
     */
    private String dayOfWeek;

    /**
     * 来源类型; 1：自动， 2：手动
     */
    private Integer sourceType;
    private String sourceTypeDesc;

    /**
     * 是否结算日: 1：是， 2：否
     */
    private Integer settlementDay;
    private String settlementDayDesc;
}
