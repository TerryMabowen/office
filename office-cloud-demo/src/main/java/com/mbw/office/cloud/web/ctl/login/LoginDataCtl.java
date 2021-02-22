package com.mbw.office.cloud.web.ctl.login;

import com.mbw.office.cloud.common.lang.response.ResponseResults;
import com.mbw.office.cloud.web.ctl.base.BaseDataCtl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:04
 */
@RestController
public class LoginDataCtl extends BaseDataCtl {

    @PostMapping("/login")
    public ResponseResults login(@RequestParam("username") String username,
                                 @RequestParam("password") String password) {

        System.out.println(String.format("username: %s, password: %s", username, password));
        return ResponseResults.newSuccess();
    }
}
