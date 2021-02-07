/**
 * 
 */
package com.mbw.office.learn.admin.security.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dinghq
 *
 */
public class OptionsRequestFilter extends OncePerRequestFilter {

	private  final  static String OPTIONS = "OPTIONS";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(OPTIONS.equals(request.getMethod())) {
			response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
			response.setHeader("Access-Control-Allow-Headers", response.getHeader("Access-Control-Request-Headers"));
			return;
		}
		filterChain.doFilter(request, response);
	}
}
