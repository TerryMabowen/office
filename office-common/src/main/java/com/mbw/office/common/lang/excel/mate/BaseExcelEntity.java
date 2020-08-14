package com.mbw.office.common.lang.excel.mate;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mabowen
 * @date 2020-01-04 19:49
 */
@Data
@NoArgsConstructor
public class BaseExcelEntity {
    @ExcelIgnore
    private Long id;

    /**
     * 序号
     */
    @Excel(name = "序号", orderNum = "1", type = 10, numFormat = "0", width = 10)
    private Long serialNumber;
}
