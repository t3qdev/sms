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
			message : {
				sllrId : {
					required : "아이디를 입력하세요.",
					minlength : jQuery.validator.format("아이디는 최소 {0}자입니다."),
					maxlength : jQuery.validator.format("아이디는 최대 20자입니다.")
				},
				sllrPwd : {
					required : "비밀번호를 입력하세요.",
					minlength : jQuery.validator.format("비밀번호는 최소 {0}자입니다."),
					maxlength : jQuery.validator.format("비밀번호는 최대 20자입니다.")
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

		$('#dialog_id').dialog({
			modal : true,
			autoOpen : false,
			resizable : false,
			draggable : false,
			width : 480,
			height : 250,
			buttons : {
				'확인' : function() {
				/* 	var crn1 = $('#crn1').val().trim();
					var crn2 = $('#crn2').val().trim();
					var crn3 = $('#crn3').val().trim();
					if (crn1 == "" || crn2 == "" || crn3 == "") {
						alert("사업자등록번호를 입력하세요.");
						return false;
					}
					 
					var crn = crn1 + '-' + crn2 + '-' + crn3;
					*/
					
					var crn=$('#crn').val().trim();
					if(crn==""){
						alert("사업자등록번호를 입력하세요.");
						return false;
					}
					
					$.ajax({
						type : "POST",
						url : "${web_ctx}/findSllrId.ajax",
						async : false,
						data :  {
							"crn" : crn
						}, 
						cache : false,
						success : function(data) {
							if (data == "") { 
								alert("해당 아이디가 없습니다.");
								return false;
							}
							var sllrId_list=''; 
							$('#dialog_id').dialog('close');
							$(data).each(function(index,item){
								sllrId_list+=item;
								sllrId_list+='</br>';

							});
							$('#mySllrId').val("");
							$('#mySllrId').html(sllrId_list);
							$('#dialog_id_submit').dialog('open');
							$('#crn').val("");
						}
					});
				},
				'취소' : function() { 
					$('#crn').val("");
					$(this).dialog('close'); 
				}
			}
		});

		$('#dialog_pw').dialog({
			modal : true,
			autoOpen : false,
			resizable : false,
			draggable : false,
			width : 480,
			height : 280,
			buttons : {
				'비밀번호 재발급 요청' : function() {
					var Id = $('#Id').val().trim();
					var crn_pwd=$('#crn_pwd').val().trim();
/* 					var crn11 = $('#crn11').val().trim();
					var crn22 = $('#crn22').val().trim();
					var crn33 = $('#crn33').val().trim();
					if (Id == "") {
						alert("아이디를 입력하세요.");
						return false;
					}
					if (crn11 == "" || crn22 == "" || crn33 == "") {
						alert("사업자등록번호를 입력하세요.");
						return false;
					}

					var crn = crn11 + '-' + crn22 + '-' + crn33;
 */
				 if (Id == "") {
						alert("아이디를 입력하세요.");
						return false;
					}
 
				 if (crn_pwd == "") {
						alert("사업자등록번호를 입력하세요.");
						return false;
					}
								 
 
					$.ajax({
						type : "POST",
						url : "${web_ctx}/requestMyPwd.ajax",
						async : false,
						data : {
							"id" : Id,
							"crn" : crn_pwd
						},
						cache : false,
						success : function(data) {
							if (data.result == "ok") {

								$('#dialog_pw').dialog('close');
								$('#mySllrPwd').html(data.sllrId + "의 운영담당자 이메일 " + data.email + " 으로 비밀번호를 전송하였습니다");
								$('#dialog_pw_submit').dialog('open'); 
								$('#crn_pwd').val("");
								$('#Id').val("");
								// alert("담당자 ${sllrId}의 이메일 ${email}으로 비밀번호를 전송하였습니다.");
								return false;
							}
							else{
								alert("해당 아이디가 존재하지 않거나 잘못된 사업자 등록번호를 입력하셨습니다.");
							}
						}
					});
				},
				'취소' : function() {
					$('#crn_pwd').val("");
					$('#Id').val("");
					$(this).dialog('close');
				}
			}
		});

		$('#dialog_id_submit').dialog({
			modal : true,
			autoOpen : false,
			resizable : false,
			draggable : false,
			width : 480,
			height : 200,
			open : function() {
				var mySllrId = $(this).parents('#dialog_id').data('mySllrId');
			},
			buttons : {
				'확인' : function() {
					$(this).dialog('close');
				}
			}
		});

		$('#dialog_pw_submit').dialog({
			modal : true,
			autoOpen : false,
			resizable : false,
			draggable : false,
			width : 480,
			height : 200,
			buttons : {
				'확인' : function() {
					$(this).dialog('close');
				}
			}
		});

		$('#find_id').click(function() {
			$('#dialog_id').dialog('open');
		});
		$('#change_pw').click(function() {
			$('#dialog_pw').dialog('open');
		});
		/* $('#btnLogin').click(function(){
			var id = $('#sllrId').val().trim();
			var pwd = $('#sllrPwd').val().trim();
			if(id == "") {
				alert("아이디를 입력하세요.");
				$('#sllrId').focus();
				$('#sllrId').select();
				return false;
			}else if(pwd == "") {
				alert("비밀번호를 입력하세요.");
				$('#sllrPwd').focus();
				$('#sllrPwd').select();
				return false;
			} */

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
