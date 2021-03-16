package com.mbw.office.validate.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author Mabowen
 * @date 2020-07-08 17:42
 */
@Data
@Accessors(chain = true)
public class TestPojo {
    private Long id;
    /**
     * 网点编号
     */
    @NotNull(message = "网点编号不能为空")
    private String bankCode;
    /**
     * 银行
     */
    @NotNull(message = "银行不能为空")
    private String bank;

    @Override
    public String toString() {
        return "TestPojo{" +
                "id=" + id +
                ", bankCode='" + bankCode + '\'' +
                ", bank='" + bank + '\'' +
                '}';
    }
}
