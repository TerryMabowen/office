package com.mbw.office.demo.biz.jalian.model.role.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mbw.office.demo.biz.jalian.model.permission.vo.PermissionVO;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author Mabowen
 * @date 2020-07-01 16:26
 */
@Data
public class RoleVO {
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

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    /**
     * 权限集合
     */
    private Set<PermissionVO> permissions;
}
