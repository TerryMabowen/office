package com.mbw.office.demo.model.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.demo.model.role.vo.RoleVO;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mabowen
 * @date 2020-07-01 16:26
 */
@Data
@ToString
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
    @DateTimeFormat(pattern = DateUtil.DEFAULT_PATTERN)
    @JsonFormat(pattern = DateUtil.DEFAULT_PATTERN)
    private Date createdTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DateUtil.DEFAULT_PATTERN)
    private Date updatedTime;

    /**
     * 角色集合
     */
    private Set<RoleVO> roles = new HashSet<>(0);
}
