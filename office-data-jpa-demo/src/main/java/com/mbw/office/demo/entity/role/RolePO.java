package com.mbw.office.demo.entity.role;

import com.mbw.office.common.enums.EnumLogicStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 * @author Mabowen
 * @date 2020-07-01 15:51
 */
public class RolePO implements Serializable {
    private static final long serialVersionUID = -1515580025645265875L;

    private Long id;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 角色编号
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    private Integer status = EnumLogicStatus.NORMAL.getValue();

    private Date createdTime;

    private Date updatedTime;
}
