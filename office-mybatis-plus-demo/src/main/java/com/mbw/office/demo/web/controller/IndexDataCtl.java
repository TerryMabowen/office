package com.mbw.office.demo.web.controller;

import com.mbw.office.common.response.PageResult;
import com.mbw.office.common.response.ResponseResults;
import com.mbw.office.demo.model.user.vo.UserVO;
import com.mbw.office.demo.service.user.IUserService;
import com.mbw.office.demo.web.controller.base.BaseDataCtl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-03 10:03
 */
@RestController
@RequestMapping("/index")
public class IndexDataCtl extends BaseDataCtl {
    @Autowired
    private IUserService userService;

    @GetMapping("/user/{userId}")
    public ResponseResults getUserById(@PathVariable("userId") Long userId) {
        try {
            UserVO user = userService.getUserWithRolesById(userId);
            return ResponseResults.newSuccess()
                    .setData(user);
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage(e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseResults listUsers() {
        try {
            List<UserVO> users = userService.listUserWithRoles();
            return ResponseResults.newSuccess()
                    .setData(users);
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage(e.getMessage());
        }
    }

    @PostMapping("page/users")
    public PageResult pageUsers() {
        try {
            List<UserVO> users = userService.listUserWithRoles();
            return PageResult.newSuccess()
                    .setData(users)
                    .setCount(users.size());
        } catch (Exception e) {
            return PageResult.newFailed()
                    .setMessage(e.getMessage());
        }
    }
}
