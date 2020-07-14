package com.mbw.office.sso.spi.enums;

import com.mbw.office.common.lang.enums.BaseEnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 应用启用状态枚举
 * @author Mabowen
 * @date 2020-07-01 15:57
 */
@Getter
@AllArgsConstructor
public enum  EnumAppEnableStatus implements BaseEnumStatus<Integer> {
    /**
     * 启用
     */
    ENABLED(1, "启用"),

    /**
     * 禁用
     */
    DISABLED(0, "禁用");

    private Integer value;

    private String desc;
}
