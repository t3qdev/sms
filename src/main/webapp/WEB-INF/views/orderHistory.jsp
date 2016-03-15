<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="web_ctx" value="${pageContext.request.contextPath}" />
<style>
body{min-width:550px; background:#fff;}
.order_table th,.order_table td{ text-align:center;}

</style>
<!--  Header Include   -->
<!--jsp:include page="layout/header.jsp"/-->
<sec:authentication var="user" property="principal" />
<article>
	<h1>
		<span>历史记录</span>
	</h1>
	<c:choose>
		<c:when test="${ordStatCd eq 'N000550100'}">
			<div class="history_step history_step1"></div>
		</c:when>
		<c:when test="${ordStatCd eq 'N000550200'}">
			<div class="history_step history_step2"></div>
		</c:when>
		<c:when test="${ordStatCd eq 'N000550300'}">
			<div class="history_step history_step3"></div>
		</c:when>
		<c:when test="${ordStatCd eq 'N000550400'}">
			<div class="history_step history_step4"></div>
		</c:when>
		<c:otherwise>
			<div class="history_step history_step5"></div>
		</c:otherwise>
	</c:choose>
	
	<div class="ui-layout-single">
        <section>
            <h2><span>Order Number: ${ordNo} </span></h2>
            <div class="ui-layout-double">
                <section class="ui-layout-action">
                </section>
                <section class="ui-layout-action">
                    <button class="btn-add mr10" id="history_add">添加历史记录</button>
                </section>
            </div>
            <div class="ui-layout-jqgrid">
                <table id="jqgrid_a"></table>
            </div>
        </section>
    </div>
    <div class="ui-layout-double">
        <section class="ui-layout-action">

        </section>
        <section class="ui-layout-action">

            <button class="btn-save" id="btn-save">保存</button>
        </section>
    </div>

</article>

<script>
$(function(){
	// 브라우저에 따라 caching 때문에 ajax 최신정보가 보이지 않게됨을 막음.
	jQuery.ajaxSetup({cache:false}); 
	
	// SMS_MS_ORD 상태가 DROP(N000560100) 일 때에는, 본 화면은 기능하지 않는다.
	var ordStatCd = "${ordStatCd}";
	if(ordStatCd == "N000560100"){
		$("#history_add").hide();
		$("#btn-save").hide();
	}
	
	// [+히스토리 추가] 버튼을 눌렀을 때, Jqgrid 에서 한 행 추가 하기 위해 생성한 변수
	cli_num = 0;	
	//////////////////////////////////////////////////////// jqgrid로딩 및 jqgrid 세팅 ///////////////////////////////////////////////////////
    $('#jqgrid_a').jqGrid({
		url : "${web_ctx}/orderHistoryLoad.ajax",
		ajaxGridOptions : {async:false},    // 동기로 변환
		datatype : "json",
    	width: 1200,
    	postData :{ordNo : ${ordNo}},
        //height: 250,
        colNames:['订单编号','历史订单号','日期','状态','提交人','详细内容'],
        colModel:[
                  
            {name:'ordNo',align:'center',width:100,resizable:false, hidden : true,editable:true},
            {name:'ordHistSeq',align:'center',width:100,resizable:false, hidden : true,editable:true},
            {name:'ordHistRegDttm',align:'center',width:100,resizable:false,editable:true,editoptions:{readonly:'true'}},
            {name:'ordStatCd',align:'center',width:70,resizable:false, formatter : formatterordStatCd, editable: true, edittype:"select",editoptions:{value:{N000550200:'进行',N000560100:'DROP'}} },
            {name:'ordHistWrtrEml',align:'center',width:70,resizable:false, editable:true,editoptions:{readonly:'true'}},
            {name:'ordHistHistCont',align:'left', editable: true, edittype:"text", editoptions:{maxlength:50}}
        ],
    	onSelectRow: function(id){
    		// SMS_MS_ORD 상태가 DROP(N000560100) 일 때에는, 본 화면은 기능하지 않는다.
    		if(ordStatCd != "N000560100"){
    			//본인이 쓴 history 가 아니면 수정할 수 없다.
    			var ordHistWrtrEml =  $('#jqgrid_a').getCell(id, 'ordHistWrtrEml');
    			if(ordHistWrtrEml == "${user.username}"){
    				jQuery('#jqgrid_a').jqGrid('editRow',id,true);
    			}
    		}
		},
        editurl:'${web_ctx}/orderHistorySave.ajax',
        multiselect: true,
        //pager: '#pager_a',
        rowNum:100,
        viewrecords: true,
        
    });

    
    jQuery(".btn-save").click( function() {
		// 상태가 Drop 일 때는 기능 하지 않는다.
		if(ordStatCd != "N000560100"){
			id = jQuery("#jqgrid_a").jqGrid('getGridParam','selarrrow');
			var data = $("#jqgrid_a").jqGrid("getRowData");
			var list = [];
			saveparameters = {
				    "successfunc" : null,
				    "url" : '${web_ctx}/orderHistorySave.ajax',
				    "extraparam" : {},
				    "aftersavefunc" : function( response ) {
// 				                          alert('saved : '+response);
				                      },
				    "errorfunc": function( response ) {
				                    	alert('error : '+response);
				                    },
				    "afterrestorefunc" : null,
				    "restoreAfterError" : true,
				    "mtype" : "POST"
				}
			for(var i=0; i<id.length; i++){
				var dataOrdStatCd = $('#jqgrid_a').getRowData(id[i]).ordStatCd;
				alert("ordstatCd" + ordStatCd);
				alert("dataOrdStatCd" + dataOrdStatCd);
				if(ordStatCd > dataOrdStatCd){
					alert("negative");
					return;
				}
				jQuery("#jqgrid_a").jqGrid('saveRow',id[i],saveparameters);
			}
			jQuery("#jqgrid_a").setGridParam({
				url : "${web_ctx}/orderHistoryLoad.ajax",
				ajaxGridOptions : {async:false},    // 동기로 변환
				datatype : "json",
			}).trigger('reloadGrid');
		}
  });
    
    // jqgrid의 ognzDivCd 컬럼에 대한 formatter
    function formatterordStatCd(cellvalue,options,rowObject){
		if(cellvalue == 'N000550100'){
			return "接受";
		}else if(cellvalue == 'N000550200'){
			return "进行";
		}else if(cellvalue == 'N000550300'){
			return "确定";
		}else if(cellvalue == 'N000550400'){
			return "结算";
		}else if(cellvalue == 'N000560100'){
			return "DROP";
		}else{
			return "";
		}
    }
    
    $('#jqgrid_a').setGridWidth($(".ui-layout-jqgrid").width() - 2);
    $(window).bind('resize', function() {// Grid Resize 처리 
        $('#jqgrid_a').setGridWidth($(".ui-layout-jqgrid").width() - 2);
    }).trigger('resize');
	
	$("#history_add").click(function(){
		// history 는 하나밖에 추가 하지 못한다.
		if(cli_num==0){
		var d = new Date();
		var s =
			    leadingZeros(d.getFullYear(), 4) + '-' +
			    leadingZeros(d.getMonth() + 1, 2) + '-' +
			    leadingZeros(d.getDate(), 2);
			rowdata = {
					ordNo:"${ordNo}",
				ordHistRegDttm:s,
				ordHistWrtrEml:"${user.username}"
			};
			$("#jqgrid_a").jqGrid('addRowData','new_'+cli_num,rowdata,'first');
			$("#jqg_jqgrid_a_new_"+cli_num).attr("checked","checked").click().parent().parent().addClass("ui-state-highlight");
			cli_num++;
		}
    });
	
});

//자바 스크립트 현재 날짜 뽑아 올 때, YYYY-MM-DD 로 나오게 만듬
//because   Date() 에서 YYYY-M-D 로 나올 때가 있음.
function leadingZeros(n, digits) {
	  var zero = '';
	  n = n.toString();

	  if (n.length < digits) {
	    for (i = 0; i < digits - n.length; i++)
	      zero += '0';
	  }
	  return zero + n;
}

</script>

