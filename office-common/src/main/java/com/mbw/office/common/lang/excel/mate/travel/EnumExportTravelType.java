package com.mbw.office.common.lang.excel.mate.travel;

import com.mbw.office.common.lang.enums.BaseEnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * eg: 导出游学项类型
 *
 * @author Mabowen
 * @date 2020-08-14 15:29
 */
@Getter
@AllArgsConstructor
public enum EnumExportTravelType implements BaseEnumStatus<String> {
    /**
     * 例子
     */
    EG("eg", "例子");

    private String value;

    private String desc;
}
