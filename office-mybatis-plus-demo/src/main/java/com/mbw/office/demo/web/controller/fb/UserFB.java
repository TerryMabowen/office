package com.mbw.office.demo.web.controller.fb;

import lombok.Data;

import java.util.Set;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-03 10:08
 */
@Data
public class UserFB {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 加密密码
     */
    private String passwordHash;

    private Set<Long> ids;

    private String beginTime;

    private String endTime;
}
