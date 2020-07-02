package com.mbw.office.demo.service.user;

import com.mbw.office.demo.model.user.vo.UserVO;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-01 16:44
 */
public interface UserService {
    /**
     * 根据ID和数据状态获取用户
     * @param id
     * @param status
     * @return
     */
    UserVO getUserById(Long id, Integer status);

    /**
     * 根据数据状态获取所有用户
     * @param status
     * @return
     */
    List<UserVO> listUsers(Integer status);
}
