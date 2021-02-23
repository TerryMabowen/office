package com.mbw.office.cloud.web.ctl.login;

import com.mbw.office.cloud.common.lang.response.ResponseResults;
import com.mbw.office.cloud.web.ctl.base.BaseDataCtl;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:04
 */
@RestController
@RequestMapping("/api/auth")
public class LoginDataCtl extends BaseDataCtl {

    @PostMapping("/login")
    public ResponseResults login(@RequestParam("username") String username,
                                 @RequestParam("password") String password) {

        System.out.println(String.format("%s用户登录, 密码是%s", username, password));
        return ResponseResults.newSuccess();
    }

    @PostMapping("/logout")
    public ResponseResults logout(@RequestParam("username") String username) {
        System.out.println(String.format("%s用户登出", username));
        return ResponseResults.newSuccess();
    }
}
