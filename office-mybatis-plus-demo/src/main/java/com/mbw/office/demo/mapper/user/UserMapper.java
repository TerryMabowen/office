package com.mbw.office.demo.mapper.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbw.office.common.mybatis.CommonMapper;
import com.mbw.office.demo.entity.user.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-07-02 15:31
 */
@Repository
@Mapper
public interface UserMapper extends CommonMapper<UserPO> {

    UserPO getUserWithRolesById(@Param("userId") Long userId, @Param("status") Integer status);

    List<UserPO> listUsers(@Param("status") Integer status);

    Page<UserPO> pageUsers(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("params") Map<String, Object> params);

    long getTotalCount(@Param("params") Map<String, Object> params);
}
