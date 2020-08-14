package com.mbw.office.common.lang.excel.mate.match;

import com.mbw.office.common.lang.enums.BaseEnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * eg: 导出比赛项类型
 *
 * @author Mabowen
 * @date 2020-08-14 15:28
 */
@Getter
@AllArgsConstructor
public enum EnumExportMatchType implements BaseEnumStatus<String> {
    /**
     * 例子
     */
    EG("eg", "例子");

    private String value;

    private String desc;
}
