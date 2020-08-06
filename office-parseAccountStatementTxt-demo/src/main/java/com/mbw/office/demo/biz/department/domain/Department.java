package com.mbw.office.demo.biz.department.domain;

import lombok.Data;

/**
 * @author Mabowen
 * @date 2020-08-06 10:30
 */
@Data
public class Department {
    private Long id;

    private Integer status;

    private String name;

    private String code;

    private Integer type;

    private String parentDepartmentIds;

    private String inChargeName;

    private Long parentId;

    private String parentName;

    private Long dingdingDeptId;

    private SchoolInfo schoolInfo;

    private Boolean containsChildren;

    private Integer departmentState;
}
