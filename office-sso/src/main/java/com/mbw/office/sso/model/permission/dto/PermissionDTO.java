package com.mbw.office.sso.model.permission.dto;

import lombok.Data;

import javax.persistence.Column;

/**
 *
 * @author Mabowen
 * @date 2020-07-01 16:26
 */
@Data
public class PermissionDTO {
    private Long id;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 父权限ID
     */
    private Long pid;

    /**
     * 父路径
     */
    private String parentPath;
}
