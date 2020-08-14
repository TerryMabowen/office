package com.mbw.office.common.lang.excel;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 导出属性实体
 *
 * @author Mabowen
 * @date 2020-08-14 15:11
 */
@Data
@AllArgsConstructor
public class ExportEntityProperties {
    /**
     * 变量名
     */
    private String key;
    /**
     * 列名
     */
    private String name;
    /**
     * 列排序
     */
    private Integer orderNum;
    /**
     * 列宽
     */
    private Double width;
    /**
     * 替换项
     */
    private String[] replaces;
    /**
     * 单元格类型,默认文本，10为数值
     */
    private Integer type;
    /**
     * 数字格式
     */
    private String numFormat;
    /**
     * 字典/扩展
     */
    private String dict;
}
