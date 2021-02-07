package com.mbw.office.learn.admin.security.handler;

import com.mbw.office.learn.biz.lang.response.ResponseResults;
import com.mbw.office.learn.biz.utils.json.JacksonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @author dinghq
 *
 */
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException, ServletException {

		ResponseResults data = ResponseResults.newFailed("当前的接口没有被授权");
		data.setCode(HttpStatus.UNAUTHORIZED.value());

		response.setStatus(HttpStatus.OK.value());
		response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
		response.getWriter().write(JacksonUtil.beanToJson(data));
		
		response.flushBuffer();
	}
}
