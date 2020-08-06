package com.mbw.office.demo.biz.weixin.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mabowen
 * @date 2020-07-23 11:09
 */
@Data
public class WxBillDetail {
    private Long departmentId;
    private String departmentName;
    private Date settlementDate;

    /**
     * 交易时间.
     */
    private Date tradeTime;

    /**
     * 特约商户号.
     */
    private String subMchId;

    /**
     * 设备号.
     */
    private String deviceInfo;

    /**
     * 微信订单号.
     */
    private String transactionId;

    /**
     * 商户订单号.
     */
    private String outTradeNo;

    /**
     * 用户标识.
     */
    private String openId;

    /**
     * 交易类型.
     */
    private String tradeType;

    /**
     * 交易状态.
     */
    private String tradeState;

    /**
     * 付款银行.
     */
    private String bankType;

    /**
     * 货币种类.
     */
    private String feeType;

    /**
     * 应结订单金额.
     */
    private BigDecimal totalFee;

    /**
     * 代金券金额.
     */
    private BigDecimal couponFee;

    /**
     * 微信退款单号.
     */
    private String refundId;

    /**
     * 商户退款单号.
     */
    private String outRefundNo;

    /**
     * 退款金额.
     */
    private BigDecimal settlementRefundFee;

    /**
     * 充值券退款金额.
     */
    private BigDecimal couponRefundFee;

    /**
     * 退款类型.
     */
    private String refundChannel;

    /**
     * 退款状态.
     */
    private String refundState;

    /**
     * 商品名称.
     */
    private String body;

    /**
     * 商户数据包.
     */
    private String attach;

    /**
     * 手续费.
     */
    private BigDecimal poundage;

    /**
     * 费率.
     */
    private String poundageRate;

    /**
     * 订单金额.
     */
    private BigDecimal totalAmount;

    /**
     * 申请退款金额.
     */
    private BigDecimal appliedRefundAmount;

    /**
     * 费率备注.
     */
    private String feeRemark;

    /**
     * 退款申请时间
     */
    private Date refundTime;

    /**
     * 退款成功时间
     */
    private Date refundSuccessTime;
}
