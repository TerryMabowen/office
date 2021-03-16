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
public class DtDepartment {
    @Excel(name = "dt_deprtment_id")
    private Long dtDepartmentId;

    @Excel(name = "dt_department_name")
    private String dtDepartmentName;

    @Excel(name = "oa_department_code")
    private String oaDepartmentCode;
}
