<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page import="java.io.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="web_ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ERROR</title>
<link type="text/css" rel="stylesheet" href="${web_ctx}/libs/jquery-ui/jquery-ui.css" />
<link type="text/css" rel="stylesheet" href="${web_ctx}/style/default.css" />
</head>
<body class="ui-login">

	<!-- 	Header Include -->
	<!--jsp:include page="layout/header.jsp"/-->

	<!-- dialog -->
	<div id="login_form" class="dialog">
		<article>
			<h1>
				<span>系统出错</span>
			</h1>
			<section class="ui-layout-form-b">
				<h4>
				<!--
				<c:out value="${exception.message}"/> : 
				  -->
				
				</h4>
							
	
				<p>系统出错，请联系管理者</p>
				<P>
				<!--
				Exception Trace : 
				
				 -->

					联系电话: 02-3404-7288 (上午9点 - 下午6点. 周末，公休日除外)
				</p>
			</section>
		</article>
	</div>




	<!-- jQuery -->
	<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/external/jquery/jquery.js"></script>
	<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/jquery-ui.js"></script>
	<!-- Script -->
	<script>
		$(function() {

			$('.btn-login').button({
				icons : {
					primary : "ui-icon-key"
				}
			});

			$('#login_form').dialog({
				modal : false,
				autoOpen : true,
				resizable : false,
				draggable : false,
				closeOnEscape : false,
				width : 505,
				height : 300,
				buttons : {
					/* '确认' : function() {
						location.href = '${web_ctx}/'+parent.document.referrer;
					} */
				}
			});

			$('#login_form').parent().children('.ui-dialog-titlebar').hide();

			$("body").bind("keyup.myDialog", function(event) {
				// 27 == "esc"
				if (event.which == 27) {
					// TODO: close the dialog
					// unbind the event
					$("body").unbind("keyup.myDialog");
				}
			});

			$(window).bind('resize', function() {
				$('.dialog').dialog({
					position : {
						my : 'center',
						at : 'center',
						of : window
					}
				});
			}).trigger('resize');

		});
	</script>

</body>
</html>