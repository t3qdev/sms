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
	<form method="post" action="${web_ctx}/passwordChangeUpdate.ajax" id="passwordChangeForm" name="passwordChangeForm">
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
		   
		// 브라우저에 따라 caching 때문에 ajax 최신정보가 보이지 않게됨을 막음.
		jQuery.ajaxSetup({cache:false});   
		
	
		var num = 1;
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
                	 required: "required",
                	 equalTo:"Not equal to 新密码"
                 }
			},
			submitHandler: function(form){

				$('#passwordChangeForm').ajaxSubmit({
			        
					type : "POST",
					async: false,
					cache : false,				
					datatype : "json",
					success: function(data){

				        	if(data=="success"){
				            	alert("保存成功");  // 저장성공
				            }else{
				            	alert("false");
				            }

			          }
		        });   
				location.reload();

			}

		});

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


		
	});
</script>
