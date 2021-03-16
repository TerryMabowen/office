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
public class OaDepartment {
    @Excel(name = "oa_department_id")
    private Long oaDepartmentId;

    @Excel(name = "oa_department_name")
    private String oaDepartmentName;

    @Excel(name = "oa_department_code")
    private String oaDepartmentCode;
}
