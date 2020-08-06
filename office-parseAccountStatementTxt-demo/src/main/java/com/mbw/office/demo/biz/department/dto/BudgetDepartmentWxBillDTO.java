package com.mbw.office.demo.biz.department.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 预算部门微信账单
 *
 * @author Mabowen
 * @date 2020-08-05 14:22
 */
@Data
public class BudgetDepartmentWxBillDTO {
    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 结算日期
     */
    private Date settlementDate;

    /**
     * 收入金额
     */
    private BigDecimal amountReceivable;

    /**
     * 总记录数
     */
    private Long totalRecord;
}
