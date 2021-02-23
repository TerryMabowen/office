package com.mbw.office.cloud.web.config;

import com.mbw.office.cloud.web.security.AuthenticationFailureHandler;
import com.mbw.office.cloud.web.security.AuthenticationSuccessHandler;
import com.mbw.office.cloud.web.security.CustomHttpBasicServerAuthenticationEntryPoint;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2021-02-22 17:08
 */
//@Configuration
//@EnableWebFluxSecurity
public class SecurityConfig {
//    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

//    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    //    @Autowired
    private CustomHttpBasicServerAuthenticationEntryPoint customHttpBasicServerAuthenticationEntryPoint;

    //security的鉴权排除的url列表
    private static final String[] excludedAuthPages = {
            "/api/auth/login",
            "/api/auth/logout",
            "/health",
            "/api/socket/**"
    };

//    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()
                .pathMatchers(excludedAuthPages).permitAll()  //无需进行权限过滤的请求路径
                .pathMatchers(HttpMethod.OPTIONS).permitAll() //option 请求默认放行
                .anyExchange().authenticated()
                .and().httpBasic()
                .and().formLogin().loginPage("/auth/login")
                .authenticationSuccessHandler(authenticationSuccessHandler) //认证成功
                .authenticationFailureHandler(authenticationFailureHandler) //登陆验证失败
//            .and().exceptionHandling().authenticationEntryPoint(customHttpBasicServerAuthenticationEntryPoint)  //基于http的接口请求鉴权失败
                .and().csrf().disable()//必须支持跨域
                .logout().logoutUrl("/auth/logout").disable();

        return http.build();
    }

//    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); //默认不加密
    }
}
