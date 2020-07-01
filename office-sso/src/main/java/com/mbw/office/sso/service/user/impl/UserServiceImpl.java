package com.mbw.office.sso.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mbw.office.sso.entity.user.UserPO;
import com.mbw.office.sso.model.user.vo.UserVO;
import com.mbw.office.sso.repositories.user.UserRepository;
import com.mbw.office.sso.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-01 16:45
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserVO findByIdAndStatus(Long id, Integer status) {
        UserPO userPo = userRepository.findByIdAndStatus(id, status);
        UserVO userVo = new UserVO();
        BeanUtil.copyProperties(userPo, userVo);
        return userVo;
    }

    @Override
    public List<UserVO> findAllByStatus(Integer status) {
        List<UserPO> userPos = userRepository.findAllByStatus(status);
        if (userPos != null && !userPos.isEmpty()) {
            List<UserVO> userVos = new ArrayList<>();
            BeanUtil.copyProperties(userPos, userVos);
            return userVos;
        }
        return Collections.emptyList();
    }
}
