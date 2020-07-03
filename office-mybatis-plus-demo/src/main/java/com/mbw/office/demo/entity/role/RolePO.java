package com.mbw.office.demo.entity.role;

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
 * 角色表
 * @author Mabowen
 * @date 2020-07-01 15:51
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("oc_sso_roles")
public class RolePO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -1515580025645265875L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应用ID
     */
    @TableField(value = "app_id")
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
