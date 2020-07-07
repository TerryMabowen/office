package com.mbw.office.demo.mapper.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mbw.office.demo.entity.role.RolePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author Mabowen
 * @date 2020-07-07 13:38
 */
public interface RoleMapper extends BaseMapper<RolePO> {

    @Select("select r.* from oc_sso_roles r where r.status = 1 and r.id in (${roleIds})")
    List<RolePO> listRolesByIds(@Param("roleIds") Set<Long> roleIds);

    @Select("select r.* from oc_sso_roles r left join oc_sso_user_app_roles uar on uar.role_id = r.id where r.status = 1 and uar.user_id in (${userIds})")
    List<RolePO> listRolesByUserIds(@Param("userIds") Set<Long> userIds);
}
