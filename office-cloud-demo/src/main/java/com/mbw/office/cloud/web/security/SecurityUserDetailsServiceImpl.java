package com.mbw.office.cloud.web.security;

import com.mbw.office.cloud.biz.security.IUserService;
import com.mbw.office.cloud.biz.security.vo.AuthUserDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:24
 */
@Slf4j
@Component
public class SecurityUserDetailsServiceImpl implements ReactiveUserDetailsService {
    @Autowired
    private IUserService authUserService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        //todo 预留调用数据库根据用户名获取用户
        log.info(username + "访问数据库获取用户信息");
        AuthUserDetailVO authUserDetail = authUserService.getUserByName(username);
        if (authUserDetail != null) {
            UserDetails userDetail = User.withUsername(authUserDetail.getUsername())
//                    .password(MD5Encoder.encode(.getPassword(), username))
                    .password(authUserDetail.getPassword())
                    .roles("admin")
                    .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("admin"))
                    .build();
            return Mono.just(userDetail);
        } else {
            return Mono.error(new UsernameNotFoundException("User Not Found"));
        }
    }
}
