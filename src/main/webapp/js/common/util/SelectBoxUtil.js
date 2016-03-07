//  ======================================================================
//  Author      : 석동훈
//  Date        : 2013. 12. 02. 
//  Description : 숫자입력 체크
//
//  ------------ MODIFICATIONLOG -----------------------------------------
//  Ver  Date            Author       Modification
//  
//  ======================================================================



function fnChangeSelelectBox(){
	//축종코드 변경시 - 브랜드코드, 품목코드, 등급코드를 재조회한다.
	$("#cattle_species_code").change( function() {
		$("#origin_code option").not("[value='']").remove();
		$("#brand_code option").not("[value='']").remove();
		$("#item_code option").not("[value='']").remove();
		$("#grade_code option").not("[value='']").remove();
		$("#factory_code option").not("[value='']").remove();
		
		var	option = {
				url: "getSearchCodeList.do",
				callbackFn: fnCallback_search_code,
				data: {cattle_species_code : $("#cattle_species_code option:selected").val(), origin_code : $("#origin_code option:selected").val()}
			};
			
			commonUtil.fnAjaxCall(option);
	});
	
	//원산지코드 변경시 - 브랜드코드, 품목코드, 등급코드를 재조회한다.
	$("#origin_code").change( function() {
		$("#brand_code option").not("[value='']").remove();
		$("#item_code option").not("[value='']").remove();
		$("#grade_code option").not("[value='']").remove();
		$("#factory_code option").not("[value='']").remove();
		
		var	option = {
				url: "getSearchCodeList.do",
				callbackFn: fnCallback_search_code,
				data: {cattle_species_code : $("#cattle_species_code option:selected").val(), origin_code : $("#origin_code option:selected").val()}
			};
			
			commonUtil.fnAjaxCall(option);
	});
	
	//브랜드코드 변경시 - 공장코드를 조회한다.
	$("#brand_code").change( function() {
		$("#factory_code option").not("[value='']").remove();
		
			var option = {
				url: "getSearchCodeList.do",
				callbackFn: fnCallback_search_factory_code,
				data: {cattle_species_code : $("#cattle_species_code option:selected").val(), origin_code : $("#origin_code option:selected").val(), brand_code : $("#brand_code option:selected").val()}
			};
			
			commonUtil.fnAjaxCall(option);
	});
}
	
	/**
	 * 검색조건 select box
	 */
	//축종코드, 원산지 변경시 브랜드,품목, 원산지 코드 재조회
function fnCallback_search_code(data){
	if("Y"==data.originCodeFlag){
		//원산지코드조회
		//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
		$("#origin_code option").not("[value='']").remove();
		if(data.originCode != null) {
			for(var i=0; i<data.originCode.length; i++) {
				$("#origin_code").append("<option value='" + data.originCode[i].origin_code + "'>" + data.originCode[i].origin_code_hnm+ "</option>");
			}
		}
	}
	
	//브랜드코드조회
	//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
	$("#brand_code option").not("[value='']").remove();
	if(data.brandCode != null) {
		for(var i=0; i<data.brandCode.length; i++) {
			$("#brand_code").append("<option value='" + data.brandCode[i].brand_code + "'>" + data.brandCode[i].brand_code_hnm+ "</option>");
		}
	}
	//품목코드조회
	//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
	$("#item_code option").not("[value='']").remove();
	if(data.itemCode != null) {
		for(var i=0; i<data.itemCode.length; i++) {
			$("#item_code").append("<option value='" + data.itemCode[i].item_code + "'>" + data.itemCode[i].item_code_hnm+ "</option>");
		}
	}
	//등급코드조회
	//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
	$("#grade_code option").not("[value='']").remove();
	if(data.gradeCode != null) {
		for(var i=0; i<data.gradeCode.length; i++) {
			$("#grade_code").append("<option value='" + data.gradeCode[i].grade_code + "'>" + data.gradeCode[i].grade_code_hnm+ "</option>");
		}	
	}
	//공장코드조회
	//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
	$("#factory_code option").not("[value='']").remove();
	if(data.factoryCode != null) {
		for(var i=0; i<data.factoryCode.length; i++) {
			$("#factory_code").append("<option value='" + data.factoryCode[i].factory_code + "'>" + data.factoryCode[i].factory_code_hnm+ "</option>");
		}	
	}
}

//축종코드, 원산지 변경시 브랜드,품목, 원산지 코드 재조회
function fnCallback_search_factory_code(data){

	//공장코드조회
	//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
	$("#factory_code option").not("[value='']").remove();
	if(data.factoryCode != null) {
		for(var i=0; i<data.factoryCode.length; i++) {
			$("#factory_code").append("<option value='" + data.factoryCode[i].factory_code + "'>" + data.factoryCode[i].factory_code_hnm+ "</option>");
		}	
	}
}


//영문화면 select box
function fnChangeSelelectEngBox(){
	//축종코드 변경시 - 브랜드코드, 품목코드, 등급코드를 재조회한다.
	$("#cattle_species_code").change( function() {
		$("#origin_code option").not("[value='']").remove();
		$("#brand_code option").not("[value='']").remove();
		$("#item_code option").not("[value='']").remove();
		$("#grade_code option").not("[value='']").remove();
		$("#factory_code option").not("[value='']").remove();
		
		var	option = {
				url: "getSearchCodeList.do",
				callbackFn: fnCallback_search_code_eng,
				data: {cattle_species_code : $("#cattle_species_code option:selected").val(), origin_code : $("#origin_code option:selected").val()}
			};
			
			commonUtil.fnAjaxCall(option);
	});
	
	//원산지코드 변경시 - 브랜드코드, 품목코드, 등급코드를 재조회한다.
	$("#origin_code").change( function() {
		$("#brand_code option").not("[value='']").remove();
		$("#item_code option").not("[value='']").remove();
		$("#grade_code option").not("[value='']").remove();
		$("#factory_code option").not("[value='']").remove();
		
		var	option = {
				url: "getSearchCodeList.do",
				callbackFn: fnCallback_search_code_eng,
				data: {cattle_species_code : $("#cattle_species_code option:selected").val(), origin_code : $("#origin_code option:selected").val()}
			};
			
			commonUtil.fnAjaxCall(option);
	});
	
	//브랜드코드 변경시 - 공장코드를 조회한다.
	$("#brand_code").change( function() {
		$("#factory_code option").not("[value='']").remove();
		
			var option = {
				url: "getSearchCodeList.do",
				callbackFn: fnCallback_search_factory_code_eng,
				data: {cattle_species_code : $("#cattle_species_code option:selected").val(), origin_code : $("#origin_code option:selected").val(), brand_code : $("#brand_code option:selected").val()}
			};
			
			commonUtil.fnAjaxCall(option);
	});
}

function fnCallback_search_code_eng(data){
	if("Y"==data.originCodeFlag){
		//원산지코드조회
		//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
		$("#origin_code option").not("[value='']").remove();
		if(data.originCode != null) {
			for(var i=0; i<data.originCode.length; i++) {
				$("#origin_code").append("<option value='" + data.originCode[i].origin_code + "'>" + data.originCode[i].origin_code_enm+ "</option>");
			}
		}
	}
	
	//브랜드코드조회
	//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
	$("#brand_code option").not("[value='']").remove();
	if(data.brandCode != null) {
		for(var i=0; i<data.brandCode.length; i++) {
			$("#brand_code").append("<option value='" + data.brandCode[i].brand_code + "'>" + data.brandCode[i].brand_code_enm+ "</option>");
		}
	}
	//품목코드조회
	//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
	$("#item_code option").not("[value='']").remove();
	if(data.itemCode != null) {
		for(var i=0; i<data.itemCode.length; i++) {
			$("#item_code").append("<option value='" + data.itemCode[i].item_code + "'>" + data.itemCode[i].item_code_enm+ "</option>");
		}
	}
	//등급코드조회
	//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
	$("#grade_code option").not("[value='']").remove();
	if(data.gradeCode != null) {
		for(var i=0; i<data.gradeCode.length; i++) {
			$("#grade_code").append("<option value='" + data.gradeCode[i].grade_code + "'>" + data.gradeCode[i].grade_code_enm+ "</option>");
		}	
	}
	//공장코드조회
	//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
	$("#factory_code option").not("[value='']").remove();
	if(data.factoryCode != null) {
		for(var i=0; i<data.factoryCode.length; i++) {
			$("#factory_code").append("<option value='" + data.factoryCode[i].factory_code + "'>" + data.factoryCode[i].factory_code_enm+ "</option>");
		}	
	}
}

//축종코드, 원산지 변경시 브랜드,품목, 원산지 코드 재조회
function fnCallback_search_factory_code_eng(data){
	
	//공장코드조회
	//기본값만 남기고 모든 옵션 삭제 후 옵션을 추가한다.
	$("#factory_code option").not("[value='']").remove();
	if(data.factoryCode != null) {
		for(var i=0; i<data.factoryCode.length; i++) {
			$("#factory_code").append("<option value='" + data.factoryCode[i].factory_code + "'>" + data.factoryCode[i].factory_code_enm+ "</option>");
		}	
	}
}