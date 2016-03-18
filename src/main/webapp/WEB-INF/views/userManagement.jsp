<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="web_ctx" value="${pageContext.request.contextPath}" />

	<script type="text/javascript" src="libs/jquery-ui/external/jquery/jquery.js"></script>
	<script type="text/javascript" src="libs/jquery-ui/jquery-ui.js"></script>
	<script type="text/javascript" src="libs/jquery-ui/jquery-ui.autocomplete.js"></script>
	<script type="text/javascript" src="libs/jqGrid/js/minified/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="libs/jqGrid/js/minified/i18n/grid.locale-en.js"></script>
	<script type="text/javascript" src="js/common.js"></script>

<article>
    
	<h1>
		<span>用户管理</span>
	</h1>
    <div class="ui-layout-single">
        <section>
            <!--<h2><span>브랜드</span></h2> -->

            <div class="ui-layout-double">
                <section class="ui-layout-action">
                </section>
                <section class="ui-layout-action">
                    <button class="btn-add mr10" id="member_add">添加用户</button>
                </section>
            </div>
            <div class="ui-layout-jqgrid">
                <table id="jqgrid_a"></table>
            </div>
            <div id="pager_a"></div>
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
	
	// 네비게이션에 하이라이트 효과
	$('#userManagement').attr("class", "ui-state-active");	
	
	// [+회원추가] 버튼을 눌렀을 때, Jqgrid 에서 한 행 추가 하기 위해 생성한 변수
	cli_num = 0;
	
	//////////////////////////////////////////////////////// jqgrid로딩 및 jqgrid 세팅 ///////////////////////////////////////////////////////
	var lastsel;
	// Jqgrid  로딩
    $('#jqgrid_a').jqGrid({
		url : "${web_ctx}/userManagementLoad.ajax",
		ajaxGridOptions : {async:false},    // 동기로 변환
		datatype : "json",
		loadonce: true,            // jqgrid 누를때마다 로딩되는 것을 막음
		width: 600,
        //height: 250,
        colNames:['邮箱','花名','简称','密码','所属','权限1', '权限2', '权限3','权限4','用户状态'],
        colModel:[
            {name:'userEml',align:'center',editable:true, editrules:{required:true,email:true},editoptions:{readonly:'true'}},
            {name:'userAlasCnsNm',align:'center',width:80, editable: true, editrules:{required:true} },
			{name:'userAlasEngNm',align:'center',width:80, editable: true, editrules:{required:true}},
            {name:'userPwd',align:'center', editable: true,editrules:{required:true}, edittype: "password"},
            {name:'ognzDivCd',align:'center',width:80,formatter:'select',editable: true, edittype:"select",editoptions:{value:{N000530100:'上海',N000530200:'韩国'}}},
            {name:'roleGrpDivCd1',align:'center', formatter:'select',editable: true, edittype:"select",editoptions:{value:":;N000580100:最高权限;N000580200:阅览, 修正;N000580300:P/O确定;N000580400:结算;N000580500:物流"}},
            {name:'roleGrpDivCd2',align:'center', formatter:'select',editable: true, edittype:"select",editoptions:{value:":;N000580100:最高权限;N000580200:阅览, 修正;N000580300:P/O确定;N000580400:结算;N000580500:物流"}},		
            {name:'roleGrpDivCd3',align:'center', formatter:'select',editable: true, edittype:"select",editoptions:{value:":;N000580100:最高权限;N000580200:阅览, 修正;N000580300:P/O确定;N000580400:结算;N000580500:物流"}},		
            {name:'roleGrpDivCd4',align:'center', formatter:'select',editable: true, edittype:"select",editoptions:{value:":;N000580100:最高权限;N000580200:阅览, 修正;N000580300:P/O确定;N000580400:结算;N000580500:物流"}},		
            {name:'userStatCd',align:'center', formatter:'select',editable: true, edittype:"select",editoptions:{value:"N000610100:在职;N000610200:休假;N000610300:离职"}}		
        ],
    	onSelectRow: function(id){
    			jQuery('#jqgrid_a').jqGrid('editRow',id,false);
    	},
        multiselect: true,
        pager: '#pager_a',
        rowNum:100,
        viewrecords: true,

    });

    jQuery(".btn-save").click( function() {
		id = jQuery("#jqgrid_a").jqGrid('getGridParam','selarrrow');
		var data = $("#jqgrid_a").jqGrid("getRowData");
		var list = [];
		
		
		//이메일, 화명 중복 체크
		var allIds =jQuery("#jqgrid_a").jqGrid('getDataIDs');
		var userAlasCnsNmMap = new Map();
		var userEmlMap = new Map();
		for(var i = 0; i <  allIds.length; i++ ){
			jQuery("#jqgrid_a").jqGrid('saveRow',allIds[i]);
			var userEml = $('#jqgrid_a').getCell(allIds[i], 'userEml');
			var ind = $('#jqgrid_a').getInd(allIds[i],userAlasCnsNm);
			
			if(userEmlMap.has(userEml)){
				alert("duplicated userEml");
				jQuery("#jqgrid_a").setGridParam({
					url : "${web_ctx}/userManagementLoad.ajax",
					ajaxGridOptions : {async:false},    // 동기로 변환
					datatype : "json",
				}).trigger('reloadGrid');
				return;
			}else{
				userEmlMap.set(userEml,userEml);
			}	
			var userAlasCnsNm = $('#jqgrid_a').getCell(allIds[i], 'userAlasCnsNm');
			if(userAlasCnsNmMap.has(userAlasCnsNm)){
				alert("duplicated CnsNM");
				jQuery("#jqgrid_a").setGridParam({
					url : "${web_ctx}/userManagementLoad.ajax",
					ajaxGridOptions : {async:false},    // 동기로 변환
					datatype : "json",
				}).trigger('reloadGrid');
				return;
			}else{
				userAlasCnsNmMap.set(userAlasCnsNm,userAlasCnsNm);
			}
			
		}

		// END: 이메일, 화명 중복 체크
		
		
		saveparameters = {
			    "successfunc" : null,
			    "url" : '${web_ctx}/userManagementSave.ajax',
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
				jQuery('#jqgrid_a').jqGrid('editRow',id[i],false);
				jQuery("#jqgrid_a").jqGrid('saveRow',id[i],saveparameters);
		}
		jQuery("#jqgrid_a").setGridParam({
			url : "${web_ctx}/userManagementLoad.ajax",
			ajaxGridOptions : {async:false},    // 동기로 변환
			datatype : "json",
		}).trigger('reloadGrid');
    });


    // jqgrid의 password컬럼에 대한 formatter
    function formatterUserPwd(cellvalue,options,rowObject){
    		// 이유 : 서버에서 보안상 password 를 화면으로 전송하지 않는데, 고객 요구는 form 에 * 라도 있어야 한다고 요구.
    		// 컨트롤러에서는 "**********" 가 들어오면 비밀번호 변경으로 인식하지 않는다.
			return "**********";
    }
    
    // jqgrid의 ognzDivCd 컬럼에 대한 formatter
    function formatterOgnzDivCd(cellvalue,options,rowObject){
		if(cellvalue == 'N000530200'){
			return "韩国";
		}else if(cellvalue == 'N000530100'){
			return "上海";
		}else{
			return "";
		}
    }

    // jqgrid의 roleGrpDivCd 컬럼에 대한 formatter와 unformatter
    function formatterRoleGrpDivCd(cellvalue,options,rowObject){
		if(cellvalue == 'N000580100'){
			return "最高权限";
		}else if(cellvalue == 'N000580200'){
			return "阅览, 修正";
		}else if(cellvalue == 'N000580300'){
			return "P/O确定";
		}else if(cellvalue == 'N000580400'){
			return "结算";
		}else if(cellvalue == 'N000580500'){
			return "物流";
		}else{
			return "";
		}
		return cellvalue;
    }
    function unformatterSelect1(cellvalue,options,rowObject){
    	return $('#1_roleGrpDivCd' + options.rowId + ' option:selected').val();
    }
    function unformatterSelect2(cellvalue,options,rowObject){
    	return $('#2_roleGrpDivCd' + options.rowId + ' option:selected').val();
    }
    function unformatterSelect(cellvalue,options,rowObject){
    	return $('#2_roleGrpDivCd' + options.rowId + ' option:selected').val();
    }

    // jqgrid의 userStatCd 컬럼에 대한 formatter와 unformatter
    function formatterUserStatCd(cellvalue,options,rowObject){
		if(cellvalue == 'N000610100'){
			return "在职";
		}else if(cellvalue == 'N000610200'){
			return "休假";
    	}else if(cellvalue == 'N000610300'){
    		return "离职";
    	}else{
    		return "";
    	}
    }

    //jqgrid Sorting 가능 세팅
	$("#jqgrid_a").sortableRows();
    
    //jqgrid Drag and Drop 가능 세팅
// 	$("#jqgrid_a").jqGrid('gridDnD');
			
    $('#jqgrid_a').setGridWidth($(".ui-layout-jqgrid").width() - 2);
    $(window).bind('resize', function() {// Grid Resize 처리 
        $('#jqgrid_a').setGridWidth($(".ui-layout-jqgrid").width() - 2);
    }).trigger('resize');
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
    //$("#jqgrid_a").jqGrid('setFrozenColumns');




    
    $("#member_add").click(function(){
        rowdata = {
            0:'',
            1:'',
			9:'',
            2:'',
            3:'',
            4:'',
            5:'',
            6:'',
            7:'',
            8:''
        };
        
        $("#jqgrid_a").jqGrid('addRowData','new_'+cli_num,rowdata,'first');
        jQuery("#jqgrid_a").setColProp('userEml', {editoptions:{readonly:false}});
        jQuery("#jqgrid_a").setColProp('userPwd', {editrules:{required:true}});
        $("#jqg_jqgrid_a_new_"+cli_num).attr("checked","checked").click().parent().parent().addClass("ui-state-highlight");
		
        cli_num++;
        
    });
    
    
});
</script>