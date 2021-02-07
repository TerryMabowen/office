package com.mbw.office.learn.biz.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户上下文持有类
 * @author dinghq
 *
 */
public class UcHelper {

    public static UserContext getContext()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserContext loginUser = (UserContext)authentication.getPrincipal();
        return loginUser;
    }

    public static Long getUserId() {
        return getContext().getId();
    }
    
}
