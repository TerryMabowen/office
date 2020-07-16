package com.mbw.office.demo.model.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 *
 * @author Mabowen
 * @date 2020-07-01 16:26
 */
@Data
@Builder
public class UserDTO {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密密码
     */
    private String password;

    private Set<Long> ids;

    private String beginTime;

    private String endTime;
}
