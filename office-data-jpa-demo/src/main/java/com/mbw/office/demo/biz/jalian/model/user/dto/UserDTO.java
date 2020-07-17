package com.mbw.office.demo.biz.jalian.model.user.dto;

import lombok.Data;

/**
 *
 * @author Mabowen
 * @date 2020-07-01 16:26
 */
@Data
public class UserDTO {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密密码
     */
    private String passwordHash;
}
