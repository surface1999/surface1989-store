package com.surface1989.surface1989store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
		return true;
	}
}
