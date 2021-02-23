package com.mbw.office.cloud.biz.security.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:18
 */
@Data
public class AuthUserDetailVO implements UserDetails {
    private static final long serialVersionUID = -7474135070948589647L;

    private String username;

    private String password;

    public AuthUserDetailVO() {
    }

    public AuthUserDetailVO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
