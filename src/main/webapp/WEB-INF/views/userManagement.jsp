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
		<span>사용자관리</span>
	</h1>
    <div class="ui-layout-single">
        <section>
            <!--<h2><span>브랜드</span></h2> -->

            <div class="ui-layout-double">
                <section class="ui-layout-action">
                </section>
                <section class="ui-layout-action">
                    <button class="btn-add mr10" id="member_add">회원추가</button>
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

            <button class="btn-save">저장</button>
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
        colNames:['이메일','화명','약칭','비밀번호','소속','권한1', '권한2', '권한3','권한4','사용자상태'],
        colModel:[
            {name:'userEml',align:'center',editable:true, editrules:{required:true,email:true},editoptions:{readonly:'true'}},
            {name:'userAlasCnsNm',align:'center',width:80, editable: true },
			{name:'userAlasEngNm',align:'center',width:80, editable: true},
            {name:'userPwd',align:'center',formatter : formatterUserPwd, editable: true,editrules:{required:false}, edittype: "password"},
            {name:'ognzDivCd',align:'center',width:80,formatter:formatterOgnzDivCd,editable: true, edittype:"select",editoptions:{value:{N000530100:'상해팀',N000530200:'한국팀'}}},
            {name:'roleGrpDivCd1',align:'center', formatter:formatterRoleGrpDivCd,editable: true, edittype:"select",editoptions:{value:":;N000580100:마스터;N000580200:열람, 수정;N000580300:PO확정;N000580400:결제;N000580500:물류"}},
            {name:'roleGrpDivCd2',align:'center', formatter:formatterRoleGrpDivCd,editable: true, edittype:"select",editoptions:{value:":;N000580100:마스터;N000580200:열람, 수정;N000580300:PO확정;N000580400:결제;N000580500:물류"}},		
            {name:'roleGrpDivCd3',align:'center', formatter:formatterRoleGrpDivCd,editable: true, edittype:"select",editoptions:{value:":;N000580100:마스터;N000580200:열람, 수정;N000580300:PO확정;N000580400:결제;N000580500:물류"}},		
            {name:'roleGrpDivCd4',align:'center', formatter:formatterRoleGrpDivCd,editable: true, edittype:"select",editoptions:{value:":;N000580100:마스터;N000580200:열람, 수정;N000580300:PO확정;N000580400:결제;N000580500:물류"}},		
            {name:'userStatCd',align:'center', formatter:formatterUserStatCd,editable: true, edittype:"select",editoptions:{value:"N000610100:재직;N000610200:휴직;N000610300:퇴직"}}		
        ],
    	onSelectRow: function(id){
    			jQuery('#jqgrid_a').jqGrid('editRow',id,true);
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
			return "한국팀";
		}else if(cellvalue == 'N000530100'){
			return "상해팀";
		}else{
			return "";
		}
    }

    // jqgrid의 roleGrpDivCd 컬럼에 대한 formatter와 unformatter
    function formatterRoleGrpDivCd(cellvalue,options,rowObject){
		if(cellvalue == 'N000580100'){
			return "마스터";
		}else if(cellvalue == 'N000580200'){
			return "열람, 수정";
		}else if(cellvalue == 'N000580300'){
			return "PO확정";
		}else if(cellvalue == 'N000580400'){
			return "결제";
		}else if(cellvalue == 'N000580500'){
			return "물류";
		}else{
			return "";
		}
    }

    // jqgrid의 userStatCd 컬럼에 대한 formatter와 unformatter
    function formatterUserStatCd(cellvalue,options,rowObject){
		if(cellvalue == 'N000610100'){
			return "재직";
		}else if(cellvalue == 'N000610200'){
			return "휴직";
    	}else if(cellvalue == 'N000610300'){
    		return "퇴직";
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