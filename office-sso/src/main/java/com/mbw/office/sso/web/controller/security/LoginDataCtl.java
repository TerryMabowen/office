package com.mbw.office.sso.web.controller.security;

import com.mbw.office.common.response.ResponseResults;
import com.mbw.office.sso.web.controller.base.BaseDataCtl;
import com.mbw.office.sso.web.controller.security.fb.LoginUserFB;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mabowen
 * @date 2020-07-01 18:41
 */
@RestController
public class LoginDataCtl extends BaseDataCtl {

    @PostMapping("/login")
    public ResponseResults login(LoginUserFB fb) {
        return ResponseResults.newSuccess().setData(fb);
    }
}
