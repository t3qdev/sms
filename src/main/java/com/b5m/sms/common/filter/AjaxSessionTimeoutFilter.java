package com.b5m.sms.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.filter.GenericFilterBean;

import com.b5m.sms.common.security.User;
import com.b5m.sms.web.controller.OrderManagementController;

/**
 * AJAX 요청시 권한 관련 오류가 생기면 redirect 시키는데, AJAX는 HTTP 상태 코드를 이용해서 에러를 확인해야 하므로,
 * redirect 되기전에 상태 코드를 전송하게함.
 * 
 */
public class AjaxSessionTimeoutFilter extends GenericFilterBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(AjaxSessionTimeoutFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		LOGGER.debug("AjaxSessionTimeoutFilter.doFilter()!!!!");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (isAjaxRequest(req)) {
			LOGGER.debug("This is ajax request!");
			try {
				
				// 세션체크
				if(isInvalidSession(req, res)) {
					LOGGER.debug("This is invalid Session!!!");
					res.setStatus(HttpStatus.UNAUTHORIZED.value());
		            return;
				}
				
				LOGGER.debug("Do Filter!!");
				chain.doFilter(req, res);
			} catch (AccessDeniedException e) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			} catch (AuthenticationException e) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	private boolean isInvalidSession(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession(false);
		SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		if(context != null && context.getAuthentication() != null) {
			User usr = (User)context.getAuthentication().getPrincipal();
			String userEml = usr.getUsername();
			LOGGER.debug("userEml : " + userEml);
			return userEml == null;
		}
		return true;
	}

	private boolean isAjaxRequest(HttpServletRequest req) {
		return "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
	}

}
