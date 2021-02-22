package com.mbw.office.cloud.biz.security.vo;

import lombok.Data;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:18
 */
@Data
public class AuthUserDetailVO {

    private String username;

    private String password;

    public AuthUserDetailVO() {
    }

    public AuthUserDetailVO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
