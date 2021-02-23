package com.mbw.office.cloud.web.security;

import com.mbw.office.cloud.biz.security.AuthUserService;
import com.mbw.office.cloud.biz.security.vo.AuthUserDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:24
 */
//@Component
public class SecurityUserDetailsServiceImpl implements ReactiveUserDetailsService {
    @Autowired
    private AuthUserService authUserService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        //todo 预留调用数据库根据用户名获取用户
        AuthUserDetailVO authUserDetail = authUserService.getUserByName(username);
        if (authUserDetail != null) {
            UserDetails user = User.withUsername(authUserDetail.getUsername())
//                    .password(MD5Encoder.encode(authUserDetail.getPassword(), username))
                    .password(authUserDetail.getPassword())
                    .roles("admin").authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))
                    .build();
            return Mono.just(user);
        } else {
            return Mono.error(new UsernameNotFoundException("User Not Found"));
        }
    }
}
