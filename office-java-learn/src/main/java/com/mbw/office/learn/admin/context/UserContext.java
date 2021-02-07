package com.mbw.office.learn.admin.context;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author dinghq
 * @description 登录会员信息
 * @date 2020/6/18
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UserContext extends User {
    private static final long serialVersionUID = -1383844426711203187L;

    /**
     * 昵称
     */
    private String name;

    /**
     * 会员ID
     */
    private Long id;

    public UserContext(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
