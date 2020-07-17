package com.mbw.office.demo.web.controller;

import com.baidu.unbiz.fluentvalidator.Result;
import com.mbw.office.common.lang.response.PageResult;
import com.mbw.office.common.lang.response.ResponseResults;
import com.mbw.office.demo.jalian.model.user.dto.UserDTO;
import com.mbw.office.demo.jalian.model.user.vo.UserVO;
import com.mbw.office.demo.service.user.IUserService;
import com.mbw.office.demo.web.controller.base.BaseDataCtl;
import com.mbw.office.demo.web.controller.fb.UserFB;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-03 10:03
 */
@RestController
@RequestMapping("/index")
@Api(tags = "首页数据控制器")
public class IndexDataCtl extends BaseDataCtl {
    @Autowired
    private IUserService userService;

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "根据用户ID获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户ID", required=true, paramType = "path", dataType = "Long"),
    })
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
    @ApiOperation(value = "获取用户集合")
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

    @PostMapping("/page/users")
    @ApiOperation(value = "分页查询用户")
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

    @PostMapping("/user/add")
    @ApiOperation(value = "添加用户")
    public ResponseResults addUser(UserFB fb) {
        try {
            Result result = validate(fb);
            if (!result.isSuccess()) {
                return ResponseResults.newFailed()
                        .setMessage("参数校验失败，原因是：" + result.getErrors().toString());
            }

            UserDTO dto = UserDTO.builder()
                    .username(fb.getUsername())
                    .password(fb.getPassword())
                    .build();
            userService.addUser(dto);

            return ResponseResults.newSuccess()
                    .setMessage("添加用户成功");
        } catch (Exception e) {
            return ResponseResults.newFailed()
                    .setMessage("添加用户失败，原因是：" + e.getMessage());
        }
    }
}
