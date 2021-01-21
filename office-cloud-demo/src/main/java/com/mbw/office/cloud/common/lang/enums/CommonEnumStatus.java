package com.mbw.office.cloud.common.lang.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mabowen
 * @date 2019-12-20 17:14
 */
@Getter
@AllArgsConstructor
public enum CommonEnumStatus implements BaseEnumStatus<Integer> {
    /**
     * 未知状态
     */
    UNKNOWN(-10000, "未知状态");

    private Integer value;
    private String desc;
}
