package com.mbw.office.sso.web.controller.base;

import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.sso.model.user.vo.UserVO;
import com.mbw.office.sso.service.util.UserUtil;

/**
 * @author Mabowen
 * @date 2020-07-01 18:37
 */
public class BaseDataCtl {
    public UserVO currentUser() {
        UserVO user = UserUtil.getLoginUser();
        if (user == null) {
            throw new ServiceException("当前没有用户登录");
        }

        return user;
    }
}
