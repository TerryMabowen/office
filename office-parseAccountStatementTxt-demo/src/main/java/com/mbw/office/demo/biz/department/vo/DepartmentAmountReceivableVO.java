package com.mbw.office.demo.biz.department.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 部门收入金额VO
 *
 * @author Mabowen
 * @date 2020-08-05 10:50
 */
@Data
public class DepartmentAmountReceivableVO {
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
     * 预算规则类型
     */
    private Integer budgetRuleType;

    /**
     * 预算规则描述
     */
    private String  budgetRuleDesc;

    /**
     * 总收入金额
     */
    private BigDecimal totalAmountReceivable;

    private Long totalRecords;
}
