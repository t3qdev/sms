<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="web_ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=0.25,maximum-scale=4.0,user-scalable=yes">
	<meta name="format-detection" content="telephone=no">	
	<link type="text/css" rel="stylesheet" href="${web_ctx}/libs/jquery-ui/jquery-ui.css" />
	<link type="text/css" rel="stylesheet" href="${web_ctx}/libs/jqGrid/css/ui.jqgrid.css" />
	<link type="text/css" rel="stylesheet" href="${web_ctx}/css/default.css" />
	<link type="text/css" rel="stylesheet"  href="${web_ctx}/libs/multiselect/jquery.multiselect.css"/>
	<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/external/jquery/jquery.js"></script>
	<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/jquery-ui.autocomplete.js"></script>
	
<%-- 	<script type="text/javascript" src="${web_ctx}/libs/jqGrid/js/minified/jquery.jqGrid.min.js"></script> --%>
	<script type="text/javascript" src="${web_ctx}/libs/jqGrid/js/jquery.jqGrid.js"></script>
	
	<script type="text/javascript" src="${web_ctx}/libs/jqGrid/js/minified/i18n/grid.locale-en.js"></script>
	<script type="text/javascript" src="${web_ctx}/js/common.js"></script>
	<script type="text/javascript" src="${web_ctx}/libs/validation/jquery.validate.js"></script>
	<script type="text/javascript" src="${web_ctx}/libs/multiselect/jquery.multiselect.js"></script>
<%-- 	<script type="text/javascript" src="${web_ctx}/libs/multiselect/jquery.multiselect.css"></script> --%>
    <script type="text/javascript" src="${web_ctx}/js/common/lib/jquery.form.min.js" ></script>
</head>
<body class="">
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

