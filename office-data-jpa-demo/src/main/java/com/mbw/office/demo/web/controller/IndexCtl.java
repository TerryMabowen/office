package com.mbw.office.demo.web.controller;

import com.mbw.office.demo.jalian.model.user.vo.UserVO;
import com.mbw.office.demo.service.user.IUserService;
import com.mbw.office.demo.web.controller.base.BaseCtl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mabowen
 * @date 2020-07-01 20:09
 */
@Controller
public class IndexCtl extends BaseCtl {
    @Autowired
    private IUserService userService;

    @GetMapping(value = {"","/","/index"})
    public String index(Model model) {
        UserVO vo = userService.getUserById(1L);
        model.addAttribute("user", vo);
        return "index.html";
    }
}
