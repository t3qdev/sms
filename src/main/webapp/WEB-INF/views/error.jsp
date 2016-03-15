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
<title>에러</title>
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
				<span>시스템 오류</span>
			</h1>
			<section class="ui-layout-form-b">
				<%				
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				if(exception!=null){
						exception.printStackTrace(printWriter);
				}
				%>
				<h4 style="display:none">
					<!--
				<c:out value="${exception.message}"/> : 
				  -->
					<%
					out.println( stringWriter.toString().substring(0,stringWriter.toString().indexOf('\n')));
				%>
				</h4>

				에러발생시의 상황을 자세히 입력해 주세요.

				<!-- 메일로 보내기 위해 textArea에 넣어둔다. -->
				<textarea id="user_exception" cols="70" rows="5" style="margin-top:10px;"></textarea>
				<textarea id="exceptions" hidden>
					에러이름: 			
					<%out.println(stringWriter.toString().substring(0,stringWriter.toString().indexOf('\n')));%>
					
					에러내용:
					<%
					if(exception!=null){
					
						out.println(stringWriter);
					
					}
					printWriter.close();
					stringWriter.close();
					%>
				
				</textarea>
				<br>
				<br>
				<button type="button" class="button btn-sendError" id="myBtn">오류 내용 관리자에게 전송</button>
				<br>
				<br>
				문의전화: 02-3404-7288 (오전 9시 - 오후 6시. 토/일/공휴일 제외)

			</section>
		</article>
	</div>




	<!-- jQuery -->
	<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/external/jquery/jquery.js"></script>
	<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/jquery-ui.js"></script>
	<!-- Script -->
	<script>
		$(function() {

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
						alert("오류 내용을 관리자에게 전송했습니다.");

					},
					error : function(jqXHR, textStatus, errorThrown) {
						//Error시, 처리
						alert("에러");
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
					'확인' : function() {
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