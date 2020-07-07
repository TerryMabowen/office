package com.mbw.office.demo.entity.permission;

import java.io.Serializable;

/**
 * 权限表
 * @author Mabowen
 * @date 2020-07-01 15:52
 */
public class PermissionPO implements Serializable {
    private static final long serialVersionUID = -7372985955141388266L;

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
