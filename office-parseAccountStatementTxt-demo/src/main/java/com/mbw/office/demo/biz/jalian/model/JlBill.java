package com.mbw.office.demo.biz.jalian.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-23 09:42
 */
@Data
public class JlBill {
    private Long id;

    /**
     * 机构号
     */
    private String appId;

    /**
     * 商户号---mch_id
     */
    private String mchId;

    /**
     * 账单日期
     */
    private Date billDate;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 对账单集合
     */
    private List<JlBillDetail> records;

    /**
     * 交易总笔数
     */
    private Integer totalRecord;

    /**
     * 交易总金额
     */
    private BigDecimal totalFee;

    /**
     * 总手续费
     */
    private BigDecimal totalCommissions;

    /**
     * 渠道总手续费
     */
    private BigDecimal totalChannelCommissions;

    /**
     * 附加总手续费
     */
    private BigDecimal totalExtraCommissions;

    /**
     * 渠道总费用
     */
    private BigDecimal totalChannelFee;
}
