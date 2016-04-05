<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="web_ctx" value="${pageContext.request.contextPath}" />

<!--  Header Include   -->
<!--jsp:include page="layout/header.jsp"/-->

<!-- dialog -->
<div id="login_form" class="dialog">
	<form id="loginForm" name="loginForm" action="${web_ctx}/j_spring_security_check" method="POST">
<%-- 	<form id="loginForm" name="loginForm" action="${web_ctx}/j_spring_security_check" method="POST"> --%>
		<article>
        <h1><span>登录</span></h1>
			<section class="ui-layout-form-b">
				<h2>欢迎来到SMS管理者页面</h2>
				<ul>
					<li>
						<label>帐号</label>
						<input id="sllrId" name="sllrId" type="text" value="${cookieId }" maxlength="20" />
						<input type="checkbox" id="cb_remember_id" name="cb_remember_id" />
						<label for="cb_remember_id">记住帐号</label>
					</li>
					<li>
						<label>密码</label>
						<!--  보안상 문제로 AUTOCOMPLETE="OFF" 추가 - 주엽 -->
						<input id="sllrPwd" name="sllrPwd" type="password" maxlength="20" AUTOCOMPLETE="OFF"/>
						<button id="btnLogin" name="btnLogin" class="btn-login">登录</button>
					</li>
				</ul>
			</section>
			<span id="loginResult">
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
					<font color="red">
						<%-- Your login attempt was not successful due to --%>
						帐号或者密码有误。
						<%-- <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" /> --%>
						<%--<spring:message code="AbstractUserDetailsAuthenticationProvider.badCredentials" /> --%>
					</font>
				</c:if>
			</span>

		</article>
		<footer>
			<p class="tal">
            忘记登录帐号或密码时请联系管理者<br>
            中国负责人 : 常遇春 (changyuchun@b5m.com)<br>
            韩国负责人 : 汤和 (tanghe@b5m.com)
      	   </p>
		</footer>
	</form>
</div>





<!-- jQuery -->
<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/external/jquery/jquery.js"></script>
<script type="text/javascript" src="${web_ctx}/libs/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="${web_ctx}/libs/validation/jquery.validate.js"></script>
<script type="text/javascript" src="${web_ctx}/libs/jquery-cookie/src/jquery.cookie.js"></script>

<!-- script -->
<script>
	$(document).ready(function() {
		var cookieId = $.cookie('savedId');
		if (cookieId == "") {
			$('#sllrId').val("");
			$('#cb_remember_id').attr('checked', false);
		} else {
			$('#sllrId').val(cookieId);
			$('#cb_remember_id').attr('checked', true);
		}
	});

	$(function() {
	    $('body').addClass('ui-login');
	    $('#login_form').dialog({
	        modal: false,
	        autoOpen: true,
	        resizable: false,
	        draggable: false,
	        closeOnEscape: true,
	        width:430,
	        height: 370,
	        open: function (event, ui) {
	            $(".ui-dialog-titlebar-close", $(this).parent()).hide();
	            $(".ui-dialog .ui-dialog-titlebar").hide();
	        }
	    });
		jQuery.ajaxSetup({cache:false});

		$('.btn-login').button({
			icons : {
				primary : "ui-icon-key"
			}
		});

		$('#loginForm').validate({
			rules : {
				sllrId : {
					required : true,
					maxlength : 20
				},
				sllrPwd : {
					required : true,
					maxlength : 20
				}
			},
			errorPlacement : function(error, element) {
// 				alert(error.text());
			},
			submitHandler : function(form) {
				if ($('input:checkbox[name=cb_remember_id]').is(':checked')) {
					$.cookie('savedId', $('#sllrId').val());
				} else {
					$.cookie('savedId', '');
				}
				form.submit();
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
