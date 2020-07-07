package com.mbw.office.demo.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mbw.office.demo.entity.base.BaseEntity;
import com.mbw.office.demo.entity.role.RolePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户表
 * @author Mabowen
 * @date 2020-07-01 15:32
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "oc_sso_users", autoResultMap = true)
public class UserPO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -5535772926419600611L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密密码
     */
    private String passwordHash;

    /**
     * 角色集合
     */
    @TableField(exist = false)
    private Set<RolePO> roles = new HashSet<>(0);

    @Override
    protected Serializable pkVal() {
        return super.pkVal();
    }
}
