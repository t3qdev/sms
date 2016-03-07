<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="web_ctx" value="${pageContext.request.contextPath}" />

<!--  Header Include   -->
<!--jsp:include page="layout/header.jsp"/-->
<sec:authentication var="user" property="principal" />
<article>


	<h1>
		<span>정산</span>
	</h1>
    <form method="POST" action="${web_ctx}/orderCalculateSave.do" id="orderCalculateForm" name="orderCalculateForm">
		<div class="ui-layout-single">
	        <section class="ui-layout-form-b">
	            <ul>
	                <li>
	                    <label>날짜</label>
	                    <input type="text" size="20" class="ui-calendar" id="bactPrvdDt" name="bactPrvdDt" value="${ordcalc.bactPrvdDt }"/>
	                </li>
	                <li>
	                    <label>금액</label>
	                    <input type="text" size="20" id="bactPrvdAmt" name="bactPrvdAmt" value="${ordcalc.bactPrvdAmt }"/>
	                </li>
	                <li>
	                    <label>메모</label>
	                    <input type="text" size="40" id="bactPrvdMemoCont" name="bactPrvdMemoCont" value="${ordcalc.bactPrvdMemoCont }"/>
	                    <input type="hidden" id="bactRegrEml" name="bactRegrEml" value="${user.username }"/>
	                    <input type="hidden" id="first" name="first" value="${fisrt }"/>
	                    <input type="hidden" id="ordNo" name="ordNo" value="${ordNo }"/>
	                </li>
	            </ul>
	        </section>
	    </div>

	    <div class="ui-layout-single">
	        <section>
	            <div class="ui-layout-double">
	            	<section class="ui-layout-action">
	                </section>
	                <section class="ui-layout-action">
	                    <button class="btn-cancel">취소</button>
	                    <button class="btn-submit mr10" id="check">확인</button>
	                </section> 
	            </div>
	        </section>
	    </div>
    </form>
    
</article>

<script>
$(function(){
	//시작시 날짜 초기화
	$("bactPrvdDt").val(dtToDate("${ordcalc.bactPrvdDt}"));
	
	
	$('#check').click(function(){
		var formData = $("#orderCalculateForm").serialize();
		$.ajax({
			type : "POST",
			url : '${web_ctx}/orderCalculateSave.do',
			data:	formData,
			async: false,
			cache : false,
			success:function(result){
				alert("성공적으로 저장 되었습니다.");
				
				window.open("about:blank","_self").close();
			}
		});//end $.ajax	

	//	
		
	});//end click
	

}) //end func

//20170121 => 2017-01-21 
function dtToDate(dt){
	var date;
	console.log(dt);
	console.log(dt.length);
	if(dt.length==8){
		date=dt.substr(0,4)+'-'+dt.substr(4,2)+'-'+dt.substr(6,2);
		return date;
	}else{
		return dt;
	}
}
	

</script>
