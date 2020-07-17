package com.mbw.office.demo.biz.weixin.ctl.fb;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Mabowen
 * @date 2020-07-17 15:29
 */
@Data
public class BillFB {
    @NotBlank(message = "对账单日期不能为空")
    private String billDate;

    private String billType;
}
