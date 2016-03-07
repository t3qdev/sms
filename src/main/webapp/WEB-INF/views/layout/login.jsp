<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="web_ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="${web_ctx}/libs/jquery-ui/jquery-ui.css" />
	<link type="text/css" rel="stylesheet" href="${web_ctx}/libs/jqGrid/css/ui.jqgrid.css" />
	<link type="text/css" rel="stylesheet" href="${web_ctx}/css/default.css" />
</head>
<body class="ui-login">
	<!-- head 영역 -->
    <tiles:insertAttribute name="header" ignore="true"/>
    <!-- //head 영역 -->
    <!-- 본문영역 -->
    <tiles:insertAttribute name="body" />
	<!-- //본문영역 -->
    <!-- 하단영역 -->
	<tiles:insertAttribute name="footer" ignore="true"/>
	<!-- //하단영역 -->
</body>
</html>

