package com.mbw.office.demo.biz.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 部门支出金额VO
 *
 * @author Mabowen
 * @date 2020-08-05 10:50
 */
@Data
public class DepartmentAmountPaidVO {
    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 总支出金额
     */
    private BigDecimal totalAmountPaid;
}
