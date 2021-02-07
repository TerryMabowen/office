/**
 * 
 */
package com.mbw.office.learn.admin.security.handler;

import ai.bell.devops.admin.security.jwt.JwtUserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dinghq
 *
 */
public class TokenClearLogoutHandler implements LogoutHandler {
	private JwtUserServiceImpl jwtUserServiceImpl;
	
	public TokenClearLogoutHandler(JwtUserServiceImpl jwtUserServiceImpl) {
		this.jwtUserServiceImpl = jwtUserServiceImpl;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		clearToken(authentication);
	}
	
	protected void clearToken(Authentication authentication) {
		if(authentication == null){
			return;
		}
		UserDetails user = (UserDetails)authentication.getPrincipal();
		if(user!=null && user.getUsername()!=null){
			jwtUserServiceImpl.deleteUserLoginInfo(user.getUsername());
		}
	}
 
}
