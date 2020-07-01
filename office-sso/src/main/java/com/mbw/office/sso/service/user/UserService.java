package com.mbw.office.sso.service.user;

import com.mbw.office.sso.model.user.vo.UserVO;

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
    UserVO findByIdAndStatus(Long id, Integer status);

    /**
     * 根据数据状态获取所有用户
     * @param status
     * @return
     */
    List<UserVO> findAllByStatus(Integer status);
}
