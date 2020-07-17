package com.mbw.office.demo.jalian.model.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mbw.office.demo.jalian.model.role.vo.RoleVO;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mabowen
 * @date 2020-07-01 16:26
 */
@Data
public class UserVO {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密密码
     */
//    @JsonIgnore
    private String passwordHash;

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
     * 角色集合
     */
    private Set<RoleVO> roles = new HashSet<>(0);
}
