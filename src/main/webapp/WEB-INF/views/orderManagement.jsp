
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="web_ctx" value="${pageContext.request.contextPath}" />
<sec:authentication var="user" property="principal" />
<STYLE>
        .bold {
            font-weight: bold !important;
            text-align: left !important;
		}
        .boldAndBlue a {
       		color:#3498d8 !important;
            font-weight: bold !important; 
	    }
	    article{opacity:0}
</STYLE>
<article>
	<h1>
		<span>订单/交易管理</span>
	</h1>
    <div class="ui-layout-single">
        <section>
        
            <div class="ui-layout-single">
	                <section class="ui-layout-action tar">
                	<div class="s_area">
                    	<input type="text" id="searchKeyWord" size="30" style="height:30px; line-height:30px; box-sizing:border-box" />
                        <button class="btn-search">搜索</button>
                    </div>
	                	<div class="file_upoad_area">
	                        <span class="bulk_tit"></span>
	                        <button class="btn-add" type="button">订单上传</button>
	                    </div>
	                	<button class="btn-excel" id="templateExcelDownload">模版下载</button>
	                    <button class="btn-excel" id="jqGridExcelDownload">列表下载</button>
	                    <select class="ui-selector mr10", id="rownum">
	                    <option value="20">每页显示数量</option>
	                    <option value="20">20个</option>
	                    <option value="50">50个</option>
	                    <option value="100">100个</option>
	                    <option value="150">150个</option>
	                    </select>
	                </section>
            </div>
        </section>
    </div>
	<div class="ui-layout-single">
        <section>
        <h3><span id="countDB">搜索结果: 共1件</span></h3>
            <div class="ui-layout-jqgrid">
                <table id="jqgrid_a"></table>
                <div id="pager_a"></div>
                <div id="paginate"></div>
                <input type="text" id="totalDbCount" hidden>
                <input type="text" id="page" hidden>
                <input type="text" id="row" hidden>
<!--                 <input type="text" id="pagecontent" size="500"> -->
            </div>
        </section>
    </div>
    <div class="ui-layout-single">
        <section class="ui-layout-action">
        	<div class="paging_area">
                <div class="paginate_complex">
                </div>
                <button class="btn-save">保存</button>
            </div>
        </section>
    </div>
        <div id="dialog_upload" title="上传客户询盘单">
		 	<form id="dialog_upload_form" action="${web_ctx}/orderManagementInsert.ajax" method="post" enctype="multipart/form-data">
				<section class="ui-layout-form-b">
					<ul>
						<li>
							<label for="">excel样式文件</label>
	                        <input name="file" id="inputExcelFile" type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel">
						</li>
					</ul>
				</section>
			</form>
		</div>
		<!-- 160405 엑셀 upload 실패시 보여줄 dialog  -->
		<div id="dialog_upload_result" title="excel上传失败">
			<section class="ui-layout-form-b">
				<ul>
					<li>
						<label for="">excel上传失败</label>
					</li>
				</ul>
			</section>
		</div>
		
		<div id="dialog_upload_special" title="上传特殊订单客户询价单">
		 	<form id="dialog_upload_form_special" action="${web_ctx}/orderDetailSpecialView.do" method="post" enctype="multipart/form-data">
				<section class="ui-layout-form-b">
					<ul class="so_layer">
					<li>
					<label for="">订单编号</label><input name="ordNo" id="ordNoSpecial" value="" readonly/>
					</li>
                    <li>
						<div class="img_area">
<!-- 							<img id="special_img" src="http://placehold.it/80x80"> -->
						</div>
                        <div class="text_area">
                        	<textarea id="special_req_cont"></textarea>
                        </div>
					</li>
                    <li>
                        <label for="">excel样式文件</label>
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

	//0.권한 관리를 위한 유저권한 체크
   var roles = new Array();
   <c:forEach var="role" items="${user.authorities }">
      roles.push("${role.name}");
   </c:forEach>
   
   //버튼별 권한테이블
   var RolesExcelUp = new Array("N000580100","N000580200","N000580300");      		//1.주문 생성
   var RolesEditordSumAmt = new Array("N000580100","N000580200","N000580300");  //2.주문/딜내역 수정
   var RolesEditPapt = new Array("N000580100","N000580200","N000580300");      		//3.대금입금(선금)수정
   var RolesEditRapt = new Array("N000580100","N000580200","N000580300");      		//4.대금입금(잔금)수정
   var RolesEditShipping = new Array("N000580100","N000580500");      					//5.배송정보 수정
   var RolesSaveBtn = new Array("N000580100","N000580200","N000580300", "N000580500");            //6.화면에 Save 버튼

   //권한에 따른 버튼 보여주기
   if(!checkIndex(RolesExcelUp,roles)){
      $('.btn-add').hide();
   }
   if(!checkIndex(RolesExcelUp,roles)){
      $('.btn-save').hide();
   }
   
	// 브라우저에 따라 caching 때문에 ajax 최신정보가 보이지 않게됨을 막음.
	jQuery.ajaxSetup({cache:false});   
	
	// 네비게이션에 하이라이트 효과
	$('#orderManagement').attr("class", "ui-state-active");	
	
	// [+신규주문등록] 버튼 클릭 했을 때, Dialog Open
	$('.btn-add').click(function(){
			$('#inputExcelFile').val('');
			$('#dialog_upload').dialog('open');
	});
	
	jQuery("#jqGridExcelDownload").click( function() {
		// 우선, 모든 rows 들을 Editable 로 만들어야 화면에 보이는 그대로의 값을 얻을 수 있다.
		// 또한 이들을 그대로 뽑아오기 위해서는 jqgrid.saverow 함수를 이용해야 하는데,
		// saverow 함수는 1 개의 row 단위로 작동하기 때문에 이를 변형해서 사용할 수 있어야 한다.
		var ids =jQuery("#jqgrid_a").jqGrid('getDataIDs');
		var dataList = [];
		for(var i = 0; i <  ids.length; i++ ){
			var cdObject = {
					"ordNo" : $('#jqgrid_a').jqGrid("getCell", ids[i], 'ordNo')
			};
			dataList.push(cdObject);
		}

		$.ajaxSettings.traditional = true;

		var form = "<form action='${web_ctx}/orderManagementExcelDownload.do?dataList="+ JSON.stringify(dataList)+"' method='post'>"; 
		form += "</form>"; 
		jQuery(form).appendTo("body").submit().remove(); 

	});
	
	// 기본 템플릿 엑셀 다운로드
	jQuery("#templateExcelDownload").click( function() {
		var form = "<form action='${web_ctx}/orderManagementExcelTemplateDownload.do' method='post'>"; 
		form += "</form>"; 
		jQuery(form).appendTo("body").submit().remove(); 
	});

	var DlvDestCd = ':;N000510100:ICN;N000510200:PUS;N000510300:PTK;N000510400:PVG;N000510500:NGB;N000510600:CGO;N000510700:CKG;N000510800:CAN;N000510900:HGH;'
							+'N000511000:TSN;N00051100:NKG;N000511200:SZX;N000511300:TAO;N000511400:HKG';
	var StdXchrCd = ':;N000590100:$;N000590200:₩;N000590300:¥';
	
	$('#jqgrid_a').jqGrid({
        url : "${web_ctx}/orderManagementSearch.ajax",
		ajaxGridOptions : {async:false},    // 동기로 변환
		data:{rowInput:$('#rownum option:selected').val(), pageInput:$('#jqgrid_a').PageIndex, searchKeyword:$('#searchKeyWord').val()},
		datatype : "json",
		loadonce: true,            
        width: 1200,
        //height: 250,
        colNames:['Order Number','申请日期','客户名称','订购商品', '查看详情','报价货币','订单/交易金额','上海负责人','韩国负责人','订购路径','状态','状态详情','最终状态','商品供应商汇款','首付日期','首付金额','首付百分比','入库日期','入库地点','出港日期','出港地点','到岸日期','到岸地点','P/O日期','P/O地点','余款结算日期','余付','余款百分比','是否在帮韩品购买','上传日期','上传内容'
                  			,'COUNT','PAGE','ROW','bactPrvdMemoCont','stdXchrAmt','krwXchrAmt','usdXchrAmt','cnsXchrAmt'],
        colModel:[
            {name:'ordNo',index:'ordNo',align:'center',width:100,resizable:false, editable:true, editoptions:{readonly:'true'},stype:'text', search:true},
            {name:'ordReqDt',index:'ordReqDt',align:'center',width:100,resizable:false,editable:true, editoptions:{readonly:'true'}, formatter:formatterDate,stype:'text', search:true},
            {name:'clientNm',index:'clientNm',align:'center',width:100,resizable:false,stype:'text', search:true},
            {name:'orderedGudsNm',index:'orderedGudsNm',align:'left',width:250,resizable:false, classes: 'bold', formatter:stringLengthLimit, search:false},
            {name:'showDetail',index:'showDetail',align:'left',width:200,resizable:false, formatter : formatterShowDetail, stype:'input' , classes: 'boldAndBlue', search:false},
//             {name:'stdXchrKindCd',index:'stdXchrKindCd',align:'center',width:70,resizable:false,formatter:formatterCurrentIcon , search:false},
            {name:'stdXchrKindCd',index:'stdXchrKindCd',align:'center',width:70,resizable:false, search:false,editable:true,edittype:"select",editoptions:{value:StdXchrCd}, formatter : formatterCurrentIcon },
            {name:'ordSumAmt',index:'ordSumAmt',align:'right',width:130,resizable:false, stype:'input', editable:true, formatter:"currency", formatoptions:{defaultValue:'',decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2,prefix: ""} , classes: 'bold' , search:false},		
//             {name:'ordSumAmt',index:'ordSumAmt',align:'right',width:130,resizable:false, stype:'input', editable:true, formatter:"currency", formatoptions:{defaultValue:'',decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2,
//             		prefix: "
//             	} , classes: 'bold' , search:false},
//             {name:'ordSumAmt',index:'ordSumAmt',align:'right',width:130,resizable:false, stype:'input', editable:true, formatter:formatterCurrencyForOrdSumAmt, unformat:unformatterCurrencyForOrdSumAmt , classes: 'bold' , search:false},		
            {name:'cnsMng',index:'cnsMng',align:'center',width:100,resizable:false ,stype:'text', search:true},		
            {name:'korMng',index:'korMng',align:'center',width:100,frozen : true,resizable:false   ,stype:'text', search:true},
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    		////// 필터
			{name:'ordTypeCd',index:'ordTypeCd',align:'center',width:100, formatter: 'select', 
				 edittype:'select', 
				 editoptions:{ 
								value:'N000620100:B5C(一般);N000620200:B5C(特殊);N000620300:线下订单'
 								,defaultValue:'none'
	 								, multiple: true
								},
// 				 search : 'true',
				 stype:'select', 
				 searchoptions: {
					 multiple: true,
					 dataInit : function (elem) {
							var options = {
                             height: "200",
								minWidth : 'auto',
								position: {
									my: 'left bottom',
									at: 'left top'
								},
                             open: function () {
                                 var $menu = $(".ui-multiselect-menu:visible");
                                 $menu.css("font-size","11px").width("auto");
                                 $menu.css("z-index","100");
                                 return;
                             }
							},
								$elem = $(elem);
								$elem.multiselect(options);
								$elem.siblings('button.ui-multiselect').css({
									width: "100%",
									marginTop: "1px",
									marginBottom: "1px",
									paddingTop: "3px"
								});
							},
					 value:'N000620100:B5C(一般);N000620200:B5C(特殊);N000620300:线下订单'
							}
				 },
            {name:'ordStatCd',index:'ordStatCd',align:'center',width:100,resizable:false,formatter: 'select',
				 edittype:'select', editoptions:{ 
	 								value:'N000550100:接受;N000550200:进行;N000550300:确定;N000550400:结算;N000560100:DROP'
	 								,defaultValue:'none'
	 								, multiple: true
	 								},
// 				 search : 'true',
				 stype:'select', 
				 searchoptions: { 
					 multiple: true,
					 dataInit : function (elem) {
							var options = {
                         	 height: "200",
								minWidth : 'auto',
								position: {
									my: 'left bottom',
									at: 'left top'
								},
                          open: function () {
                              var $menu = $(".ui-multiselect-menu:visible");
                              $menu.css("font-size","11px").width("auto");
                              $menu.css("z-index","100");
                              return;
                          }
							},
								$elem = $(elem);
								$elem.multiselect(options);
								$elem.siblings('button.ui-multiselect').css({
									width: "100%",
									marginTop: "1px",
									marginBottom: "1px",
									paddingTop: "3px"
								});
							},								
						value:'N000550100:接受;N000550200:进行;N000550300:确定;N000550400:结算;N000560100:DROP' 
				}
			},	
		    //////end: 필터2
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            {name:'histDetail',index:'histDetail',align:'left',width:300,resizable:false, formatter : formatterShowHistory, search:false},		
            {name:'ordStatCd',index:'ordStatCd',align:'center',width:70,resizable:false,formatter: 'select',
				 edittype:'select', editoptions:{ 
					 							value:'N000550300:确定;N000550400:结算;N000560100:DROP'
					 							, defaultValue:'none'
					 							, multiple: true
					 							}
          	  , search:false
			},
             {name:'bactPrvdDtPlusbactPrvdAmt',index:'bactPrvdDtPlusbactPrvdAmt',align:'center',resizable:false,  search:false, width:200},		
            {name:'paptDpstDt',index:'paptDpstDt',align:'center',width:90,resizable:false, search:false,editable:true, formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }
                  }},		
/*             {name:'paptDpstAmt',index:'paptDpstAmt',align:'center',width:90,resizable:false, search:false,editable:true,formatter:"currency", formatoptions:{defaultValue:'',decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}   }, */		
            {name:'paptDpstAmt',index:'paptDpstAmt',align:'center',width:90,resizable:false, search:false,editable:true,editrules:{number:true}, formatter:formatterCurrentAmt ,unformat:unformatterCurrencyForOrdSumAmt  },		
            {name:'paptDpstRate',index:'paptDpstRate',align:'center',width:90,resizable:false, search:false,editable:true,editrules:{number:true}, formatter:"currency", formatoptions:{defaultValue:'',decimalSeparator:".",  decimalPlaces: 2, suffix: " %"} },		
            {name:'wrhsDlvDt',index:'wrhsDlvDt',align:'center',width:90,resizable:false, search:false,editable:true,formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }
                 }	},		
            {name:'wrhsDlvDestCd',index:'wrhsDlvDestCd',align:'center',width:100,resizable:false, search:false,editable:true,edittype:"select",editoptions:{value:DlvDestCd}, formatter : formatterDlvDestCd },
            {name:'dptrDlvDt',index:'dptrDlvDt',align:'center',width:90,resizable:false, search:false,editable:true,formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }

                  }	},
            {name:'dptrDlvDestCd',index:'dptrDlvDestCd',align:'center',width:100,resizable:false, search:false,editable:true ,edittype:"select",editoptions:{value:DlvDestCd}, formatter : formatterDlvDestCd},
            {name:'arvlDlvDt',index:'arvlDlvDt',align:'center',width:90,resizable:false, search:false,editable:true,formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }

                  }	},
            {name:'arvlDlvDestCd',index:'arvlDlvDestCd',align:'center',width:100,resizable:false, search:false,editable:true ,edittype:"select",editoptions:{value:DlvDestCd}, formatter : formatterDlvDestCd},
            {name:'poDlvDt',index:'poDlvDt',align:'center',width:90,resizable:false, search:false,editable:true,formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }

                  }	},
            {name:'poDlvDestCd',index:'poDlvDestCd',align:'center',width:100,resizable:false, search:false,editable:true ,edittype:"select",editoptions:{value:DlvDestCd}, formatter : formatterDlvDestCd},
            {name:'raptDpstDt',index:'raptDpstDt',align:'center',width:90,resizable:false, search:false,editable:true,formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }

					}},
            {name:'raptDpstAmt',index:'raptDpstAmt',align:'center',width:90,resizable:false, search:false,editable:true,editrules:{number:true},formatter:formatterCurrentAmt ,unformat:unformatterCurrencyForOrdSumAmt  },	
            {name:'raptDpstRate',index:'raptDpstRate',align:'center',width:90,resizable:false, search:false,editable:true,editrules:{number:true} , formatter:"currency", formatoptions:{defaultValue:'',decimalSeparator:".",  decimalPlaces: 2, suffix: " %"}},
            {name:'b5mBuyCont',index:'b5mBuyCont',align:'center',width:160,resizable:false, search:false,editable:true},
           
            {name:'b5cGudsRegDt',index:'b5cGudsRegDt',align:'center',width:90,resizable:false, search:false,editable:true, formatter:formatterDate,
            	editoptions:{readonly:'true',size:20, dataInit:function(el){$(el).datepicker({dateFormat:'yy-mm-dd'}); }
            }},
            {name:'b5cGudsRegMemo',index:'b5cGudsRegMemo',align:'center',width:160,resizable:false, search:false,editable:true, classes: 'ttest'},
            
            {name:'count',index:'count',align:'center',width:160,resizable:false,hidden:"true"},
            {name:'page',index:'page',align:'center',width:160,resizable:false,hidden:"true"},
            {name:'row',index:'row',align:'center',width:160,resizable:false,hidden:"true"},
            {name:'bactPrvdMemoCont',index:'bactPrvdMemoCont',align:'center',width:160,resizable:false,hidden:"true"},
            {name:'stdXchrAmt',index:'stdXchrAmt',align:'center',width:160,resizable:false,hidden:"true"},

            {name:'krwXchrAmt',index:'krwXchrAmt',align:'center',width:160,resizable:false,hidden:"true"},
            {name:'usdXchrAmt',index:'usdXchrAmt',align:'center',width:160,resizable:false,hidden:"true"},
            {name:'cnsXchrAmt',index:'cnsXchrAmt',align:'center',width:160,resizable:false,hidden:"true"}
        ],

//     	onSelectRow: function(id){
       onCellSelect: function(id, cid){

    	   var selectedRows = $("#jqgrid_a").jqGrid('getGridParam', 'selarrrow');
    	   for(i=0; i < selectedRows.length; i++){
    		   if(selectedRows[i] == id){
    			   $("#jqgrid_a").jqGrid('restoreRow',id, false);
    			   return false;
    		   }
    	   }


    	   if(!checkIndex(RolesEditordSumAmt,roles)){
   			jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:false});
   		   }else{
   			jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:true});
   		   }
    		var ordStatCd =  $('#jqgrid_a').getCell(id, 'ordStatCd');
			if(ordStatCd=="N000550300" || ordStatCd=="N000550400" ){
				//PO확정 이후, 선금, 잔금, 입고일, 출항일, 도착일, P/O도착일 등 수정가능
				
				if(!checkIndex(RolesEditPapt,roles)){
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstAmt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstRate',{editable:false});	
				}else{
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstAmt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstRate',{editable:true});	
				}
				
				if(!checkIndex(RolesEditShipping,roles)){
					jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDestCd',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDestCd',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDestCd',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDestCd',{editable:false});
				}else{
					jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDestCd',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDestCd',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDestCd',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDestCd',{editable:true});
				}
				if(!checkIndex(RolesEditRapt,roles)){
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstAmt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstRate',{editable:false});
				}else{
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstAmt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstRate',{editable:true});
				}
				var paptDpstRate = $('#jqgrid_a').getCell(id, 'paptDpstRate');
				var raptDpstRate = $('#jqgrid_a').getCell(id, 'raptDpstRate');
				
// 				if(paptDpstRate == "100.00"){
// 					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstDt',{editable:false});
// 					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstAmt',{editable:false});
// 					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstRate',{editable:false});
// 				}else if(raptDpstRate == "100.00"){
// 					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:false});
// 					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstAmt',{editable:false});
// 					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstRate',{editable:false});
// 				}else{
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstAmt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstRate',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstAmt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstRate',{editable:true});
// 				}
				if(checkIndex(RolesSaveBtn,roles)){
					jQuery('#jqgrid_a').jqGrid('editRow',id,false);

				}
			}else{
				jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstAmt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstRate',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDestCd',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDestCd',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDestCd',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDestCd',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstAmt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstRate',{editable:false});

				jQuery('#jqgrid_a').jqGrid('editRow',id,false);

			}
	     	 var selectedCellForFocus = $("#jqgrid_a").jqGrid("getGridParam", "colModel")[cid].name;
			setTimeout(function() {
				$("#"+id+"_"+selectedCellForFocus).focus();
			}, 1); // 3000ms(3초)가 경과하면 이 함수가 실행됩니다.

       },

		
		pagination:true,
        multiselect: true,
        editurl:'${web_ctx}/orderManagementSave.ajax',
        excel:true,
        rowNum:20,

        gridComplete : function(e){
  
        	 
        	// 딜규모에 마우스 올리면, 환율 정보가 툴팁으로 올라감.
            var ids =jQuery("#jqgrid_a").jqGrid('getDataIDs');
            for(var i = 0; i <  ids.length; i++ ){
            	//딜규모에 마우스를 오버하면 다양한(\, ¥, $) 환율이 표기된다.
            	//환율정보는 PO확정 전에는 주문의 환율, PO확정 후에는 PO의 환율을 사용
            	var content = "";   // 환율 정보가 들어갈 곳
            	var stdXchrKindCd = jQuery("#jqgrid_a").getRowData(ids[i]).stdXchrKindCd;
            	
            	var  stdXchrAmt = jQuery("#jqgrid_a").getRowData(ids[i]).stdXchrAmt;
            	var krwXchrAmt = jQuery("#jqgrid_a").getRowData(ids[i]).krwXchrAmt;
            	var usdXchrAmt = jQuery("#jqgrid_a").getRowData(ids[i]).usdXchrAmt;
            	var cnsXchrAmt = jQuery("#jqgrid_a").getRowData(ids[i]).cnsXchrAmt;
        		

            	var myMoney=3543.75873;
            	krwXchrAmt = '₩ ' + formatMoney(krwXchrAmt,2,',','.'); // "$3,543.76"
            	usdXchrAmt = '\n$ ' + formatMoney(usdXchrAmt,2,',','.'); // "$3,543.76"
            	cnsXchrAmt = '\n¥ ' + formatMoney(cnsXchrAmt,2,',','.'); // "$3,543.76"
            	
            	if(stdXchrAmt == ""){
        			content = "None";
        		}else{
	              		content = krwXchrAmt+usdXchrAmt+cnsXchrAmt;
        		}

//             	content += stdXchrAmt;
				jQuery("#jqgrid_a").jqGrid('setCell', ids[i],"ordSumAmt", jQuery("#jqgrid_a").getRowData(ids[i]).ordSumAmt ,"", {title: content});
            	
            	var bactPrvdMemoCont = jQuery("#jqgrid_a").getRowData(ids[i]).bactPrvdMemoCont;
            	jQuery("#jqgrid_a").jqGrid('setCell', ids[i],"bactPrvdDtPlusbactPrvdAmt", jQuery("#jqgrid_a").getRowData(ids[i]).bactPrvdDtPlusbactPrvdAmt ,"", {title: bactPrvdMemoCont});
            }
            // SQL PAGING 하기 위해서, VO 에 아래 열을 더 추가함.
        	var totalDbCount = $('#jqgrid_a').getRowData(1).count;
			var page = $('#jqgrid_a').getRowData(1).page;
			var row = $('#jqgrid_a').getRowData(1).row;
			
			if(totalDbCount == null){

			}else if(page == null){
				
			}else if(row == null){
				
			}else{
				$("#totalDbCount").val(totalDbCount);
				$("#page").val(page);
				$("#row").val(row);
			}
// 			$('.ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all').css("z-index","200 !important")

			customPager($("#totalDbCount").val(),$("#page").val(), $("#row").val());
			$('article').css("opacity","1");
			$('#countDB').html("搜索结果: 共 "+$("#totalDbCount").val()+"件");  
			

        },
        viewrecords: true,
        navGrid : true,
		shrinkToFit: false
        
  	  });

	
	// jqgrid가 로딩완료 되면, 이 pager로 jqgrid 아래에 pager를 지우고 새로 그린다.
	// 이 페이저에는 goToSelectedPage() 함수가 링크 걸려 있다.
	function customPager(totalDbCount, page, row){
		var pageCount = Math.ceil(totalDbCount / row);
		$('.paginate_complex').html('');
		var pagerHtml ='';
		var prevPage = parseInt(page)-1;
		
		if(prevPage>0){
			 pagerHtml += '<a href="#" class="direction prev" onclick="goToSelectedPage('+totalDbCount+','+1+','+row+')"><span></span><span></span> Prev End</a>';
			pagerHtml += '<a href="#" class="direction prev" onclick="goToSelectedPage('+totalDbCount+','+prevPage+','+row+')">Prev <span></span></a>';
		}
		if(parseInt(pageCount) >= 9){
			if(parseInt(page) <= 5){
				for(var i=1; i<=9; i++){
					if(i==parseInt(page)){
						pagerHtml += '<strong>'+i+'</strong>'
					}else{
						pagerHtml += '<a href="#" onclick="goToSelectedPage('+totalDbCount+','+i+','+row+')">'+i+'</a>';
					}
				}
			}else if( parseInt(pageCount) - parseInt(page) <=5){
				for(var i=parseInt(pageCount)-8; i<=parseInt(pageCount); i++){
					if(i==parseInt(page)){
						pagerHtml += '<strong>'+i+'</strong>'
					}else{
						pagerHtml += '<a href="#" onclick="goToSelectedPage('+totalDbCount+','+i+','+row+')">'+i+'</a>';
					}
				}
			}else{
				for(var i=parseInt(page)-4; i<=parseInt(page)+4; i++){
					if(i==parseInt(page)){
						pagerHtml += '<strong>'+i+'</strong>'
					}else{
						pagerHtml += '<a href="#" onclick="goToSelectedPage('+totalDbCount+','+i+','+row+')">'+i+'</a>';
					}
				}
			}
		}else{
			for(var i=1; i<=pageCount; i++){
				if(i==parseInt(page)){
					pagerHtml += '<strong>'+i+'</strong>'
				}else{
					pagerHtml += '<a href="#" onclick="goToSelectedPage('+totalDbCount+','+i+','+row+')">'+i+'</a>';
				}
			}
		}
		var nextPage = 1 + parseInt(page);
		if(parseInt(page)!=parseInt(pageCount)) {
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
			    "successfunc" : function(){
			    										alert("订单保存成功")  // 주문저장성공
			    										},
			    "url" : '${web_ctx}/orderManagementSave.ajax',
			    "extraparam" : {},
			    "aftersavefunc" : function( response ) {
			                          alert('saved : '+response);
			                      },
			    "errorfunc": function( response ) {
// 			                    	alert('error : '+response);
//									alert('订单保存失败');  // 주문저장실패
			                    },
			    "afterrestorefunc" : null,
			    "restoreAfterError" : true,
			    "mtype" : "POST"
			}
		for(var i=0; i<id.length; i++){
			
	   		   if(!checkIndex(RolesEditordSumAmt,roles)){
	      			jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:false});
	      		   }else{
	      			jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:true});
	      		   }
			// 이 이유는, multiselect 했을 때, 마지막 editable 여부에 따라, 값이 날아가고 안날아가고의 차이가 있기 때문.
			// 임시방편으로, 1개의 row 가 save 될 때, 그 직전에 그 row의 상태를 한번 더 확인하고, 전송여부를 판단하여 editable을 다시 설정해주고 전송한다.
			var ordStatCd =  $('#jqgrid_a').getCell(id[i],'ordStatCd');
			if(ordStatCd=="N000550300" || ordStatCd=="N000550400" ){
// 				PO확정 이후, 선금, 잔금, 입고일, 출항일, 도착일, P/O도착일 등 수정가능
				
				if(!checkIndex(RolesEditPapt,roles)){
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstAmt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstRate',{editable:false});	
				}else{
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstAmt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstRate',{editable:true});	
				}
				
				if(!checkIndex(RolesEditShipping,roles)){
					jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDestCd',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDestCd',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDestCd',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDestCd',{editable:false});
				}else{
					jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDestCd',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDestCd',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDestCd',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDestCd',{editable:true});
				}
				if(!checkIndex(RolesEditRapt,roles)){
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstDt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstAmt',{editable:false});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstRate',{editable:false});
				}else{
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstAmt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstRate',{editable:true});
				}
				var paptDpstRate = $('#jqgrid_a').getCell(id, 'paptDpstRate');
				var raptDpstRate = $('#jqgrid_a').getCell(id, 'raptDpstRate');
				
// 				if(paptDpstRate == "0.00"){
// 					alert("1");
// 					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:false});
// 					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstAmt',{editable:false});
// 					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstRate',{editable:false});
// 				}else if(raptDpstRate == "0.00"){
// 					alert("2");
// 					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstDt',{editable:false});
// 					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstAmt',{editable:false});
// 					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstRate',{editable:false});
// 				}else{
// 					alert(paptDpstRate);
// 					alert("3");
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstAmt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstRate',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstAmt',{editable:true});
					jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstRate',{editable:true});
// 				}
// 				jQuery("#jqgrid_a").jqGrid('setColProp','b5mBuyCont',{editable:true});
			}else{
				jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstAmt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','paptDpstRate',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','wrhsDlvDestCd',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','dptrDlvDestCd',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','arvlDlvDestCd',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','poDlvDestCd',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstDt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstAmt',{editable:false});
				jQuery("#jqgrid_a").jqGrid('setColProp','raptDpstRate',{editable:false});
// 				jQuery("#jqgrid_a").jqGrid('setColProp','b5mBuyCont',{editable:false});
			}
			jQuery("#jqgrid_a").jqGrid('saveRow',id[i],saveparameters);
		}
// 		var page = $('#jqgrid_a').getRowData(1).page;
// 		var row = $('#jqgrid_a').getRowData(1).row;
		var page = $("#page").val();
		var row = $("#row").val();

// 		$("#totalDbCount").html(totalDbCount);
// 		$("#page").html(page);
// 		$("#row").html(row);
		
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
				postData:{"rowInput":$('#rownum option:selected').val(), "pageInput":"1", "searchKeyword":$('#searchKeyWord').val()},
				rowNum : $('#rownum option:selected').val(),
				datatype : "json",
			}).trigger('reloadGrid');


			
			// 필터 체크 초기화 -> 페이징 or rownum 조정 or 검색 시에 바꿔준다.
			if($('#ui-multiselect-gs_ordTypeCd-option-0').is(':checked')){
				$('#ui-multiselect-gs_ordTypeCd-option-0').trigger('click');
			}
			if($('#ui-multiselect-gs_ordTypeCd-option-1').is(':checked')){
				$('#ui-multiselect-gs_ordTypeCd-option-1').trigger('click');
			}
			if($('#ui-multiselect-gs_ordTypeCd-option-2').is(':checked')){
				$('#ui-multiselect-gs_ordTypeCd-option-2').trigger('click');
			}
			
			if($('#ui-multiselect-gs_ordStatCd-option-0').is(':checked')){
				$('#ui-multiselect-gs_ordStatCd-option-0').trigger('click');
			}
			if($('#ui-multiselect-gs_ordStatCd-option-1').is(':checked')){
				$('#ui-multiselect-gs_ordStatCd-option-1').trigger('click');
			}
			if($('#ui-multiselect-gs_ordStatCd-option-2').is(':checked')){
				$('#ui-multiselect-gs_ordStatCd-option-2').trigger('click');
			}
			if($('#ui-multiselect-gs_ordStatCd-option-3').is(':checked')){
				$('#ui-multiselect-gs_ordStatCd-option-3').trigger('click');
			}
			if($('#ui-multiselect-gs_ordStatCd-option-4').is(':checked')){
				$('#ui-multiselect-gs_ordStatCd-option-4').trigger('click');
			}
			
			
			

			
			
		}
	});	
	$('.btn-search').click(function(){
		
		// 필터 체크 초기화 -> 페이징 or rownum 조정 or 검색 시에 바꿔준다.
		if($('#ui-multiselect-gs_ordTypeCd-option-0').is(':checked')){
			$('#ui-multiselect-gs_ordTypeCd-option-0').trigger('click');
		}
		if($('#ui-multiselect-gs_ordTypeCd-option-1').is(':checked')){
			$('#ui-multiselect-gs_ordTypeCd-option-1').trigger('click');
		}
		if($('#ui-multiselect-gs_ordTypeCd-option-2').is(':checked')){
			$('#ui-multiselect-gs_ordTypeCd-option-2').trigger('click');
		}
		
		if($('#ui-multiselect-gs_ordStatCd-option-0').is(':checked')){
			$('#ui-multiselect-gs_ordStatCd-option-0').trigger('click');
		}
		if($('#ui-multiselect-gs_ordStatCd-option-1').is(':checked')){
			$('#ui-multiselect-gs_ordStatCd-option-1').trigger('click');
		}
		if($('#ui-multiselect-gs_ordStatCd-option-2').is(':checked')){
			$('#ui-multiselect-gs_ordStatCd-option-2').trigger('click');
		}
		if($('#ui-multiselect-gs_ordStatCd-option-3').is(':checked')){
			$('#ui-multiselect-gs_ordStatCd-option-3').trigger('click');
		}
		if($('#ui-multiselect-gs_ordStatCd-option-4').is(':checked')){
			$('#ui-multiselect-gs_ordStatCd-option-4').trigger('click');
		}
		
		jQuery("#jqgrid_a").setGridParam({
			url : "${web_ctx}/orderManagementSearch.ajax",
			ajaxGridOptions : {async:false},    // 동기로 변환
			postData:{"rowInput":$('#rownum option:selected').val(), "pageInput":"1", "searchKeyword":$('#searchKeyWord').val()},
			rowNum : $('#rownum option:selected').val(),
			datatype : "json",
		}).trigger('reloadGrid');
	});
	
	function stringLengthLimit(cellvalue,options,rowObject){
		var inputString = cellvalue;
		if(inputString !=null){
			if(inputString.length>20){
				return inputString.substring(0,17)+"...";
			}else{
				return inputString;
			}
		}else{
			return "";
		}
	}
	
	function formatterCurrencyForOrdSumAmt(cellvalue,options,rowObject){

		// 		alert(rowObject.stdXchrKindCd);
		return rowObject.stdXchrKindCd;
		// 		alert(cellvalue);
		// 		if(cellvalue == null)
		// 			return "";
		// 		else 	if(rowObject.stdXchrKindCd == "N000590100")    // 달러
		// 			return '$ ' + formatMoney(cellvalue,2,',','.');
		// 		else if(rowObject.stdXchrKindCd == "N000590200")   // 원화
		// 			return '₩ ' + formatMoney(cellvalue,2,',','.');
		// 		else if(rowObject.stdXchrKindCd == "N000590300")   // 위안화
		// 			return '¥ ' + formatMoney(cellvalue,2,',','.');
		// 		else
		// 			return formatMoney(cellvalue,2,',','.')
				
		// 			alert("2");
		// 		if(rowObject.stdXchrKindCd == null){
		// 			return '未知 ';
		// 		}
	
	}
	function unformatterCurrencyForOrdSumAmt(cellvalue,options){
// 		alert("3");
		return cellvalue.replace("$","").replace("₩","").replace("¥","").replace(" ","").replace(",","");
	}
	
	function formatterCurrentIcon(cellvalue,options,rowObject){
				if(cellvalue == "N000590100")    // 달러
					return '$ ';
				else if(cellvalue == "N000590200")   // 원화
					return '₩ ';
				else if(cellvalue == "N000590300")   // 위안화
					return '¥ ';
				else{
					return ' ';
				}
	}
	
	function formatterCurrentAmt(cellvalue,options,rowObject){
		//3자리수마다 콤마 , 소수점이하 2자리까지 표시, prefix에 통화마크 표시(stdXchrKindCd)
		var result ="";
		var ro = rowObject.stdXchrKindCd;
		
		if(ro) {
			ro = formatterCurrentIcon(ro); 
		} else {
			ro = '';
		}
		if(cellvalue){
			result=ro + ' ' + formatMoney(cellvalue);
		}
		//return '$ ' + $.number(cellvalue, 2);
		return result;
		
	}
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
		return '<span class="ui-icon ui-icon-calendar"></span><a href="#" onclick="popUpHistory('+rowObject.ordNo+')">'+cellvalue+'</a>';
	}
 
	//Date 형식 formatter  (db 에는 varchar(8) 로 되어 있어서, formatter로 형식변환)
	function formatterDate(cellvalue,options,rowObject){
		if(cellvalue == null){
			return "";
		}else{
			return cellvalue;
		}
	}  

    // jqgrid의 DlvDestCd 컬럼에 대한 formatter
    function formatterDlvDestCd(cellvalue,options,rowObject){
  	
    	if(cellvalue == 'N000510100'){
			return "ICN";
		}else if(cellvalue == 'N000510200'){
			return "PUS";
		}else if(cellvalue == 'N000510300'){
			return "PTK";
		}else if(cellvalue == 'N000510400'){
			return "PVG";
		}else if(cellvalue == 'N000510500'){
			return "NGB";
		}else if(cellvalue == 'N000510600'){
			return "CGO";
		}else if(cellvalue == 'N000510700'){
			return "CKG";
		}else if(cellvalue == 'N000510800'){
			return "CAN";
		}else if(cellvalue == 'N000510900'){
			return "HGH";
		}else if(cellvalue == 'N000511000'){
			return "TSN";
		}else if(cellvalue == 'N000511100'){
			return "NKG";
		}else if(cellvalue == 'N000511200'){
			return "SZX";
		}else if(cellvalue == 'N000511300'){
			return "TAO";
		}else if(cellvalue == 'N000511400'){
			return "HKG";
		}else{
			return "";
		}
	};
	
 	$("#jqgrid_a").jqGrid('setGroupHeaders', {
		useColSpanStyle: true, 
		groupHeaders:[
			{startColumnName: 'paptDpstDt', numberOfColumns: 3, titleText: '首付'},
			{startColumnName: 'wrhsDlvDt', numberOfColumns: 2, titleText: '入库'},
			{startColumnName: 'dptrDlvDt', numberOfColumns: 2, titleText: '出港'},
			{startColumnName: 'arvlDlvDt', numberOfColumns: 2, titleText: '到岸'},
			{startColumnName: 'poDlvDt', numberOfColumns: 2, titleText: 'P/O'},
			{startColumnName: 'raptDpstDt', numberOfColumns: 3, titleText: '余款'},
			{startColumnName: 'b5cGudsRegDt', numberOfColumns: 2, titleText: '帮5采上传商品'},
			{startColumnName: 'stdXchrKindCd', numberOfColumns: 2, titleText: '交易规模'},
			
			
		]	
	});
 	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////// 필터
	$("#jqgrid_a").jqGrid('filterToolbar',{
		stringResult:true
		, searchOnEnter:true
		, defaultSearch:"cn"
		, groupOp:'AND'
			,onClearSearchValue : function (elem, coli, soptions, defval) {
				if(coli > 0) {
					var name = this.p.colModel[coli].name;
					if(name === 'ordTypeCd') {
						$(elem).val(defval);
						$(elem).multiselect('refresh');
						$(elem).siblings('button.ui-multiselect').css({
							width: "100%",
							marginTop: "1px",
							marginBottom: "1px",
							paddingTop: "3px"
						});
					}
					
				}
			},
			beforeClear : function() {
				var elem = $("#gs_ordTypeCd");
				elem.val("");
				elem.multiselect('refresh');
				elem.siblings('button.ui-multiselect').css({
						width: "100%",
						marginTop: "1px",
						marginBottom: "1px",
						paddingTop: "3px"
				});					
			}
		}
	);
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	$("#jqgrid_a").jqGrid('setFrozenColumns');
    $('#jqgrid_a').setGridWidth($(".ui-layout-jqgrid").width() - 2);
    $(window).bind('resize', function() {// Grid Resize 처리 
        $('#jqgrid_a').setGridWidth($(".ui-layout-jqgrid").width() - 2);
    }).trigger('resize');
	
	$(".btn_pop").each(function(){
       $(this).click(function(){
			window.open($(this).data("href"), "查看详情", "scrollbars=yes,width=" + $(this).data("popw") + ",height=" + $(this).data("poph") + ",top=10,left=20");
		}); 
    });
	
	$('.frozen-div td[colindex="2"]+td>input').removeAttr("id").addClass("ui-calendar tac");
	$('.ui-calendar').datepicker();
	$('.ui-calendar').datepicker('option', 'dateFormat', 'yy-mm-dd' );
	$('select.ui-widget-content.ui-corner-all').multiselect();

	
	

    // ajaxform
    $('#dialog_upload_form').ajaxForm({
    	forceSync:true,
        success: function(data){
           
      	  if(data=="success"){
      		 alert('订单保存成功');
      		 
      		$('#dialog_upload').dialog('close');
			$('#inputExcelFile').val('');
      		 jQuery("#jqgrid_a").setGridParam({
				url : "${web_ctx}/orderManagementSearch.ajax",
				ajaxGridOptions : {async:false},    // 동기로 변환
				postData:{"rowInput":$('#rownum option:selected').val(), "pageInput":"1", "searchKeyword":""},
				rowNum : $('#rownum option:selected').val(),
				datatype : "json",
			}).trigger('reloadGrid');
      		 
    	  }else if(data=="false"){			//DB입력시 잘못되는 경우 
    		  alert('订单保存失败');
    		  $('#dialog_upload').dialog('close');
    		  $('#inputExcelFile').val('');
    	  }else{									//엑셀파일이 잘못되었을경우
    		  if (data !==  '') {
	                $("#dialog_upload_result").dialog("option","buttons",{
	                	'上传失败excel下载': function(){
							
							url = '${web_ctx}/downloadInvalidExcel.do?fileName=' +  data;
							$(location).attr('href',url);
							$(this).dialog("close");
						}
	                });
    		  }
    		  $('#dialog_upload').dialog('close');
    		  $('#inputExcelFile').val('');
    		  $('#dialog_upload_result').dialog('open');
    	  }
			
// 			location.reload();
			
          }
    }); 	
	// [+신규주문등록] Dialog Control
	$('#dialog_upload').dialog({
		modal: true,
		autoOpen: false,
		width: 500,
		height: 150,
		buttons: {
			'上传文件': function(evt){
		 		if($('#inputExcelFile').val()==""){
	 				alert("请选择要上传的EXCEL文件。");
	 			}else{
	 				if (confirm("确认要上传所选中的文件?")) {
		 				$('#dialog_upload_form').submit();
	 				} 
	 			}
			}
		}
	});
	//엑셀결과 dialog 160405
	$("#dialog_upload_result").dialog({
		modal: true,
		autoOpen: false,
		width: 500,
		height: 150
	});

			
		
	// [Special + 접수] Dialog Control
	$('#dialog_upload_special').dialog({
		modal: true,
		autoOpen: false,
		width: 500,
		height: 270,
		buttons: {
			'文件上传': function(evt){
		 		if($('#inputExcelFileSpecial').val()==""){
	 				alert("请选择要上传的EXCEL文件。");
	 			}else{
	 				if (confirm("确认要上传所选中的文件?")) {
						$("#dialog_upload_form_special").attr('target', 'popUp');
						var newWindow=window.open("",'popUp','height=800,width=1500,top=0,left=0');
						$("#dialog_upload_form_special").submit();
						$('#dialog_upload_special').dialog('close');
						$('#inputExcelFileSpecial').val('');
	 				} else {

	 				}
	 			}
			}
		}
	});
	
});      // End : $(function(){}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//jqgrid 내에서, [상세보기] 클릭했을 때, row에서 스페셜오더, 접수 상태일 때는 아래의 다이얼로그가 활성화 된다.
function dialogSpecialExcel(ordNo){
	//dialog 에 해당 주문 번호 넘겨 주고,
	$('#inputExcelFileSpecial').val('');
	$('#ordNoSpecial').val(ordNo);
	
	$.ajax({
		type : "POST",
		url : '${web_ctx}/orderManagementSelectTbMsOrdSplReqCont.ajax',
		data:	{"ordNo" : ordNo},
		async: false,
		cache : false,
		success:function(result){ 
	 		$('#special_req_cont').val(result.reqCont);
		}
	});//end $.ajax	
	
	//dialog 활성화.
	$('#dialog_upload_special').dialog('open');

	
};

// [상세보기] 클릭 했을 때, 새 창
function popUp(ordNo){
	var address = "${web_ctx}/orderDetailView.do?ordNo="+ordNo;
	window.open( address, "idcheck", "top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=1300, height=700" );
}
// [현재상태 상세보기] 클릭 했을 때, 새 창
function popUpHistory(ordNo){
	var address = "${web_ctx}/orderHistoryView.do?ordNo="+ordNo;
	window.open( address, "idcheck", "top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=800, height=500" );
}


// pager 링크 함수.    jqgrid 아래에 pager 가 클릭되면, 이 함수가 실행.
// 선택한 page 로 DB 검색 후 jqgrid reload
function goToSelectedPage(totalDbCount, page, row){
	// 필터 체크 초기화 -> 페이징 or rownum 조정 or 검색 시에 바꿔준다.
	if($('#ui-multiselect-gs_ordTypeCd-option-0').is(':checked')){
		$('#ui-multiselect-gs_ordTypeCd-option-0').trigger('click');
	}
	if($('#ui-multiselect-gs_ordTypeCd-option-1').is(':checked')){
		$('#ui-multiselect-gs_ordTypeCd-option-1').trigger('click');
	}
	if($('#ui-multiselect-gs_ordTypeCd-option-2').is(':checked')){
		$('#ui-multiselect-gs_ordTypeCd-option-2').trigger('click');
	}
	
	if($('#ui-multiselect-gs_ordStatCd-option-0').is(':checked')){
		$('#ui-multiselect-gs_ordStatCd-option-0').trigger('click');
	}
	if($('#ui-multiselect-gs_ordStatCd-option-1').is(':checked')){
		$('#ui-multiselect-gs_ordStatCd-option-1').trigger('click');
	}
	if($('#ui-multiselect-gs_ordStatCd-option-2').is(':checked')){
		$('#ui-multiselect-gs_ordStatCd-option-2').trigger('click');
	}
	if($('#ui-multiselect-gs_ordStatCd-option-3').is(':checked')){
		$('#ui-multiselect-gs_ordStatCd-option-3').trigger('click');
	}
	if($('#ui-multiselect-gs_ordStatCd-option-4').is(':checked')){
		$('#ui-multiselect-gs_ordStatCd-option-4').trigger('click');
	}
	
	
	
	jQuery("#jqgrid_a").setGridParam({
		url : "${web_ctx}/orderManagementSearch.ajax",
		ajaxGridOptions : {async:false},    // 동기로 변환
		postData:{"rowInput":row, "pageInput":page},
		datatype : "json",
	}).trigger('reloadGrid');

};

//array와 array를 비교해여 해당값이 존재하는지 체크 
function checkIndex(btnRole, userRole){
   var flag=false;
   btnRole.forEach(function(role){
         if(userRole.indexOf(role)!=-1){
            flag=true;
            return true;
         }
      });
   return flag;
}

// 자바스크립트,  String -> currency format
function formatMoney(number, decPlaces, thouSeparator, decSeparator) {
    var n = number,
        decPlaces = isNaN(decPlaces = Math.abs(decPlaces)) ? 2 : decPlaces,
        decSeparator = decSeparator == undefined ? "." : decSeparator,
        thouSeparator = thouSeparator == undefined ? "," : thouSeparator,
        sign = n < 0 ? "-" : "",
        i = parseInt(n = Math.abs(+n || 0).toFixed(decPlaces)) + "",
        j = (j = i.length) > 3 ? j % 3 : 0;
    return sign + (j ? i.substr(0, j) + thouSeparator : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thouSeparator) + (decPlaces ? decSeparator + Math.abs(n - i).toFixed(decPlaces).slice(2) : "");
};

function reLoadJqgrid(){
	  var page = $("#page").val();
	  var row = $("#row").val();
// 	  alert("page : " + page + " row : " + row);
	   jQuery("#jqgrid_a").setGridParam({
	      url : "${web_ctx}/orderManagementSearch.ajax",
	      ajaxGridOptions : {async:false},    // 동기로 변환
	      postData:{"rowInput":row, "pageInput":page, "searchKeyword":$('#searchKeyWord').val()},
	      rowNum : $('#rownum option:selected').val(),
	      datatype : "json",
	   }).trigger('reloadGrid');
}

</script>
<style>
#jqgrid_a_ordTypeCd #jqgh_jqgrid_a_ordTypeCd{top:19px !important;}
</style>

