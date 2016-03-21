<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="web_ctx" value="${pageContext.request.contextPath}" />


<!--  Header Include   -->
<!--jsp:include page="layout/header.jsp"/-->

<title>管理密码</title>
<article>
    
	<h1>
		<span>密码管理</span>
	</h1>
	<form method="post" action="${web_ctx}/passwordChangeUpdate.do" id="passwordChangeForm" name="passwordChangeForm">
	    <div class="ui-layout-single">
	        <section class="ui-layout-form-b"> 
	            <ul>
	                <li>
	                    <label>邮箱</label>
	                    <input type="text" id="userEml" name="userEml" value="${userEml }"  readonly="readonly" />
	                </li>
	                <li>
	                    <label>花名</label>
	                    <input type="text" id="userAlas" name="userAlas" value="${userAlas }"  readonly="readonly"/>
	                </li>
	                <li>
	                    <label>当前密码</label>
	                    <input type="password" id="userPwd_old" name="userPwd_old" size="20" />
	                </li>
	                <li>
	                    <label>新密码</label>
	                    <input type="password" id="userPwd" name="userPwd" size="20" />
	                </li>
	                <li>
	                    <label>重新输入密码</label>
	                    <input type="password" id="userPwd_again" name="userPwd_again" size="20" />
	               </li>
	            </ul>
	        </section>
	    </div>
	
		<div class="ui-layout-double">
	        <section class="ui-layout-action">
	
	        </section>
	        <section class="ui-layout-action">
	
	            <button class="btn-submit">保存</button>
	        </section>
	    </div>
    </form>
</article>






<!-- script -->
<script>


	$(function() {
		
/* 		var isCloseOK = "No";
		isCloseOK = "${param.isCloseOK}";
		if (isCloseOK == 'Yes'){
			alert("성공적으로 저장 되었습니다.");
			opener.parentReload();
			// window.close();
			window.open('about:blank', '_self').close(); // 익스플로러에서 확인창 뜨는것 방지
		}
		else if(isCloseOK == 'fail'){
			alert("저장에 실패 하였습니다.");
			opener.parentReload();
			// window.close();
			window.open('about:blank', '_self').close(); // 익스플로러에서 확인창 뜨는것 방지
		} */
	    // ajaxform
	    $('#passwordChangeForm').ajaxForm({
	        success: function(data){
	            alert('订单保存成功');

	          },
	          error : function(data){
	        	  alert('订单保存失败');
	          }
	    }); 	
		$('#passwordChangeForm').validate({
			rules : {
				userPwd_old :{
					required:true
				},
				userPwd: {
					required : true,
// 					minlength:6,
					maxlength :20,
					pwcheck : true
				},
				userPwd_again : {
					required : true,
// 					minlength:6,
					equalTo : "#userPwd",
					pwcheck : true
				}
			},
			 messages: {
				 userPwd_old: {
					 required: "required"
                 },
                 userPwd: {
                	 required: "required"
                 },
                 userPwd_again:{
                	 required: "required"
                 }
			},
			submitHandler: function(form){
//		 	    var newPassword = document.getElementById('changePasswordForm').newPassword.value;
//		 	    var minNumberofChars = 6;
//		 	    var maxNumberofChars = 16;
//		 	    var regularExpression  = /^[a-zA-Z0-9!@#$%^&*]{6,16}$/;
//		 	    alert(newPassword); 
//		 	    if(newPassword.length < minNumberofChars || newPassword.length > maxNumberofChars){
//		 	        return false;
//		 	    }
//		 	    if(!regularExpression.test(newPassword)) {
//		 	        alert("password should contain atleast one number and one special character");
//		 	        return false;
//		 	    }


				form.submit();
			}

		});
// 		checkPwd = function() {
// 		    var str = document.getElementById('pass').value;
// 		    if (str.length < 6) {
// 		        alert("too_short");
// 		        return("too_short");
// 		    } else if (str.length > 50) {
// 		        alert("too_long");
// 		        return("too_long");
// 		    } else if (str.search(/\d/) == -1) {
// 		        alert("no_num");
// 		        return("no_num");
// 		    } else if (str.search(/[a-zA-Z]/) == -1) {
// 		        alert("no_letter");
// 		        return("no_letter");
// 		    } else if (str.search(/[^a-zA-Z0-9\!\@\#\$\%\^\&\*\(\)\_\+\.\,\;\:]/) != -1) {
// 		        alert("bad_char");
// 		        return("bad_char");
// 		    }
// 		    alert("oukey!!");
// 		    return("ok");
// 		}
		 $.validator.addMethod("pwcheck",
		     function(value, element) {
	 		    if (value.length < 6) {

		        return false;
		    } else if (value.length > 50) {

		        return false;
		    } else if (value.search(/\d/) == -1) {

		        return false;
		    } else if (value.search(/[a-zA-Z]/) == -1) {

		        return false;
		    } else if (value.search(/[^a-zA-Z0-9\!\@\#\$\%\^\&\*\(\)\_\+\.\,\;\:]/) != -1) {

		        return false;
		    }

		    return true;
		        
		 },"password should contain atleast one number, minlength is 6");

//         $.validator.addMethod("pwcheck",
//                 function(value, element) {
//                     return /^[a-zA-Z0-9!@#$%^&*]{6,16}$/.test(value);
//             },"password should contain atleast one number and one special character");
		
	});
</script>
