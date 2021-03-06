package com.mbw.office.demo.web.ctl.fb;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Mabowen
 * @date 2020-07-17 15:29
 */
@Data
public class BillFB {
    @NotEmpty(message = "对账单日期不能为空")
    private String billDate;

    private String billType;
}
