package com.mbw.office.demo.biz.statistics.dto;

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
     * 应结订单金额
     */
    private BigDecimal totalFee;

    /**
     * 代金券金额
     */
    private BigDecimal couponFee;

    /**
     * 退款金额
     */
    private BigDecimal settlementRefundFee;

    /**
     * 充值券退款金额
     */
    private BigDecimal couponRefundFee;

    /**
     * 手续费
     */
    private BigDecimal poundage;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 申请退款金额
     */
    private BigDecimal appliedRefundAmount;

    /**
     * 总记录数
     */
    private Long totalRecord;
}
