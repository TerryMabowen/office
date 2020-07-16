package com.mbw.office.demo.web.controller.fb;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author Mabowen
 * @date 2020-07-03 10:08
 */
@Data
public class UserFB {
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
