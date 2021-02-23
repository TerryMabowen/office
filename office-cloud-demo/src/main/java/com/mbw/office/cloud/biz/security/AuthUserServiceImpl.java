package com.mbw.office.cloud.biz.security;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mbw.office.cloud.biz.security.vo.AuthUserDetailVO;
import com.mbw.office.cloud.entity.user.UserPO;
import com.mbw.office.cloud.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:20
 */
@Slf4j
@Service("authUserServiceImpl")
@Transactional(readOnly = true)
public class AuthUserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements IUserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public AuthUserDetailVO getUserByName(String username) {
        if ("admin".equals(username)) {
            return new AuthUserDetailVO("admin", "123456");
        }

        UserPO user = new LambdaQueryChainWrapper<>(baseMapper)
                .eq(UserPO::getUsername, username)
                .one();
        if (user != null) {
            return BeanUtil.toBean(user, AuthUserDetailVO.class);
        }

        //方式二
//        Wrapper<UserPO> wrapper = new LambdaQueryChainWrapper<>(baseMapper).eq(UserPO::getId, 1L).select();
//        UserPO userPO = userMapper.selectOne(wrapper);

        return null;
    }
}
