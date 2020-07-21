package com.mbw.office.demo.biz.jalian.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 对账单
 *
 * @author Mabowen
 * @date 2020-07-17 11:22
 */
@Data
public class AccountStatementData {
    /**
     * 商户号
     */
    private String merchNo;

    /**
     * 终端号
     */
    private String termNo;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 支付类型
     */
    private String payWay;

    /**
     * 交易卡号
     */
    private String tradeCardNo;

    /**
     * 交易金额 money
     */
    private BigDecimal moneyAmount;

    /**
     * 手续费 charge
     */
    private BigDecimal serviceFee;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 流水号
     */
    private String serialNo;

    /**
     * 系统参考号
     */
    private String sysRefNo;

    /**
     * 授权码
     */
    private String authCode;

    /**
     * 应答码
     */
    private String retCode;

    /**
     * 交易时间
     */
    private Date paidTime;

    /**
     * 清算日期---记账日期
     */
    private Date accountDate;

    /**
     * 手续费封顶标记
     */
    private Integer feeFlag;

    /**
     * 渠道手续费---channel_charge
     */
    private BigDecimal channelFee;

    /**
     * 附加手续费---extra_charge
     */
    private BigDecimal extraFee;

    /**
     * 交易订单号---order_no
     */
    private String orderId;

    /**
     * 原交易订单号---origin_order_no
     */
    private String originOrderId;

    /**
     * 客户订单号---customer_order_no
     */
    private String userOrderId;

    /**
     * 非标价格商户---non_standard_merch
     */
    private String instdMerch;

    /**
     * 免密免签标志 sign_password_flag
     */
    private Integer signPwdFlag;

    /**
     * 渠道金额---channel_fee
     */
    private BigDecimal channelAmount;

    /**
     * 处理方式
     */
    private Integer procType;
}
