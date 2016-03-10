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
		<span>历史记录</span>
	</h1>
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

            <button class="btn-save">保存</button>
        </section>
    </div>

</article>

<script>
$(function(){
	// 브라우저에 따라 caching 때문에 ajax 최신정보가 보이지 않게됨을 막음.
	jQuery.ajaxSetup({cache:false}); 
	
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
			jQuery('#jqgrid_a').jqGrid('editRow',id,true);
		},
        multiselect: true,
        //pager: '#pager_a',
        rowNum:100,
        viewrecords: true,
        
    });
    
    jQuery(".btn-save").click( function() {
		id = jQuery("#jqgrid_a").jqGrid('getGridParam','selarrrow');
		var data = $("#jqgrid_a").jqGrid("getRowData");
		var list = [];
		saveparameters = {
			    "successfunc" : null,
			    "url" : '${web_ctx}/orderHistorySave.ajax',
			    "extraparam" : {},
			    "aftersavefunc" : function( response ) {
			                          alert('saved : '+response);
			                      },
			    "errorfunc": function( response ) {
			                    	alert('error : '+response);
			                    },
			    "afterrestorefunc" : null,
			    "restoreAfterError" : true,
			    "mtype" : "POST"
			}
		for(var i=0; i<id.length; i++){
			jQuery("#jqgrid_a").jqGrid('saveRow',id[i],saveparameters);
		}
		jQuery("#jqgrid_a").setGridParam({
			url : "${web_ctx}/orderHistoryLoad.ajax",
			ajaxGridOptions : {async:false},    // 동기로 변환
			datatype : "json",
		}).trigger('reloadGrid');
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

