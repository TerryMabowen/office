package com.mbw.office.demo.web.controller;

import com.mbw.office.demo.model.user.vo.UserVO;
import com.mbw.office.demo.service.user.IUserService;
import com.mbw.office.demo.web.controller.base.BaseCtl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-01 20:09
 */
@Slf4j
@Controller
public class IndexCtl extends BaseCtl {
    @Autowired
    private IUserService userService;

    @GetMapping(value = {"","/","/index"})
    public String index(Model model) {
        UserVO user = userService.getUserWithRolesById(1L);
        model.addAttribute("user", user);
        List<UserVO> users = userService.listUserWithRoles();
        return "index.html";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login.html";
    }
}
