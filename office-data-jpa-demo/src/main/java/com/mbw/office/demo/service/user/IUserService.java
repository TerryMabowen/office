package com.mbw.office.demo.service.user;

import com.mbw.office.demo.biz.jalian.model.user.vo.UserVO;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-08 15:11
 */
public interface IUserService {

    UserVO getUserById(Long userId);

    List<UserVO> listUsers();
}
