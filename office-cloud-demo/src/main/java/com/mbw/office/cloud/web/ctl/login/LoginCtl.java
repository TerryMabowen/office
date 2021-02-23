package com.mbw.office.cloud.web.ctl.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-23 15:56
 */
@Controller
@RequestMapping("/api/auth")
public class LoginCtl {

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
}
