<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ page import="java.io.*"%>
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
				<h4 style="display:none">
				</h4>

				请输入发生错误的详细过程

				<!-- 메일로 보내기 위해 textArea에 넣어둔다. -->
				<textarea id="user_exception" cols="70" rows="5" style="margin-top:10px;"></textarea>
				<textarea id="exceptions" hidden>
				</textarea>
				<br>
				<br>
				<button type="button" class="button btn-sendError" id="myBtn">错误信息发送给管理者</button>
				<br>
				<br>
				联系电话: 02-3404-7288 (上午9点 - 下午6点. 周末，公休日除外)

			</section>
		</article>
	</div>




	<!-- jQuery -->
	<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/external/jquery/jquery.js"></script>
	<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/jquery-ui.js"></script>
	<!-- Script -->
	<script>
		function urlParam(name){
			var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
			if(results)	return results[1] || 0;
			return '';
		}
	
		$(function() {
			var decodedError = decodeURIComponent('${param.error}').replace(/\+/gi, " ");
			var decodedMessage = decodeURIComponent('${param.message}').replace(/\+/gi, " ");
			var exceptionMesssage='Exception Name : [' + decodedError + ']' + '**************************************' + 'Exception Details : [' + decodedMessage + ']' ;
			$('#exceptions').text(exceptionMesssage);
			
			//[오류 내용 관리자에게 전송] 버튼 클릭
			$('.btn-sendError').click(function() {
				// 메일 보내기 ajax
				$.ajax({
					url : "${web_ctx}/sendErrorMailToAdmin.ajax",
					async: false,
					type : "POST",
					data : {
						"mailContent" : $("#user_exception").val() + $("#exceptions").val()
					},
					success : function(list, textStatus, jqXHR) {
						//Sucess시, 처리
						document.getElementById("myBtn").disabled = true;
						alert("错误信息已传送给管理者");

					}
				});
				
			});
			
			
			$('.btn-login').button({
				icons : {
					primary : "ui-icon-key"
				}
			});
			
			$('.button').button();

			$('#login_form').dialog({
				modal : false,
				autoOpen : true,
				resizable : false,
				draggable : false,
				closeOnEscape : false,
				width : 505,
				height : 370,
				buttons : {
					'确认' : function() {
						//location.href = parent.document.referrer;
						window.history.back();
					}
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