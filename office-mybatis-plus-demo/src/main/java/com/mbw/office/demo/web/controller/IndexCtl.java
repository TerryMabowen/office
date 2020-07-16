package com.mbw.office.demo.web.controller;

import com.mbw.office.demo.model.user.vo.UserVO;
import com.mbw.office.demo.service.user.IUserService;
import com.mbw.office.demo.web.controller.base.BaseCtl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mabowen
 * @date 2020-07-01 20:09
 */
@Slf4j
@Controller
@Api(tags = "首页页面跳转控制器", value = "没有参数")
public class IndexCtl extends BaseCtl {
    @Autowired
    private IUserService userService;

    @GetMapping(value = {"","/","/index"})
    @ApiOperation(value = "跳转index首页的方法", notes = "备注说明")
    public String index(Model model) {
        UserVO user = userService.getUserWithRolesById(1L);
        model.addAttribute("user", user);
        return "index.html";
    }

    @GetMapping("/login")
    @ApiOperation(value = "跳转登录页面的方法", notes = "备注说明")
    public String login(Model model) {
        return "login.html";
    }
}
