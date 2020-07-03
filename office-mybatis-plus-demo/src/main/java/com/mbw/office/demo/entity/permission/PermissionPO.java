package com.mbw.office.demo.entity.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mbw.office.demo.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 权限表
 * @author Mabowen
 * @date 2020-07-01 15:52
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("oc_sso_permissions")
public class PermissionPO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7372985955141388266L;

    @TableId(type = IdType.AUTO)
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
