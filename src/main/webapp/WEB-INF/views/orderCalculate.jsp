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
<style>
body{min-width:560px; background:#fff;}
</style>
<article>


	<h1>
		<span>结算</span>
	</h1>
    <form method="POST" action="${web_ctx}/orderCalculateSave.do" id="orderCalculateForm" name="orderCalculateForm">
		<div class="ui-layout-single">
	        <section class="ui-layout-form-b">
	            <ul>
	                <li>
	                    <label>日期</label>
	                    <input type="text" size="20" class="ui-calendar" id="bactPrvdDt" name="bactPrvdDt" value="${ordcalc.bactPrvdDt }"/>
	                </li>
	                <li>
	                    <label>金额</label>
	                    <input type="text" size="20" id="bactPrvdAmt" name="bactPrvdAmt" value="${ordcalc.bactPrvdAmt }"/>
	                </li>
	                <li>
	                    <label>留言</label>
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
	                    <button class="btn-cancel">取消</button>
	                    <button class="mr10" id="check">确认</button>
	                </section> 
	            </div>
	        </section>
	    </div>
    </form>
    
</article>

<script>
$(function(){
	//시작시 날짜 초기화
	$("#bactPrvdDt").val(dtToDate("${ordcalc.bactPrvdDt}"));
	
	//1.취소버튼
	$('.btn-cancel').click(function(){
		//1-1.업로드한 파일/사진/상품이미지등을 삭제한다. (덮어씌워줌으로 반드시 필요 X)
		window.close();			//IE에서 안될경우 window.open("about:blank","_self").close();
	});//end click
	
	$('#check').click(function(a){
		if(!isNaN($("#bactPrvdAmt").val())){
			var formData = $("#orderCalculateForm").serialize();
			$.ajax({
				type : "POST",
				url : '${web_ctx}/orderCalculateSave.do',
				data:	formData,
				async: false,
				cache : false,
				success:function(result){
					if(result){
						alert("结算确认");	
						if(opener.parent && !opener.parent.closed){
							opener.parent.location.reload();
							if(opener.opener.parent && !opener.opener.parent.closed && opener.opener.parent.reLoadJqgrid){
//									opener.opener.parent.location.reload();
								opener.opener.parent.reLoadJqgrid();
							}
						}
						window.open("about:blank","_self").close();
					}else{
						alert("结算确认失败");
						window.open("about:blank","_self").close();
					}
				}
			});//end $.ajax	
		}else{//endif
			a.preventDefault();
			alert("请确认金额");
		}

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
