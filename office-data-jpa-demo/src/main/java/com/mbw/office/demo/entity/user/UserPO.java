package com.mbw.office.demo.entity.user;

import com.mbw.office.common.enums.EnumLogicStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @author Mabowen
 * @date 2020-07-01 15:32
 */
public class UserPO implements Serializable {
    private static final long serialVersionUID = -5535772926419600611L;

    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密密码
     */
    private String passwordHash;

    private Integer status = EnumLogicStatus.NORMAL.getValue();

    private Date createdTime;

    private Date updatedTime;
}
