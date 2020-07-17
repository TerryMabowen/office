package com.mbw.office.demo.model;

import lombok.Data;

import java.math.BigDecimal;

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
     * 交易金额
     */
    private BigDecimal moneyAmount;

    /**
     * 手续费
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
    private String paidTime;

    /**
     * 清算日期
     */
    private String accountDate;

    /**
     * 手续费封顶标记
     */
    private String feeFlag;

    /**
     * 渠道手续费
     */
    private BigDecimal channelFee;

    /**
     * 附加手续费
     */
    private BigDecimal extraFee;

    /**
     * 交易订单号
     */
    private String orderId;

    /**
     * 原交易订单号
     */
    private String originOrderId;

    /**
     * 客户订单号
     */
    private String userOrderId;

    /**
     * 非标价格商户
     */
    private String instdMerch;

    /**
     * 免密免签标志
     */
    private String signPwdFlag;

    /**
     * 渠道金额
     */
    private BigDecimal channelAmount;

    /**
     * 处理方式
     */
    private Integer procType;
}
