package com.mbw.office.learn.admin.security.handler;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.mbw.office.learn.admin.security.jwt.JwtAuthenticationToken;
import com.mbw.office.learn.admin.security.jwt.JwtToken;
import com.mbw.office.learn.admin.security.jwt.JwtUserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author dinghq
 * @descrition
 * @date 2020/5/29
 */
public class JwtRefreshSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 刷新时间间隔一天
     */
    private static final int TOKEN_REFRESH_INTERVAL = 24 * 60 * 60;

    private JwtUserServiceImpl jwtUserServiceImpl;

    public JwtRefreshSuccessHandler(JwtUserServiceImpl jwtUserServiceImpl) {
        this.jwtUserServiceImpl = jwtUserServiceImpl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();
        boolean shouldRefresh = shouldTokenRefresh(jwt.getIssuedAt());
        if (shouldRefresh) {
            JwtToken jwtToken = jwtUserServiceImpl.saveUserLoginInfo((UserDetails) authentication.getPrincipal());
            response.setHeader("Authorization", jwtToken.getToken());
            response.setHeader("RefreshToken", "true");
        }
    }

    /**
     * 是否应该刷新Token
     *
     * @param issueAt
     * @return
     */
    protected boolean shouldTokenRefresh(Date issueAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(TOKEN_REFRESH_INTERVAL).isAfter(issueTime);
    }
}
