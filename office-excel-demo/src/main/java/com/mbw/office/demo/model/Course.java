package com.mbw.office.demo.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 课程
 *
 * @author Mabowen
 * @date 2020-09-11 17:08
 */
@Data
@ExcelTarget("course")
public class Course {
    @Excel(name = "课程名称", orderNum = "1")
    private String courseName;

    @Excel(name = "上课时间", exportFormat = "yyyy-MM-dd", orderNum = "2")
    private Date attendTime;

    @Excel(name = "班级", orderNum = "3", width = 20)
    private String classroom;

    @ExcelEntity(id = "th")
    private Teacher teacher;

    @ExcelCollection(name = "学生", orderNum = "6")
    private List<Student> students;
}
