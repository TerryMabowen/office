package com.mbw.office.demo.web.controller;

import com.mbw.office.common.lang.response.PageResult;
import com.mbw.office.common.lang.response.ResponseResults;
import com.mbw.office.demo.biz.jalian.model.user.vo.UserVO;
import com.mbw.office.demo.service.user.IUserService;
import com.mbw.office.demo.web.controller.base.BaseDataCtl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-07 15:07
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class IndexDataCtl extends BaseDataCtl {
    @Autowired
    private IUserService userService;

    @GetMapping("user/{userId}")
    public ResponseResults getUserById(@PathVariable("userId") Long userId) {
        try {
            UserVO vo = userService.getUserById(userId);
            return ResponseResults.newSuccess()
                    .setData(vo);
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage(e.getMessage());
        }
    }

    @GetMapping("users")
    public ResponseResults listUsers() {
        try {
            List<UserVO> vos = userService.listUsers();
            return ResponseResults.newSuccess()
                    .setData(vos);
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage(e.getMessage());
        }
    }

    @PostMapping("page/users")
    public PageResult pageUsers() {
        try {
            List<UserVO> vos = userService.listUsers();
            return PageResult.newSuccess()
                    .setData(vos)
                    .setCount(vos.size());
        } catch (Exception e) {
            return PageResult.newFailed()
                    .setMessage(e.getMessage());
        }
    }
}
