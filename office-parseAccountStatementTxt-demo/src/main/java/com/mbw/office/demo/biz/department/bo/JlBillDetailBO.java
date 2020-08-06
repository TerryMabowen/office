package com.mbw.office.demo.biz.department.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mabowen
 * @date 2020-08-06 11:42
 */
@Data
public class JlBillDetailBO {
    private Long id;

    private Long departmentId;

    private String departmentName;

    private Date settlementDate;

    /**
     * 交易金额
     */
    private BigDecimal tradeMoney;
}
