package com.mbw.office.demo.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mbw.office.demo.entity.user.UserPO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-07-02 15:31
 */
public interface UserMapper extends BaseMapper<UserPO> {

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "roles", many = @Many(select = "com.mbw.office.demo.mapper.role.RoleMapper.listRolesByUserIds", fetchType = FetchType.EAGER))
    })
    @Select("select u.* from oc_sso_users u where u.status = #{status} and u.id = #{userId}")
    UserPO getUserWithRolesById(@Param("userId") Long userId, @Param("status") Integer status);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "roles", many = @Many(select = "com.mbw.office.demo.mapper.role.RoleMapper.listRolesByUserIds", fetchType = FetchType.EAGER))
    })
    @Select("select u.* from oc_sso_users u where u.status = #{status}")
    List<UserPO> listUserWithRoles(@Param("status") Integer status);


    Page<UserPO> pageUsers(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("params") Map<String, Object> params);

    long getTotalCount(@Param("params") Map<String, Object> params);
}
