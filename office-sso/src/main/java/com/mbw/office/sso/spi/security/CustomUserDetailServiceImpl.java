package com.mbw.office.sso.spi.security;

import cn.hutool.core.bean.BeanUtil;
import com.mbw.office.common.enums.EnumLogicStatus;
import com.mbw.office.common.util.validate.AssertUtil;
import com.mbw.office.sso.entity.user.UserPO;
import com.mbw.office.sso.model.role.vo.RoleVO;
import com.mbw.office.sso.model.user.vo.UserVO;
import com.mbw.office.sso.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Mabowen
 * @date 2020-07-01 16:48
 */
@Component
public class CustomUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AssertUtil.assertNotEmpty(username, "username can not be null.");

        UserPO userPo = userRepository.findByUsernameAndStatus(username, EnumLogicStatus.NORMAL.getValue());
        if (userPo == null) {
            throw new UsernameNotFoundException(String.format("%s用户不存在.", username));
        }
        UserVO userVo = new UserVO();
        BeanUtil.copyProperties(userPo, userVo);
        String password = passwordEncoder.encode(userVo.getPasswordHash());
        Set<RoleVO> roles = userVo.getRoles();
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if (!roles.isEmpty()) {
            for (RoleVO role : roles) {
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getCode());
                authorityList.add(grantedAuthority);
            }
        }

        return new User(userVo.getUsername(), password, authorityList);
    }
}
