package com.mbw.office.demo.service.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbw.office.common.enums.EnumLogicStatus;
import com.mbw.office.demo.entity.base.BaseEntity;
import com.mbw.office.demo.entity.user.UserPO;
import com.mbw.office.demo.mapper.user.UserMapper;
import com.mbw.office.demo.model.user.dto.UserDTO;
import com.mbw.office.demo.model.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-02 15:54
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public UserVO getUserById(Long id) {
        //方式一
        UserPO po = userMapper.getUserWithRolesById(1L, EnumLogicStatus.NORMAL.getValue());
        //方式二,不用mapper.xml
//        UserPO po = userMapper.selectById(id);
        log.info("userPO: {}", po.toString());
        UserVO vo = BeanUtil.toBean(po, UserVO.class);
        log.info("userVO: {}", vo.toString());
        return vo;
    }

    public List<UserVO> listUsers() {
        //方式一
        List<UserPO> pos = userMapper.listUsers(EnumLogicStatus.NORMAL.getValue());
        //方式二,不用mapper.xml
//        List<UserPO> pos = userMapper.selectList(new LambdaQueryWrapper<UserPO>()
//            .eq(BaseEntity::getStatus, EnumLogicStatus.NORMAL.getValue()));
        return convert(pos);
    }

    public Page<UserVO> pageUsers(int pageNo, int pageSize, UserDTO dto) {
        Page<UserVO> voPage = new Page<>(pageNo, pageSize);
        //方式二,不用mapper.xml
        LambdaQueryWrapper<UserPO> lambdaQueryWrapper = new LambdaQueryWrapper<UserPO>()
                .eq(BaseEntity::getStatus, EnumLogicStatus.NORMAL.getValue());
        if (!StrUtil.isEmpty(dto.getUsername())) {
            lambdaQueryWrapper.likeRight(UserPO::getUsername, dto.getUsername());
        }
        if (!StrUtil.isEmpty(dto.getPasswordHash())) {
            lambdaQueryWrapper.like(UserPO::getPasswordHash, dto.getPasswordHash());
        }

        lambdaQueryWrapper.in(UserPO::getId, dto.getIds())
                .between(BaseEntity::getCreatedTime, dto.getBeginTime(), dto.getEndTime())
                .orderByDesc(BaseEntity::getUpdatedTime);
        IPage<UserPO> poPage = userMapper.selectPage(new Page<>(pageNo, pageSize), lambdaQueryWrapper);
        log.info("poPage: poList--{}, currentPage--{}, pageSize--{}, totalCount--{}",
                Arrays.toString(poPage.getRecords().toArray()),
                poPage.getCurrent(), poPage.getSize(), poPage.getTotal());

        voPage.setTotal(poPage.getTotal());
        voPage.setCurrent(poPage.getCurrent());
        voPage.setSize(poPage.getSize());
        voPage.setRecords(convert(poPage.getRecords()));

        log.info("voPage: voList--{}, currentPage--{}, pageSize--{}, totalCount--{}, orders--{}",
                Arrays.toString(voPage.getRecords().toArray()),
                voPage.getCurrent(), voPage.getSize(), voPage.getTotal(),
                Arrays.toString(voPage.getOrders().toArray()));
        return voPage;
    }

    private List<UserVO> convert(List<UserPO> pos) {
        List<UserVO> vos = new ArrayList<>();
        if (pos != null && !pos.isEmpty()) {
            log.info("poList: {}", Arrays.toString(pos.toArray()));
            pos.forEach(po -> {
                vos.add(BeanUtil.toBean(po, UserVO.class));
            });
            log.info("voList: {}", Arrays.toString(vos.toArray()));
        }
        return vos;
    }
}
