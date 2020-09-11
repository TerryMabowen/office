package com.mbw.office.demo.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

/**
 * 学生
 *
 * @author Mabowen
 * @date 2020-09-11 17:09
 */
@Data
@ExcelTarget("student")
public class Student {
    @Excel(name = "姓名", orderNum = "6")
    private String name;

    @Excel(name = "性别", replace = {"女_0", "男_1", "未知_null"}, orderNum = "7")
    private Integer gender;

    @Excel(name = "年龄", type = 10, orderNum = "8")
    private Integer age;
}
