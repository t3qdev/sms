<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="web_ctx" value="${pageContext.request.contextPath}"/>
  <header>
	<h1>
		<span>SMS</span>
	</h1>
    <ul class="sms_theme">
    <li><a href="#t" data-sms2theme="default"><span></span></a></li>
    <li><a href="#t" data-sms2theme="blue"><span></span></a></li>
    <li><a href="#t" data-sms2theme="blue2"><span></span></a></li>
    <li><a href="#t" data-sms2theme="green"><span></span></a></li>
    </ul>
	<nav>
		<ul>
	   		<li><a href="${web_ctx}/orderManagementView.do" id="orderManagement" ><span>订单/交易管理</span></a></li>
        
        <sec:authorize access="hasRole('N000580100')">
        	 <li><a href="${web_ctx}/userManagementView.do" id="userManagement"><span>用户管理</span></a></li>
		</sec:authorize>
       
		</ul>
	</nav>
	<nav>
		<ul>
		<sec:authentication var="user" property="principal" />
		<li><a href="${web_ctx}/passwordChangeView.do">你好<span>${user.userAlasCnsNm}(${user.userAlasEngNm})</span><span>，欢迎来到SMS管理者页面</span></a></li>
		<li><a href="${web_ctx}/logOut.do" title="退出登录" class="icon-left"><span class="ic-logout">退出登录</span></a></li>
		</ul>
	</nav>
  </header>
  
  
  
  
  
  
