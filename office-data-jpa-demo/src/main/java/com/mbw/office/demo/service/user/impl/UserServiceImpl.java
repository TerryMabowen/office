package com.mbw.office.demo.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mbw.office.common.util.bean.BeanKit;
import com.mbw.office.demo.entity.user.UserPO;
import com.mbw.office.demo.model.user.vo.UserVO;
import com.mbw.office.demo.repositories.user.UserRepository;
import com.mbw.office.demo.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-08 15:12
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserVO getUserById(Long userId) {
        UserPO po = userRepository.findById(userId).orElse(new UserPO());
        log.info("UserPO: {}", po.toString());
        UserVO vo = BeanUtil.toBean(po, UserVO.class);
        log.info("UserVO: {}", vo.toString());
        return vo;
    }

    @Override
    public List<UserVO> listUsers() {
        List<UserPO> pos = userRepository.findAll();
        log.info("UserPOS: {}", Arrays.toString(pos.toArray()));
        List<UserVO> vos = BeanKit.toBeans(pos, UserVO.class);
        log.info("UserVOS: {}", Arrays.toString(vos.toArray()));
        return vos;
    }
}
