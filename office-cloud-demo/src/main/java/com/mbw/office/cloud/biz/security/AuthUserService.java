package com.mbw.office.cloud.biz.security;

import com.mbw.office.cloud.biz.security.vo.AuthUserDetailVO;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:20
 */
@Service
public class AuthUserService {

    public AuthUserDetailVO getUserByName(String username) {
        if ("admin".equals(username)) {
            return new AuthUserDetailVO("admin", "123456");
        }
        return null;
    }
}
