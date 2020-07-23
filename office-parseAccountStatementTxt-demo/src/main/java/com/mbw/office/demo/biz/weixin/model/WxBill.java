package com.mbw.office.demo.biz.weixin.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-23 11:09
 */
@Data
public class WxBill {
    private Long id;

    /**
     * 微信公众账号ID
     */
    private String appId;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 对账单日期
     */
    private Date billDate;

    /**
     * 账单类型:
     * ALL,
     * SUCCESS,
     * REFUND
     */
    private String billType;

    /**
     * 账单明细
     */
    private List<WxBillDetail> records;

    /**
     * 交易总单数
     */
    private Integer totalRecord;

    /**
     * 应结订单总金额
     */
    private BigDecimal totalFee;

    /**
     * 退款总金额
     */
    private BigDecimal totalRefundFee;

    /**
     * 充值券总金额
     */
    private BigDecimal totalCouponFee;

    /**
     * 手续费总金额
     */
    private BigDecimal totalPoundageFee;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 申请退款总金额
     */
    private BigDecimal totalAppliedRefundFee;
}
