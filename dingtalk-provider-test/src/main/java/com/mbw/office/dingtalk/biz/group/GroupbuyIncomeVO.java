package com.mbw.office.dingtalk.biz.group;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 团购收入
 *
 * @author Mabowen
 * @date 2020-09-02 11:42
 */
@Data
public class GroupbuyIncomeVO {
    /**
     * 钉钉部门ID
     */
    private Long dtDeptId;

    /**
     * 团购订单总额
     */
    private BigDecimal price;

    /**
     * 团购总订单数
     */
    private Integer count;

    /**
     * 近卫军订单总额
     */
    private BigDecimal grdPrice;

    /**
     * 近卫军总订单数
     */
    private Integer grdCount;

    /**
     * 团购总收入
     */
    private BigDecimal groupBuyTotalMoney;
}
