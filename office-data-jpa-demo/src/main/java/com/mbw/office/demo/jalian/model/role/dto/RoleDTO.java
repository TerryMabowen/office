package com.mbw.office.demo.jalian.model.role.dto;

import lombok.Data;

/**
 *
 * @author Mabowen
 * @date 2020-07-01 16:26
 */
@Data
public class RoleDTO {
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
}
