//  ======================================================================
//  Author      : 석동훈
//  Date        : 2013. 12. 02. 
//  Description : 리플레쉬조건 체크
//
//  ------------ MODIFICATIONLOG -----------------------------------------
//  Ver  Date            Author       Modification
//  
//  ======================================================================

var timer = new Array();
var k = -1;
var l = 1;

function refresh(grid_nm, fn_nm){
	k++;
	
	timer[k] = setInterval(function () {
		
		if(l == 0){
			
			var colNames = eval("$('" + grid_nm + "').getGridParam('colNames');");
			var colModel = eval("$('" + grid_nm + "').getGridParam('colModel');");
			
			var flag = "true";
			var check_value = "";
			
			var k = 0;
			for(var i=0; i<colNames.length;i++){
				
				if(colModel[i].editable){
					if(k == 0){
						k++;
						check_value = colModel[i].name;
					}else{
						check_value = check_value + "," + colModel[i].name;
					}
				}
			}
			
			if(focus_flag == "Y"){
				flag = "false";
			}else{
				/*
				var checkId = check_value.split(",");
				
				for (i in checkId) {
					
					// 그리드 데이터 가져오기
					var gridData = eval("$('" + grid_nm + "').jqGrid('getRowData');");
					//var gridData = $(grid_nm).jqGrid('getRowData');
					
					for (var j = 0; j < gridData.length; j++) {
						
						var celvalue = eval("$('"+grid_nm+"').getRowData(" + j + 1 + ")." + checkId[i]);
						
						if(celvalue > 0){
							flag = "false";
							break;
						}else{
							flag = "true";
						}
					}
					
					if(flag == "false"){
						break;
					}
				}
				
				*/
				
				flag = "true";
			}
			
			if(flag == "true"){
				eval(fn_nm + ";");
			}else{
				
			}
			
			flag = true;
		}else{
			l = 0;
		}
		
	}, 2000);
}


function clear_timer(){
	for(var j = 0;j < timer.length;j++){
		clearInterval(timer[j]);
		timer[j] = null;
	}
	timer = new Array();
	k = -1;
	l = 1;
	
}

function normal_refresh(fn_nm){
	timer = setInterval(function () {
		
		eval(fn_nm + ";");
		
	}, 2000);
}