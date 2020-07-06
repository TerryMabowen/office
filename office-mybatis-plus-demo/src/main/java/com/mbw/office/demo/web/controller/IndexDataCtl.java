package com.mbw.office.demo.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbw.office.common.response.PageResult;
import com.mbw.office.common.response.ResponseResults;
import com.mbw.office.demo.model.user.dto.UserDTO;
import com.mbw.office.demo.model.user.vo.UserVO;
import com.mbw.office.demo.service.user.UserService;
import com.mbw.office.demo.web.controller.base.BaseDataCtl;
import com.mbw.office.demo.web.controller.fb.UserFB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-07-03 10:03
 */
@RestController
@RequestMapping("/index")
public class IndexDataCtl extends BaseDataCtl {
    @Autowired
    private UserService userService;

    @GetMapping("user/{userId}")
    public ResponseResults getUserById(@PathVariable("userId") Long userId) {
        try {
            UserVO user = userService.getUserById(userId);
            return ResponseResults.newSuccess()
                    .setData(user);
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage(e.getMessage());
        }
    }

    @GetMapping("users")
    public ResponseResults listUsers() {
        try {
            List<UserVO> users = userService.listUsers();
            return ResponseResults.newSuccess()
                    .setData(users);
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage(e.getMessage());
        }
    }

    @PostMapping("page")
    public PageResult pageUsers(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                                @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
                                UserFB fb) {
        try {
            UserDTO dto = BeanUtil.toBean(fb, UserDTO.class);

            Page<UserVO> voPage = userService.pageUsers(pageNo, pageSize, dto);
            return PageResult.newSuccess()
                    .setData(voPage.getRecords())
                    .setCount(voPage.getTotal());
        } catch (Exception e) {
            return PageResult.newFailed()
                    .setMessage(e.getMessage());
        }
    }
}
