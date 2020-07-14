package com.mbw.office.demo.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mbw.office.common.lang.enums.EnumLogicStatus;
import com.mbw.office.common.util.bean.BeanKit;
import com.mbw.office.demo.entity.user.UserPO;
import com.mbw.office.demo.mapper.user.UserMapper;
import com.mbw.office.demo.model.user.vo.UserVO;
import com.mbw.office.demo.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-07 10:12
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVO getUserWithRolesById(Long userId) {
        UserPO po = userMapper.getUserWithRolesById(userId, EnumLogicStatus.NORMAL.getValue());
        log.info("UserPO: {}", po.toString());
        UserVO vo = BeanUtil.toBean(po, UserVO.class);
        log.info("UserVO: {}", vo.toString());
        return vo;
    }

    @Override
    public List<UserVO> listUserWithRoles() {
        List<UserPO> pos = userMapper.listUserWithRoles(EnumLogicStatus.NORMAL.getValue());
        log.info("UserPOS: {}", Arrays.toString(pos.toArray()));
        List<UserVO> vos = BeanKit.toBeans(pos, UserVO.class);
        log.info("UserVOS: {}", Arrays.toString(vos.toArray()));
        return vos;
    }
}
