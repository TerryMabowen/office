package com.mbw.office.sso.model.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;

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
