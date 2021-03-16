package com.mbw.office.department.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-03-15 14:07
 */
@Data
public class Department {
    @Excel(name = "OA部门编码", orderNum = "1", width = 20)
    private String oaDepartmentCode;

    @Excel(name = "OA部门名称", orderNum = "2", width = 30)
    private String oaDepartmentName;

    @Excel(name = "钉钉部门编码", orderNum = "3", width = 20)
    private String dtDepartmentCode;

    @Excel(name = "钉钉部门名称", orderNum = "4", width = 30)
    private String dtDepartmentName;
}
