/**
 * 
 */
package com.mbw.office.learn.admin.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mbw.office.common.lang.response.ResponseResults;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author dinghq
 *
 */
public class HttpStatusLoginFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public HttpStatusLoginFailureHandler() {
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		response.setStatus(HttpStatus.SC_OK);
		ResponseResults result = ResponseResults.newFailed("Login Failed!");
		result.setCode(HttpStatus.SC_FORBIDDEN);
		
		response.getWriter().write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
	}

}
