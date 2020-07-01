package com.mbw.office.sso.repositories.user;

import com.mbw.office.sso.entity.user.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Mabowen
 * @date 2020-07-01 16:35
 */
public interface UserRepository extends JpaRepository<UserPO, Long> {
    /**
     * 根据用户名获取用户
     * @param username
     * @param status
     * @return
     */
    UserPO findByUsernameAndStatus(String username, Integer status);

    /**
     * 根据ID和数据状态获取用户
     * @param id
     * @param status
     * @return
     */
    UserPO findByIdAndStatus(Long id, Integer status);

    /**
     * 根据数据状态获取所有用户
     * @param status
     * @return
     */
    List<UserPO> findAllByStatus(Integer status);
}
