package com.mbw.office.demo.biz.statistics.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 预算部门嘉联账单
 *
 * @author Mabowen
 * @date 2020-08-05 14:22
 */
@Data
public class BudgetDepartmentJlBillDTO {
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
     * 交易金额
     */
    private BigDecimal tradeMoney;

    /**
     * 手续费
     */
    private BigDecimal commissions;

    /**
     * 渠道手续费
     */
    private BigDecimal channelCommissions;

    /**
     * 附加手续费
     */
    private BigDecimal extraCommissions;

    /**
     * 渠道费
     */
    private BigDecimal channelFee;

    /**
     * 总记录数
     */
    private Long totalRecord;
}
