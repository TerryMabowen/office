package com.mbw.office.demo.biz.statistics.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 部门收入DTO
 *
 * @author Mabowen
 * @date 2020-08-05 19:21
 */
@Data
public class DepartmentAmountReceivableDTO {
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
     * 收入
     */
    private BigDecimal amountReceivable;

    private Long totalRecords;
}
