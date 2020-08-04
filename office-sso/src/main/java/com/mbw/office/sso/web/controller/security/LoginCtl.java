package com.mbw.office.sso.web.controller.security;

import com.mbw.office.common.web.base.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mabowen
 * @date 2020-07-01 18:38
 */
@Controller
public class LoginCtl extends BaseCtl {

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
