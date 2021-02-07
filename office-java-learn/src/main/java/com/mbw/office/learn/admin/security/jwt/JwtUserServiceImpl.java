/**
 * 
 */
package com.mbw.office.learn.admin.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.collect.ImmutableSet;
import com.mbw.office.common.lang.exception.ServiceException;
import com.mbw.office.learn.service.user.UserService;
import com.mbw.office.learn.service.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.mbw.office.learn.biz.context.LoginResult;
import com.mbw.office.learn.biz.context.UcHelper;
import com.mbw.office.learn.biz.context.UserContext;

import java.util.Date;
import java.util.Set;

/**
 * @author dinghq
 *
 */
public class JwtUserServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserService userService;

	/**
	 *  Token的有效期
	 *  TODO 后面做成可以配置
	 */
	private long tokenLiftTime = 60 * 60 * 1000 * 24 * 7;

	/**
	 * TODO 固定加盐
	 */
	private final static String SALT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9";
	
	/**
	 * 	默认使用 bcrypt， strength=10
	 */
	public JwtUserServiceImpl() {
	}

	/**
	 * 生成JWT token的时候用
	 * @param username
	 * @return
	 */
	public UserDetails getUserLoginInfo(String username) {
		UserDetails user = loadUserByUsername(username);
		return user;
	}

	public JwtToken saveUserLoginInfo(UserDetails user) {
		// @formatter:off
		Algorithm algorithm = Algorithm.HMAC256(SALT);
		Date date = new Date(System.currentTimeMillis() + tokenLiftTime);
        String token = JWT.create()
        		.withSubject(user.getUsername())
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
        // @formatter:on
        
        JwtToken jwtToken = new JwtToken();
        
        jwtToken.setToken(token);
        jwtToken.setExpireTime(date);
        jwtToken.setUsername(user.getUsername());
        
        return jwtToken;
	}
	
	/**
	 * 获取登录结果
	 * @param username
	 * @return
	 */
	public LoginResult getJwtToken(String username) {
		UserDetails user = getUserLoginInfo(username);
		JwtToken jwtToken = saveUserLoginInfo(user);
		
		LoginResult loginResult = new LoginResult();
        loginResult.setToken(jwtToken.getToken());
        loginResult.setExpireTime(jwtToken.getExpireTime());
        loginResult.setUsername(jwtToken.getUsername());
        return loginResult;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/**
		 * TODO 动态配置权限
		 */
		Set<PermissonGrantedAuthority> permissons = ImmutableSet.<PermissonGrantedAuthority>builder()
				.build();
		
		try {
			UserVO vo = userService.queryUserByMobile(username);

			if(vo != null){
				UserContext uc = new UserContext(vo.getMobile(),
						vo.getPassword(), permissons);
				uc.setId(vo.getId());
				uc.setName(vo.getMobile());

				return uc;
			}
		} catch (ServiceException se) {
			// TODO: handle exception
		}

		return null;
	}

	public void deleteUserLoginInfo(String username) {
		//清除token
	}

	/**
	 * @descrition 验证token有效性
	 * @author dinghq
	 * @date 2020/6/1
	 * @param username
	 * @param token
	 * @return void
	 */
	public void vertifyToken(String username, String token){
		Algorithm algorithm = Algorithm.HMAC256(SALT);
		JWTVerifier verifier = JWT.require(algorithm).withSubject(username).build();
		verifier.verify(token);
	}

}
