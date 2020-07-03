package com.mbw.office.demo.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbw.office.common.util.date.DateUtil;
import com.mbw.office.demo.model.user.dto.UserDTO;
import com.mbw.office.demo.model.user.vo.UserVO;
import com.mbw.office.demo.service.user.UserService;
import com.mbw.office.demo.web.controller.base.BaseCtl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mabowen
 * @date 2020-07-01 20:09
 */
@Slf4j
@Controller
public class IndexCtl extends BaseCtl {
    @Autowired
    private UserService userService;

    @GetMapping(value = {"","/","/index"})
    public String index(Model model) {
        UserVO user = userService.getUserById(1L);
        model.addAttribute("user", user);
        List<UserVO> users = userService.listUsers();
        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        UserDTO dto = UserDTO.builder()
                .username("130")
                .passwordHash("51")
                .ids(ids)
                .beginTime(DateUtil.getBeginDay(new Date()))
                .endTime(DateUtil.getEndDay(new Date()))
                .build();
        Page<UserVO> voPage = userService.pageUsers(1, 20, dto);
        return "index.html";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login.html";
    }
}
