package com.mbw.office.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单支付记录VO
 *
 * @author Mabowen
 * @date 2021-02-02 11:43
 */
@Data
public class OrderPaidRecordVO implements Serializable {
    private static final long serialVersionUID = -6056076438984068455L;
    /**
     * 支付流水ID
     */
    private Integer id;

    /**
     * 部门
     */
    private Integer departmentId;
    private String departmentName;

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 订单总金额
     */
    private BigDecimal orderTotalMoney;

    /**
     * 流水支付金额
     */
    private BigDecimal paidMoney;

    /**
     * 手续费
     */
    private BigDecimal serviceFee;

    /**
     * 商品实际价格
     */
    private BigDecimal realPrice;

    /**
     * 商品购买数量
     */
    private Integer productNumber;

    /**
     * 商品类型
     */
    private Integer productType;

    /**
     * 财务确认状态
     */
    private Integer accountantConfirmState;

    /**
     * 确认时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date paidTime;

    /**
     * 备注
     */
    private String recordRemark;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 逻辑状态
     */
    private Integer status;

    /**
     * 订单商品
     */
    private List<ProductBillVO> productBills;
}
