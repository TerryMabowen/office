package com.mbw.office.learn.admin.config;

import com.mbw.office.learn.admin.security.JwtAuthenticationProvider;
import com.mbw.office.learn.admin.security.JwtLoginConfigurer;
import com.mbw.office.learn.admin.security.filter.OptionsRequestFilter;
import com.mbw.office.learn.admin.security.handler.JwtAccessDeniedHandler;
import com.mbw.office.learn.admin.security.handler.JwtRefreshSuccessHandler;
import com.mbw.office.learn.admin.security.handler.TokenClearLogoutHandler;
import com.mbw.office.learn.admin.security.jwt.JwtUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;

/**
 * @descrition
 * @author dinghq
 */
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 	配置安全模式
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/api/auth/token").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                //swagger 权限
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .cors()
                .and()
                .headers()
                .and()
                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)
                .apply(new JwtLoginConfigurer<>())
                .tokenValidSuccessHandler(jwtRefreshSuccessHandler())
                .permissiveRequestUrls("/logout")
                .and()
                .apply(new ExceptionHandlingConfigurer<>())
                .accessDeniedHandler(new JwtAccessDeniedHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(tokenClearLogoutHandler())
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and()
                .sessionManagement()
                .disable();
        // @formatter:on
    }

    /**
     * 	设置两个认证器提供者
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(jwtAuthenticationProvider());
    }

    /**
     * 	授权认证器
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean("jwtAuthenticationProvider")
    protected AuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtUserService());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        JwtUserServiceImpl jwtUserService = jwtUserService();
        return jwtUserService;
    }

    @Bean("jwtUserService")
    protected JwtUserServiceImpl jwtUserService() {
        return new JwtUserServiceImpl();
    }

    @Bean
    protected JwtRefreshSuccessHandler jwtRefreshSuccessHandler() {
        return new JwtRefreshSuccessHandler(jwtUserService());
    }

    @Bean
    protected TokenClearLogoutHandler tokenClearLogoutHandler() {
        return new TokenClearLogoutHandler(jwtUserService());
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
