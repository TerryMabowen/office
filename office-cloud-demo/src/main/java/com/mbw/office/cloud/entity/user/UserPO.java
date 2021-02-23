package com.mbw.office.cloud.entity.user;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mbw.office.cloud.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:45
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(value = "sys_users", autoResultMap = true)
public class UserPO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -7636766652987645107L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密密码
     */
    private String password;
}
