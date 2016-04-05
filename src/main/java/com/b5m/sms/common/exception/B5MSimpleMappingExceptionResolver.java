package com.b5m.sms.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class B5MSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		System.out.println("B5MSimpleMappingExceptionResolver.doResolveException() triggered!!!!!!!!!");
		
		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);

		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			System.out.println("this is a ajax call!!!!");
			System.out.println("this is a ajax call!!!!");
			System.out.println("this is a ajax call!!!!");
			System.out.println("this is a ajax call!!!!");
			
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json; charset=utf-8");
			Integer statusCode = determineStatusCode(request, viewName);
			
			if(statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			
			String stackTrace;
			try {
				stackTrace = URLEncoder.encode(ExceptionUtils.getStackTrace(ex), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				stackTrace = "Unknown";
				e1.printStackTrace();
			}
			String error;
			try {
				error = URLEncoder.encode(ex.getMessage(), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				error = "unknown";
				e1.printStackTrace();
			}
			String responseText = "{\"stackTrace\":\"" + stackTrace + "\",\"error\":\"" + error + "\"}";
			
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.write(responseText);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(ex instanceof MaxUploadSizeExceededException){
				// 이 예외가 발생했을 시 응답이 가지 않는다.
				ex.printStackTrace();
			}
			return null;
		}
		return super.doResolveException(request, response, handler, ex);
	}
	
	
}
