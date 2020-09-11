package com.mbw.office.demo.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

/**
 * 教师
 *
 * @author Mabowen
 * @date 2020-09-11 17:08
 */
@Data
@ExcelTarget("teacher")
public class Teacher {
    @Excel(name = "姓名_th", groupName = "教师", orderNum = "4")
    private String name;

    @Excel(name = "性别_th", replace = {"女_0", "男_1", "未知_null"}, groupName = "教师", orderNum = "5")
    private Integer gender;
}
