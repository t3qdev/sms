<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="web_ctx" value="${pageContext.request.contextPath}" />


<!--  Header Include   -->
<!--jsp:include page="layout/header.jsp"/-->

<title>비밀번호 변경</title>
<article>
    
	<h1>
		<span>비밀번호관리</span>
	</h1>
	<form method="post" action="${web_ctx}/passwordChangeUpdate.do" id="passwordChangeForm" name="passwordChangeForm">
	    <div class="ui-layout-single">
	        <section class="ui-layout-form-b"> 
	            <ul>
	                <li>
	                    <label>이메일</label>
	                    <input type="text" id="userEml" name="userEml" value="${userEml }"  readonly="readonly" />
	                </li>
	                <li>
	                    <label>화명</label>
	                    <input type="text" id="userAlas" name="userAlas" value="${userAlas }"  readonly="readonly"/>
	                </li>
	                <li>
	                    <label>기존 비밀번호</label>
	                    <input type="password" id="userPwd_old" name="userPwd_old" size="20" />
	                </li>
	                <li>
	                    <label>신규 비밀번호</label>
	                    <input type="password" id="userPwd" name="userPwd" size="20" />
	                </li>
	                <li>
	                    <label>신규 비밀번호 확인</label>
	                    <input type="password" id="userPwd_again" name="userPwd_again" size="20" />
	               </li>
	            </ul>
	        </section>
	    </div>
	
		<div class="ui-layout-double">
	        <section class="ui-layout-action">
	
	        </section>
	        <section class="ui-layout-action">
	
	            <button class="btn-submit">저장</button>
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
		
		$('#passwordChangeForm').validate({
			rules : {
				userPwd_old :{
					required:true
				},
				userPwd: {
					required : true,
					maxlength :20
				},
				userPwd_again : {
					required : true,
					equalTo : "#userPwd"
				}
			},
			submitHandler: function(form){
				form.submit();
			}
		});
		
	});
</script>
