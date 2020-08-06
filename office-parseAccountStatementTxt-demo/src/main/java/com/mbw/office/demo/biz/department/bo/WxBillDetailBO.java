package com.mbw.office.demo.biz.department.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mabowen
 * @date 2020-08-06 11:42
 */
@Data
public class WxBillDetailBO {
    private Long id;

    private Long departmentId;

    private String departmentName;

    private Date settlementDate;

    /**
     * 应结订单金额
     */
    private BigDecimal totalFee;
}
