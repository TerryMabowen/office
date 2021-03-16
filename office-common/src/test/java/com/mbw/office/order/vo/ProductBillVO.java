package com.mbw.office.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品清单VO
 *
 * @author Mabowen
 * @date 2021-02-04 15:35
 */
@Data
public class ProductBillVO implements Serializable {
    private static final long serialVersionUID = -1218051086098728074L;
    /**
     * 商品总价(计算得到)
     */
    private BigDecimal productPrice;

    /**
     * 商品类型
     */
    private Integer productType;
}
