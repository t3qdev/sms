<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="web_ctx" value="${pageContext.request.contextPath}" />
<%-- <link rel="stylesheet" href="${web_ctx}/libs/jquery-ui/jquery-ui.css"> --%>
<%-- <link rel="stylesheet" href="${web_ctx}/libs/jqGrid/css/ui.jqgrid.css"> --%>
<%-- <link rel="stylesheet" href="${web_ctx}/css/default.css"> --%>


<%-- <script type="text/javascript" src="${web_ctx}/libs/multiselect/jquery.multiselect.css"></script> --%>
<%-- <script type="text/javascript" src="${web_ctx}/libs/jquery-ui/external/jquery/jquery.js"></script> --%>
<%-- <script type="text/javascript" src="${web_ctx}/libs/jquery-ui/jquery-ui.js"></script> --%>
<%-- <script type="text/javascript" src="${web_ctx}/libs/jquery-ui/jquery-ui.autocomplete.js"></script> --%>
<%-- <script type="text/javascript" src="${web_ctx}/libs/jqGrid/js/minified/jquery.jqGrid.min.js"></script> --%>
<%-- <script type="text/javascript" src="${web_ctx}/libs/jqGrid/js/minified/i18n/grid.locale-en.js"></script> --%>
<%-- <script type="text/javascript" src="${web_ctx}/libs/multiselect/jquery.multiselect.js"></script> --%>
<%-- <script type="text/javascript" src="${web_ctx}/js/common.js"></script> --%>

<article>


	<h1>
		<span>주문/딜관리</span>
	</h1>
    <div class="ui-layout-single">
    
        <section>
            <!--<h2><span>브랜드</span></h2> -->

            <div class="ui-layout-single">
	                <section class="ui-layout-action tar">
	                	<div class="file_upoad_area">
	                        <span class="bulk_tit"></span>
	                        <button class="btn-add" type="button">신규주문등록</button>
	                    </div>
	                	<button class="btn-excel">양식다운로드</button>
	                    <button class="btn-excel">리스트다운로드</button>
	                    <select class="ui-selector mr10", id="rownum">
	                    <option value="10">페이지당 노출개수</option>
	                    <option value="50">50개</option>
	                    <option value="100">100개</option>
	                    <option value="150">150개</option>
	                    </select>
	                </section>
                
            </div>
        </section>
    </div>
	<div class="ui-layout-single">
    
        <section>
           
            <div class="ui-layout-jqgrid">
                <table id="jqgrid_a"></table>
                <div id="pager_a"></div>
                <div id="paginate"></div>
            </div>
            
            
        </section>
    </div>
  
    <div class="ui-layout-single">
        <section class="ui-layout-action">
        	<div class="paging_area">
                <div class="paginate_complex">
<!--                     <a href="#" class="direction prev"><span></span><span></span> Prev End</a> -->
<!--                     <a href="#" class="direction prev"><span></span> Prev</a> -->
<!--                     <a href="#">1</a> -->
<!--                     <a href="#">2</a> -->
<!--                     <a href="#">3</a> -->
<!--                     <a href="#">4</a> -->
<!--                     <strong>5</strong> -->
<!--                     <a href="#">6</a> -->
<!--                     <a href="#">7</a> -->
<!--                     <a href="#">8</a> -->
<!--                     <a href="#">9</a> -->
<!--                     <a href="#" class="direction next">Next <span></span></a> -->
<!--                     <a href="#" class="direction next">Next End <span></span><span></span></a> -->
                </div>
                <button class="btn-save">저장</button>
            </div>
        </section>
    </div>
        <div id="dialog_upload" title="클라이언트 요청 견적서 Excel 파일 업로드">
		 	<form id="dialog_upload_form" action="${web_ctx}/orderManagementInsert.do" method="post" enctype="multipart/form-data">
				<section class="ui-layout-form-b">
					<ul>
						<li>
							<label for="">엑셀 양식 파일</label>
	                        <input name="file" id="inputExcelFile" type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel">
						</li>
					</ul>
				</section>
			</form>
		</div>
		
		<div id="dialog_upload_special" title="Special Order 클라이언트 요청 견적서 Excel 파일 업로드">
		 	<form id="dialog_upload_form_special" action="${web_ctx}/orderDetailSpecialView.do" method="post" enctype="multipart/form-data">
				<section class="ui-layout-form-b">
					<ul>
					<li>
					<label for="">주문번호</label><input name="ordNo" id="ordNoSpecial" value="" readonly/>
					</li>
						<li>
							<label for="">엑셀 양식 파일</label>
	                        <input name="file" id="inputExcelFileSpecial" type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel">
						</li>
					</ul>
				</section>
			</form>
		</div>			
</article>

<script>

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
$(function(){

	// 브라우저에 따라 caching 때문에 ajax 최신정보가 보이지 않게됨을 막음.
	jQuery.ajaxSetup({cache:false});   
	
	// 네비게이션에 하이라이트 효과
	$('#orderManagement').attr("class", "ui-state-active");	
	
	// [+신규주문등록] 버튼 클릭 했을 때, Dialog Open
	$('.btn-add').click(function(){
		$('#inputExcelFile').val('');
		$('#dialog_upload').dialog('open');
		
	})
	
	// [+신규주문등록] Dialog Control
	$('#dialog_upload').dialog({
		modal: true,
		autoOpen: false,
		width: 500,
		height: 240,
		buttons: {
			'파일 업로드': function(evt){
		 		if($('#inputExcelFile').val()==""){
	 				alert("업로드 할 엑셀파일을 선택하세요");
	 			}else{
	 				if (confirm("선택한 파일을 업로드 하시겠습니까?")) {
		 				$('#dialog_upload_form').submit();
						$('#dialog_upload').dialog('close');
						$('#inputExcelFile').val('');
	 				} 
	 			}
			}
		}
	});
	
	// [Special + 접수] Dialog Control
	$('#dialog_upload_special').dialog({
		modal: true,
		autoOpen: false,
		width: 500,
		height: 240,
		buttons: {
			'파일 업로드': function(evt){
		 		if($('#inputExcelFileSpecial').val()==""){
	 				alert("업로드 할 엑셀파일을 선택하세요");
	 			}else{
	 				if (confirm("선택한 파일을 업로드 하시겠습니까?")) {
// 		 				$('#dialog_upload_form_special').submit();
						$("#dialog_upload_form_special").attr('target', '_blank').submit();
						$('#dialog_upload_special').dialog('close');
						$('#inputExcelFileSpecial').val('');
	 				} else {

	 				}
	 			}
			}
		}
	});

	$('#jqgrid_a').jqGrid({
        url : "${web_ctx}/orderManagementSearch.ajax",
		ajaxGridOptions : {async:false},    // 동기로 변환
		data:{rowInput:$('#rownum option:selected').val(), pageInput:$('#jqgrid_a').PageIndex},
		datatype : "json",
		loadonce: true,            
        width: 1200,
        //height: 250,
        colNames:['Order Number','요청일자','클라이언트','오더상품', '상세보기','딜규모','상해팀담당자','한국팀담당자','오더영입여부','현재상태','현재상태 상세내용','최종상태','공급상 물품대금입금','선금입금일자','선금입금금액','선금입금율','입고 배송일자','입고배송도착지코드','출항배송일자','출항배송도착지코드','도착배송일자','도착배송도착지코드','po배송일자','po배송목적지코드','잔금입금일자','잔금입금금액','잔금입금율','방한핀 구매여부','COUNT','PAGE','ROW'],
        colModel:[
            {name:'ordNo',align:'center',width:100,resizable:false, stype:'text', editable:true, editoptions:{readonly:'true'}},
            {name:'ordReqDt',align:'center',width:100,resizable:false,editable:true, editoptions:{readonly:'true'}, formatter:formatterDate},
            {name:'clientNm',align:'center',width:100,resizable:false ,
            	formatter: 'select',
				 edittype:'select', editoptions:{
					 value:'미확인:미확인;XHS:XHS;WYKL:WYKL',
					 defaultValue:'none',
					 multiple: true
				 },
				 stype:'select', searchoptions: {
					 sopt: ['eq','ne'],
					 value:'미확인:미확인;XHS:XHS;WYKL:WYKL',
					 //dataInit: dataInitMultiselect
				 }
			},
            {name:'orderedGudsNm',align:'left',width:250,resizable:false, stype:'input'},
            {name:'showDetail',align:'left',width:130,resizable:false, formatter : formatterShowDetail, stype:'input'},
            {name:'ordSumAmt',align:'right',width:100,resizable:false, stype:'input', editable:true, formatter:'currency'},		
            {name:'cnsMng',align:'center',width:100,resizable:false 
//             ,	formatter: 'select',
// 				 edittype:'select', editoptions:{
// 					 value:'某某某(ZSZ):某某某(ZSZ);商鞅(SY):商鞅(SY);独孤方(DGF):独孤方(DGF)',
// 					 defaultValue:'none',
// 					 multiple: true
// 				 },
// 				 stype:'select', searchoptions: {
// 					 sopt: ['eq','ne'],
// 					 value:'某某某(ZSZ):某某某(ZSZ);商鞅(SY):商鞅(SY);独孤方(DGF):独孤方(DGF)',
// 					 //dataInit: dataInitMultiselect
// 				 }
				 },		
            {name:'korMng',align:'center',width:100,frozen : true 
// 					 , formatter: 'select',
// 					 edittype:'select', editoptions:{
// 						 value:'太公望(TGW):太公望(TGW);李小龙(LXL):李小龙(LXL)',
// 						 defaultValue:'none',
// 						 multiple: true
// 					 },
// 					 stype:'select', searchoptions: {
// 						 sopt: ['eq','ne'],
// 						 value:'太公望(TGW):太公望(TGW);李小龙(LXL):李小龙(LXL)',
// 						 //dataInit: dataInitMultiselect
// 					 }
					 },
			{name:'ordTypeCd',align:'center',width:100, formatter: 'select',
				 edittype:'select', editoptions:{
					 value:'N000620100:B5C(일반);N000620200:B5C(스페셜);N000620300:오프라인',
					 defaultValue:'none',
					 multiple: true
				 },
				 stype:'select', searchoptions: {
					 
					 value:'N000620100:B5C(일반);N000620200:B5C(스페셜);N000620300:오프라인'
					,	 sopt: ['eq','ne']
					 //dataInit: dataInitMultiselect
				 }},
            {name:'ordStatCd',align:'center',width:100,resizable:false,formatter: 'select',
				 edittype:'select', editoptions:{
					 value:'N000550100:접수;N000550200:진행;N000550300:확정;N000550400:정산;N000560100:DROP',
					 defaultValue:'none',
					 multiple: true
				 },
				 stype:'select', searchoptions: {
					 value:'N000550100:접수;N000550200:진행;N000550300:확정;N000550400:정산;N000560100:DROP',
					 sopt: ['eq','ne']
					 //dataInit: dataInitMultiselect
				 }},	
            {name:'histDetail',align:'left',width:300,resizable:false, formatter : formatterShowHistory, stype:'input'},		
            {name:'ordStatCd',align:'center',width:70,resizable:false,formatter: 'select',
				 edittype:'select', editoptions:{
					 value:'N000550300:확정;N000550400:정산;N000560100:DROP',
					 defaultValue:'none',
					 multiple: true
				 }, stype:'input'
            },		
            {name:'bactPrvdDtPlusbactPrvdAmt',align:'center',resizable:false, stype:'input'},		
            {name:'paptDpstDt',align:'center',width:80,resizable:false, stype:'input',editable:true, formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }
                  }},		
            {name:'paptDpstAmt',align:'center',width:80,resizable:false, stype:'input',editable:true},		
            {name:'paptDpstRate',align:'center',width:80,resizable:false, stype:'input',editable:true,editrules:{number:true}},		
            {name:'wrhsDlvDt',align:'center',width:80,resizable:false, stype:'input',editable:true,formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }
                 }	},		
            {name:'wrhsDlvDestCd',align:'center',width:80,resizable:false, stype:'input',editable:true},
            {name:'dptrDlvDt',align:'center',width:80,resizable:false, stype:'input',editable:true,formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }

                  }	},
            {name:'dptrDlvDestCd',align:'center',width:80,resizable:false, stype:'input',editable:true},
            {name:'arvlDlvDt',align:'center',width:80,resizable:false, stype:'input',editable:true,formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }

                  }	},
            {name:'arvlDlvDestCd',align:'center',width:80,resizable:false, stype:'input',editable:true},
            {name:'poDlvDt',align:'center',width:80,resizable:false, stype:'input',editable:true,formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }

                  }	},
            {name:'poDlvDestCd',align:'center',width:80,resizable:false, stype:'input',editable:true},
            {name:'raptDpstDt',align:'center',width:80,resizable:false, stype:'input',editable:true,formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }

					}},
            {name:'raptDpstAmt',align:'center',width:80,resizable:false, stype:'input',editable:true,editrules:{number:true}},
            {name:'raptDpstRate',align:'center',width:80,resizable:false, stype:'input',editable:true,editrules:{number:true}},
            {name:'b5mBuyCont',align:'center',width:160,resizable:false, stype:'input',editable:true},
            {name:'count',align:'center',width:160,resizable:false,hidden:"true"},
            {name:'page',align:'center',width:160,resizable:false,hidden:"true"},
            {name:'row',align:'center',width:160,resizable:false,hidden:"true"}
        ],
    	onSelectRow: function(id){
			jQuery('#jqgrid_a').jqGrid('editRow',id,true);
		},
		pagination:true,
        multiselect: true,
        gridComplete : function(e){

//         	jQuery("#jqgrid_a").jqGrid('setColProp','ordNo',{frozen:true});
//         	jQuery("#jqgrid_a").jqGrid('setColProp','ordReqDt',{frozen:true});
//         	jQuery("#jqgrid_a").jqGrid('setColProp','clientNm',{frozen:true});
//         	jQuery("#jqgrid_a").jqGrid('setColProp','orderedGudsNm',{frozen:true});
//         	jQuery("#jqgrid_a").jqGrid('setColProp','showDetail',{frozen:true});
//         	jQuery("#jqgrid_a").jqGrid('setColProp','ordSumAmt',{frozen:true});
//         	jQuery("#jqgrid_a").jqGrid('setColProp','cnsMng',{frozen:true});
//         	jQuery("#jqgrid_a").jqGrid('setColProp','korMng',{frozen:true});
//         	jQuery("#jqgrid_a").jqGrid('setColProp','ordTypeCd',{frozen:true});
        	
//         	jQuery("#jqgrid_a").jqGrid('setFrozenColumns');
//         	jQuery("#jqgrid_a").jqGrid("setGridParam",{cellEdit : true});

        	var totalDbCount = $('#jqgrid_a').getRowData(1).count;
			var page = $('#jqgrid_a').getRowData(1).page;
			var row = $('#jqgrid_a').getRowData(1).row;
			customPager(totalDbCount,page, row);
        },

        viewrecords: true,
		shrinkToFit: false
        
  	  });
    
	// jqgrid가 로딩완료 되면, 이 pager로 jqgrid 아래에 pager를 지우고 새로 그린다.
	// 이 페이저에는 goToSelectedPage() 함수가 링크 걸려 있다.
	function customPager(totalDbCount, page, row){
		var pageCount = Math.ceil(totalDbCount / row);
		$('.paginate_complex').html('');
		var pagerHtml ='';
		var prevPage = page-1;
		if(prevPage>0){
			 pagerHtml += '<a href="#" class="direction prev" onclick="goToSelectedPage('+totalDbCount+','+1+','+row+')"><span></span><span></span> Prev End</a>';
			pagerHtml += '<a href="#" class="direction prev" onclick="goToSelectedPage('+totalDbCount+','+prevPage+','+row+')">Prev <span></span></a>';
		}
		if(pageCount >= 10){
			for(var i=pageCount-4; i<=pageCount+4; i++){
				if(i==page){
					pagerHtml += '<strong>'+i+'</strong>'
				}else{
					pagerHtml += '<a href="#" onclick="goToSelectedPage('+totalDbCount+','+i+','+row+')">'+i+'</a>';
				}
			}
		}else{
			for(var i=1; i<=pageCount; i++){
				if(i==page){
					pagerHtml += '<strong>'+i+'</strong>'
				}else{
					pagerHtml += '<a href="#" onclick="goToSelectedPage('+totalDbCount+','+i+','+row+')">'+i+'</a>';
				}
			}
		}
		var nextPage = 1 + parseInt(page);
		if(page!=pageCount) {
			pagerHtml += '<a href="#" class="direction next" onclick="goToSelectedPage('+totalDbCount+','+nextPage+','+row+')">Next <span></span></a>';
			pagerHtml += '<a href="#" class="direction next" onclick="goToSelectedPage('+totalDbCount+','+pageCount+','+row+')">Next End <span></span><span></span></a>';
		}
		$('.paginate_complex').html(pagerHtml);

	};
	
	
	// [저장] 버튼 클릭했을 때
    jQuery(".btn-save").click( function() {
		id = jQuery("#jqgrid_a").jqGrid('getGridParam','selarrrow');
		var data = $("#jqgrid_a").jqGrid("getRowData");
		var list = [];
		saveparameters = {
			    "successfunc" : function(){},
			    "url" : '${web_ctx}/orderManagementSave.ajax',
			    "extraparam" : {},
			    "aftersavefunc" : function( response ) {
			                          alert('saved : '+response);
			                      },
			    "errorfunc": function( response ) {
// 			                    	alert('error : '+response);
			                    },
			    "afterrestorefunc" : null,
			    "restoreAfterError" : true,
			    "mtype" : "POST"
			}
		for(var i=0; i<id.length; i++){
			jQuery("#jqgrid_a").jqGrid('saveRow',id[i],saveparameters);
		}
		var page = $('#jqgrid_a').getRowData(1).page;
		var row = $('#jqgrid_a').getRowData(1).row;
		
		jQuery("#jqgrid_a").setGridParam({
			url : "${web_ctx}/orderManagementSearch.ajax",
			ajaxGridOptions : {async:false},    // 동기로 변환
			postData:{"rowInput":row, "pageInput":page},
			rowNum : $('#rownum option:selected').val(),
			datatype : "json",
		}).trigger('reloadGrid');
    });
	
	//rowNum셀렉트박스 상태 변화시
	$('#rownum').selectmenu({
		change:function(event, ui) {
			jQuery("#jqgrid_a").setGridParam({
				url : "${web_ctx}/orderManagementSearch.ajax",
				ajaxGridOptions : {async:false},    // 동기로 변환
				postData:{"rowInput":$('#rownum option:selected').val(), "pageInput":"1"},
				rowNum : $('#rownum option:selected').val(),
				datatype : "json",
			}).trigger('reloadGrid');
		}
	});	

	//[상세보기] 클릭 하면, 상세보기 새 창으로 링크
	function formatterShowDetail(cellvalue,options,rowObject){
		if(rowObject.ordTypeCd=="N000620200" && rowObject.ordStatCd=="N000550100"){
			return '<a href="#" onclick="dialogSpecialExcel('+rowObject.ordNo+')">'+cellvalue+'</a>';
		}else{
			var address = "${web_ctx}/orderDetailView.do?ordNo="+rowObject.ordNo;
// 			return '<a data-href="'+address+'" class="btn_pop" data-popw="1300" data-poph="800">'+cellvalue+'</a>';      -> jqgrid 리로드시에는 작동 불능이기 때문에 대체
			return '<a href="#" onclick="popUp('+rowObject.ordNo+')">'+cellvalue+'</a>';
		}
	}

	//[현재상태 상세내용] 클릭 하면, 상세보기 새 창으로 링크
	function formatterShowHistory(cellvalue,options,rowObject){
		var address = "${web_ctx}/orderHistoryView.do?ordNo="+rowObject.ordNo;
		return '<a data-href="'+address+'" class="btn_pop ico_history" data-popw="1200" data-poph="400"><span class="ui-icon ui-icon-calendar"></span>'+cellvalue+'</a>';
	}
 
	//Date 형식 formatter  (db 에는 varchar(8) 로 되어 있어서, formatter로 형식변환)
	function formatterDate(cellvalue,options,rowObject){
		if(cellvalue!=null && cellvalue.length==8){
			var yyyy = cellvalue.substr(0,4);
			var mm = cellvalue.substr(4,2);
			var dd = cellvalue.substr(6,2);
			return yyyy+'-'+mm+'-'+dd;
		}else{
			return "";
		}

	}  
 
 	$("#jqgrid_a").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
			{startColumnName: 'paptDpstDt', numberOfColumns: 3, titleText: '선금'},
// 			{startColumnName: 'wrhsDlvDt', numberOfColumns: 8, titleText: 'Shipping'},
			{startColumnName: 'wrhsDlvDt', numberOfColumns: 2, titleText: '입고일'},
			{startColumnName: 'dptrDlvDt', numberOfColumns: 2, titleText: '출항일'},
			{startColumnName: 'arvlDlvDt', numberOfColumns: 2, titleText: '도착일'},
			{startColumnName: 'poDlvDt', numberOfColumns: 2, titleText: 'P/O도착일'},
			{startColumnName: 'raptDpstDt', numberOfColumns: 3, titleText: '잔금'}

		]	
	});
// 	$("#jqgrid_a").jqGrid('filterToolbar');
	$("#jqgrid_a").filterToolbar({
	
	   stringResult: true,
	
	   groupOp: 'AND',
	
	   searchOnEnter: true,
	
	   beforeSearch: function() { //  검색전 액션이다.
	
		   alert("ddd");
	      $("#jqgrid_a").setGridParam({url:"{web_ctx}/orderManagementSearch.ajax"}); // 데이터 xml 주소를 지정할 수 있다.
	      
	   }
	
	}); 

	$("#jqgrid_a").jqGrid('setFrozenColumns');
    $('#jqgrid_a').setGridWidth($(".ui-layout-jqgrid").width() - 2);
    $(window).bind('resize', function() {// Grid Resize 처리 
        $('#jqgrid_a').setGridWidth($(".ui-layout-jqgrid").width() - 2);
    }).trigger('resize');
	
	$(".btn_pop").each(function(){
       $(this).click(function(){
			window.open($(this).data("href"), "상세보기", "scrollbars=yes,width=" + $(this).data("popw") + ",height=" + $(this).data("poph") + ",top=10,left=20");
		}); 
    });
	
	$('.frozen-div td[colindex="2"]+td>input').removeAttr("id").addClass("ui-calendar tac");
	$('.ui-calendar').datepicker();
	$('.ui-calendar').datepicker('option', 'dateFormat', 'yy-mm-dd' );
	$('select.ui-widget-content.ui-corner-all').multiselect();
	
	
});      // End : $(function(){}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//jqgrid 내에서, [상세보기] 클릭했을 때, row에서 스페셜오더, 접수 상태일 때는 아래의 다이얼로그가 활성화 된다.
function dialogSpecialExcel(ordNo){
	//dialog 에 해당 주문 번호 넘겨 주고,
	$('#inputExcelFileSpecial').val('');
	$('#ordNoSpecial').val(ordNo);
	
	//dialog 활성화.
	$('#dialog_upload_special').dialog('open');
};

// [상세보기] 클릭 했을 때, 새 창
function popUp(ordNo){
	var address = "${web_ctx}/orderDetailView.do?ordNo="+ordNo;
	window.open( address, "idcheck", "top=200, left=200, toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=1300, height=800" );
}


// pager 링크 함수.    jqgrid 아래에 pager 가 클릭되면, 이 함수가 실행.
// 선택한 page 로 DB 검색 후 jqgrid reload
function goToSelectedPage(totalDbCount, page, row){
	$('#paginate_complex')
		jQuery("#jqgrid_a").setGridParam({
		url : "${web_ctx}/orderManagementSearch.ajax",
		ajaxGridOptions : {async:false},    // 동기로 변환
		postData:{"rowInput":row, "pageInput":page},
		datatype : "json",
	}).trigger('reloadGrid');
	
	
};

</script>