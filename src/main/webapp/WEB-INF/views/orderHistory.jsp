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
                   <button class="btn-del mr10" id="history_del">DROP</button>
                
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
	
	// SMS_MS_ORD 상태가 DROP(N000550500) 일 때에는, 본 화면은 기능하지 않는다.
	var ordStatCd = "${ordStatCd}";
	if(ordStatCd == "N000550500"){
		$("#history_add").attr('disabled',true).css('background','grey');
		$("#btn-save").attr('disabled',true).css('background','grey');
		$("#history_del").attr('disabled',true).css('background','grey');
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
            {name:'ordStatCd',align:'center',width:70,resizable:true, formatter : "select", editable: false, edittype:"select",editoptions:{value:{N000550100:'接收',N000550200:'进行',N000550300:'确定',N000550400:'结算',N000560100:'DROP'},disabled:'disabled'} },
            {name:'ordHistWrtrEml',align:'center',width:70,resizable:false, editable:true,editoptions:{readonly:'true'}},
            {name:'ordHistHistCont',align:'left', editable: true, edittype:"text", editoptions:{maxlength:50}}
        ],
    	onSelectRow: function(id){
    		// SMS_MS_ORD 상태가 DROP(N000550500) 일 때에는, 본 화면은 기능하지 않는다.
    		if(ordStatCd != "N000550500"){
    			//본인이 쓴 history 가 아니면 수정할 수 없다.
    			var ordHistWrtrEml =  $('#jqgrid_a').getCell(id, 'ordHistWrtrEml');
    			if(ordHistWrtrEml == "${user.username}"){
//     				jQuery("#jqgrid_a").jqGrid('setColProp','ordStatCd',{editable:false});
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
// 		jQuery("#jqgrid_a").jqGrid('setColProp','ordStatCd',{editable:true});

		// 상태가 Drop 일 때는 기능 하지 않는다.
		if(ordStatCd != "N000550500"){
			id = jQuery("#jqgrid_a").jqGrid('getGridParam','selarrrow');
			var data = $("#jqgrid_a").jqGrid("getRowData");
			var list = [];
			saveparameters = {
				    "successfunc" : null,
				    "url" : '${web_ctx}/orderHistorySave.ajax',
				    "extraparam" : {},
				    "aftersavefunc" : function( response ) {
				                          alert('保存成功');   // 저장성공
				                      },
				    "errorfunc": function( response ) {
				                    	alert('保存失败');      // 저장실패
				                    },
				    "afterrestorefunc" : null,
				    "restoreAfterError" : true,
				    "mtype" : "POST"
				}
			var 	Canclesaveparameters = {
				    "successfunc" : null,
				    "url" : '',
				    "extraparam" : {},
				    "aftersavefunc" : function( response ) {
				                          alert('保存成功');   // 저장성공
				                      },
				    "errorfunc": function( response ) {
				                    	alert('保存失败');      // 저장실패
				                    },
				    "afterrestorefunc" : null,
				    "restoreAfterError" : true,
				    "mtype" : "POST"
				}
			for(var i=0; i<id.length; i++){
// 				alert($('#jqgrid_a').jqGrid("getCell", id[i], 'ordStatCd'));
				jQuery("#jqgrid_a").jqGrid('saveRow',id[i],Canclesaveparameters);
				var dataOrdStatCd = $('#jqgrid_a').getCell(id[i], 'ordStatCd');
				if(ordStatCd > dataOrdStatCd){
// 					alert("이전 단계로 돌아갈 수 없습니다.");
					alert("状态更改不能回到前阶段");
					jQuery('#jqgrid_a').jqGrid('editRow',id[i],true);
					return;
				}else if(dataOrdStatCd != ordStatCd){
					if(dataOrdStatCd != 'N000550500' ){
// 						alert("상태변경은 Drop 만 가능합니다.");
						alert("状态无法更改，或者只能选择DROP");
						jQuery('#jqgrid_a').jqGrid('editRow',id[i],true);
						return;
					}else{
						jQuery("#jqgrid_a").jqGrid('saveRow',id[i],saveparameters);
					}
				}else{
					jQuery("#jqgrid_a").jqGrid('saveRow',id[i],saveparameters);
				}
// 				jQuery("#jqgrid_a").jqGrid('saveRow',id[i],saveparameters);
				
			}
			
			// 저장하면, 부모창의 상태도 reload 하고, 현재도 reload(상태 navigator refresh 때문)
			location.reload();
			if(opener!=null && opener.parent!=null){
				opener.parent.location.reload();
			}
		}
  });				
    jQuery("#history_del").click( function() {
    	if(confirm("你确定要废弃订单?")){
    		var d = new Date();
    		var s = 
    			    leadingZeros(d.getFullYear(), 4) + '-' +
    			    leadingZeros(d.getMonth() + 1, 2) + '-' +
    			    leadingZeros(d.getDate(), 2);
			var ordStatCd = "N000550500";
			var ordHistHistCont = "DROP";
        	$.ajax({
    			type : "POST",
    			url : '${web_ctx}/orderHistorySave.ajax',
    			data:	{"ordNo" : "${ordNo}", ordHistRegDttm:s, ordStatCd:ordStatCd,ordHistWrtrEml:"${user.username}",ordHistHistCont:ordHistHistCont},
    			async: false,
    			cache : false,
    			success:function(result){
    		 		alert("成功");
    				location.reload();
    				if(opener!=null && opener.parent!=null){
    					opener.parent.location.reload();
    				}
    			}
    		});//end $.ajax	
    	}
   	
    });
//     
    
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
		}else if(cellvalue == 'N000550500'){
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
				ordStatCd:ordStatCd,
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

