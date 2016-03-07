//  ======================================================================
//  Author      : 신진섭
//  Date        : 2013. 12. 02. 
//  Description : 숫자입력 체크
//
//  ------------ MODIFICATIONLOG -----------------------------------------
//  Ver  Date            Author       Modification
//  
//  ======================================================================

	
/**
 *  그리드 체크박스 체크 컨트롤
 */

function fnCheckbox(gridId,rowid,status){
	
	var elem = document.activeElement;
	var chkId = "jqg_"+ gridId + "_" + rowid;
	
	if (elem.id == chkId) {
		if (!status){
        	$('#'+elem.id).attr('checked',false);
      	}
    } 
	else{
    	$('#'+gridId).setSelection(rowid, false);
    }	
}

/**
 *  그리드 font 색상
 *  row전체 적용
 */

function fn_fontColorChange(gridId,cellname,status){
	var color="red";
	
	var colModel = $("#"+gridId).getGridParam("colModel");
	
	var ids = $("#"+gridId).jqGrid('getDataIDs');
	
	for(var i=0;i < ids.length;i++){
		if($("#"+gridId).getCell(ids[i],cellname)==status){
			for(var j=0; j<colModel.length;j++){
				$("#"+gridId).jqGrid("setCell",ids[i],colModel[j].name,"",{color:color});
			}
		}
	}
}