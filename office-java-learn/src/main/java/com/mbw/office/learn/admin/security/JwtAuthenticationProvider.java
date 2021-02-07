/**
 * 
 */
package com.mbw.office.learn.admin.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.mbw.office.learn.admin.security.jwt.JwtAuthenticationToken;
import com.mbw.office.learn.admin.security.jwt.JwtUserServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import java.util.Calendar;

/**
 * @author dinghq
 *
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private JwtUserServiceImpl jwtUserService;

	public JwtAuthenticationProvider(JwtUserServiceImpl jwtUserService) {
		this.jwtUserService = jwtUserService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();
		
		if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())){
			throw new NonceExpiredException("Token expires");
		}

		String username = jwt.getSubject();
		UserDetails user = jwtUserService.getUserLoginInfo(username);
		
		if (user == null || user.getPassword() == null){
			throw new NonceExpiredException("Token expires");
		}

		try {
			jwtUserService.vertifyToken(username, jwt.getToken());
		} catch (Exception e) {
			throw new BadCredentialsException("JWT token verify fail", e);
		}
		
		JwtAuthenticationToken token = new JwtAuthenticationToken(user, jwt, user.getAuthorities());
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}
}
