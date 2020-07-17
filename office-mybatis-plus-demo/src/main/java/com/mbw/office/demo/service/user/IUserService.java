package com.mbw.office.demo.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mbw.office.demo.entity.user.UserPO;
import com.mbw.office.demo.jalian.model.user.dto.UserDTO;
import com.mbw.office.demo.jalian.model.user.vo.UserVO;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-07 10:10
 */
public interface IUserService extends IService<UserPO> {
    UserVO getUserWithRolesById(Long userId);

    List<UserVO> listUserWithRoles();

    void addUser(UserDTO dto);
}
