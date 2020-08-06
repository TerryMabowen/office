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
public class JlBillDetail {
    private Long departmentId;
    private String departmentName;
    private Date settlementDate;

    /**
     * 商户号---mch_id
     */
    private String mchId;

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
     * 交易金额 trade_money
     */
    private BigDecimal tradeMoney;

    /**
     * 手续费 commissions
     */
    private BigDecimal commissions;

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
     * 交易时间---trade_time
     */
    private Date tradeTime;

    /**
     * 清算日期---记账日期 liquidation_date
     */
    private Date liquidationDate;

    /**
     * 手续费封顶标记
     */
    private Integer feeFlag;

    /**
     * 渠道手续费---channel_commissions
     */
    private BigDecimal channelCommissions;

    /**
     * 附加手续费---extra_commissions
     */
    private BigDecimal extraCommissions;

    /**
     * 交易订单号---order_no
     */
    private String orderNo;

    /**
     * 原交易订单号---origin_order_no
     */
    private String originOrderNo;

    /**
     * 客户订单号---customer_order_no
     */
    private String customerOrderNo;

    /**
     * 非标价格商户---non_standard_merchant
     */
    private String nonStandardMerchant;

    /**
     * 免密免签标志 sign_password_flag
     */
    private Integer signPasswordFlag;

    /**
     * 渠道金额---channel_fee
     */
    private BigDecimal channelFee;

    /**
     * 处理方式---process_type
     */
    private Integer processType;
}
