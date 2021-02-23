package com.mbw.office.cloud.biz.security;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mbw.office.cloud.biz.security.vo.AuthUserDetailVO;
import com.mbw.office.cloud.entity.user.UserPO;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-23 15:53
 */
public interface IUserService extends IService<UserPO> {
    AuthUserDetailVO getUserByName(String username);
}
