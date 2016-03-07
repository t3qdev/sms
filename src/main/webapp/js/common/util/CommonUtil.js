/*===================================================================*/
// 설명	: 공통으로 사용하는 클래스
/*===================================================================*/
commonUtil = {};

//===================================================================
//설명	: Ajax 공통 통신 처리 함수
//		: 세션처리없이 JsonArray일 경우
//인자	: var option = {
//				type: POST,GET 결정(String형 default:POST)
//				url: 액션명(String형),
//				callbackFn : 콜백함수명(함수),
//				data: 파라메타값(String형, 배열형)
//				async: true,false(boolean형 default:false)
//				cache: true,false(boolean형 default:false)
//				dataType: json,xml,html(String형 default:json)
//				error: 에러처리 코드 및 호출 Div
//				repeat: 로그인처리시 호출 함수 및 코드값(배열)
//	error 에러처리
//	-. errorCd		: 구분(A,B)(String형)
//	-. errorDv		: append 될 Div 명(String형)
//	arrRepeat 로그인팝업창 호출
//	-. sFn			: 로그인처리후 실행할함수
//	-. isDisplay	: 로그인 후 로그인 창 유지여부(true:유지, false:사라짐)
//	-. lType		: 로그인 창 종류(1:default[팝업,center])
//	-. lPos			: 로그인위치구분(1:SOI브라우저, 2:블로그, 3:가맹점, 4.광장, 5:회원가입/고객센터)
//
//기능	: Ajax 공통 통신 처리 함수로 에러 및 결과 콜백 함수등을 처리한다.
//
//[사용예] 단, 사용시 필요한 option만을 정리해서 사용하면 한다.
//var sData = "title="+$("#input_plf_reg_title").val()+"&text="+$("#textarea_plf_reg_text").val(); // String으로 data를 넘길 경우 사용
//	var title = $("#input_plf_reg_title").val();
//	var text = $("#textarea_plf_reg_text").val();
//	var arrData = {"title":title,"text":text}; // 배열로 data를 넘길 경우 사용
//	var arrRepeat = {"sFn":"plfFnBoardList","isDisplay":false,"lType":1,"lPos":2}; // 세션이 있을 경우
//	
//	var option = {
//			type : "POST",
//			url : "ar.plf.insertBoard.podo",
//			callbackFn : plfFnInitBoardList,
//			data : arrData,
//			async: false,
//			cache: false,
//			dataType:"json",
//			error: {errorCd:"B",errorDv:"dv_plf_board"},
//			repeat: arrRepeat
//		};
//	commonUtil.fnAjaxCall(option);
commonUtil.fnAjaxCall = function(option) {
	if(option.async == undefined || option.async == null) option.async = true;
	if(option.cache == undefined || option.cache == null) option.cache = false;
	$.ajax({
		type: option.type || "POST",
		url: option.url,
		data : option.data || "",
		async: option.async,
		cache: option.cache,
		dataType: option.dataType || "json",
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			// 공통 에러 출력
			commonError.fnAjaxErrorView(option.error.errorCd||"B",option.error.errorDv||"");
		},
		success: function(data){
			if(commonError.fnErrorSuccessFlagCheck(data)){
				// 로그인 실행
				if(!commonError.fnLoginSuccessFlagCheck(data)){
					// 로그인 레이어 팝업
					//comLogin.fnLoginLayerPopup(option.repeat.sFn,option.repeat.isDisplay,option.repeat.lType,option.repeat.lPos,option.repeat.cType); 
					commonUtil.fnLoginCall();
				}
				else {
					option.callbackFn(data);
				}
				
				//option.callbackFn(data);
			}else{
				// 공통 에러 출력 (레이어 디자인이 나오면 구성
				// 에러코드는 레이어 위치가 있는 일단적인 에러코드이고 내부코드는 특정한에러일경우(현재 디비에러를 종류별로 잡음)
				// 에 나타나는 것으로 내부코드는 로그시스템에서 사용한다.
				//alert("에러코드:"+data.error_loc_code+" 내부코드:"+data.error_detail_code);
				
				if(data.error_loc_code == "0000200001"){
					alert(data.error_detail_msg);
				}else if(data.error_detail_code == "0000100009"){
					alert("중복데이터 에러가 발생했습니다.");
					
				}else {
					alert("에러가 발생했습니다.");
				}
				//alert("에러가 발생했습니다.");
				//commonError.fnBusinessErrorView(option.error.errorCd||"B",option.error.errorDv||"");
			}
		}
	});
};

//===================================================================
//설명	: Form의 Object를  key, value로 담는다. 구분인자 "&"
//인자	: objForm :: [Object]
//기능	: Form의 Object를  key, value로 담는다. 
//등록, 수정, 삭제 시 Ajax 통신을 하며 이때, HTML의 Form Ojbect를 파라미터로 넘길 경우 사용한다.
//===================================================================
commonUtil.setQueryString = function(objForm) {
	queryString = "";
	var numberElements = objForm.elements.length; // check frm.elements.length - 1 로 하는 경우가 있음.
	for(var i = 0; i < numberElements; i++)
	{
		input = objForm.elements[i];

		if(i < numberElements - 1){
			queryString += input.name + "=" + encodeURIComponent(input.value) + "&";
		} else {
			queryString += input.name + "=" + encodeURIComponent(input.value);
		}
	}
	return queryString;
};

//===================================================================
//설명	: Form의 Object를  key, value로 담는다. 구분인자 "&"
//인자	: objForm :: [Object]
//기능	: Form의 Object를  key, value로 담는다. 
//등록, 수정, 삭제 시 Ajax 통신을 하며 이때, HTML의 Form Ojbect를 파라미터로 넘길 경우 사용한다.
//===================================================================
commonUtil.setFormString = function(objForm,rownm) {
	queryString = "";
	var numberElements = objForm.elements.length; // check frm.elements.length - 1 로 하는 경우가 있음.
	for(var i = 0; i < numberElements; i++)
	{
		input = objForm.elements[i];

		queryString += input.name + "=" + encodeURIComponent(input.value) + "&";
	}
	
	var gridData = $(rownm).jqGrid('getRowData');
	var gridCell = $(rownm).jqGrid("getGridParam", "colModel");
	
	var cell;
	
	for (var i = 0; i < gridData.length; i++) {
		for(var j=0;j<gridCell.length;j++){
			cell = eval("gridData[i]."+gridCell[j].name);
			
			queryString += gridCell[j].name + "=" + encodeURIComponent(cell) + "&";
		}
		
	}
	return queryString;
};

//===================================================================
//설명	: Form의 Object를  key, value로 담는다. 구분인자 "&"
//인자	: objForm :: [Object]
//기능	: Form의 Object를  key, value로 담는다. 
//등록, 수정, 삭제 시 Ajax 통신을 하며 이때, HTML의 Form Ojbect를 파라미터로 넘길 경우 사용한다.
//===================================================================
commonUtil.setGridString = function(rownm) {
	queryString = "";
	
	var gridData = $(rownm).jqGrid('getRowData');
	var gridCell = $(rownm).jqGrid("getGridParam", "colModel");
	
	var cell;
	
	for (var i = 0; i < gridData.length; i++) {
		for(var j=0;j<gridCell.length;j++){
			cell = eval("gridData[i]."+gridCell[j].name);
			
			queryString += gridCell[j].name + "=" + encodeURIComponent(cell) + "&";
		}
		
	}
	return queryString;
};

commonUtil.fnLoginCall = function() {
	//alert("로그인창으로 이동.");
	location.href = "loginCheck.do";
};

commonUtil.fnGridHeaderColor = function(){
	return "red";
};

commonUtil.fnGridHeight = function(){
	
	var gridHeight = 0;
	
	var _ua = navigator.userAgent;
	var agt = _ua.toLowerCase();
	
	var trident = _ua.match(/Trident\/(\d.\d)/i);
	
	if (agt.indexOf("msie") != -1 || trident != null){
		gridHeight = 360;
	}
	else{
		gridHeight = 350;
	}
//	 if (agt.indexOf("chrome") != -1) return 'Chrome';
//	 if (agt.indexOf("opera") != -1) return 'Opera'; 
//	 if (agt.indexOf("staroffice") != -1) return 'Star Office'; 
//	 if (agt.indexOf("webtv") != -1) return 'WebTV'; 
//	 if (agt.indexOf("beonex") != -1) return 'Beonex'; 
//	 if (agt.indexOf("chimera") != -1) return 'Chimera'; 
//	 if (agt.indexOf("netpositive") != -1) return 'NetPositive'; 
//	 if (agt.indexOf("phoenix") != -1) return 'Phoenix'; 
//	 if (agt.indexOf("firefox") != -1) return 'Firefox'; 
//	 if (agt.indexOf("safari") != -1) return 'Safari'; 
//	 if (agt.indexOf("skipstone") != -1) return 'SkipStone'; 
//	 if (agt.indexOf("netscape") != -1) return 'Netscape'; 
//	 if (agt.indexOf("mozilla/5.0") != -1) return 'Mozilla';

	return gridHeight;
};

commonUtil.fnGridMidHeight = function(){
	
	var gridHeight = 0;
	
	var _ua = navigator.userAgent;
	var agt = _ua.toLowerCase();
	
	var trident = _ua.match(/Trident\/(\d.\d)/i);
	
	if (agt.indexOf("msie") != -1 || trident != null){
		gridHeight = 180;
	}
	else{
		gridHeight = 180;
	}
//	 if (agt.indexOf("chrome") != -1) return 'Chrome';
//	 if (agt.indexOf("opera") != -1) return 'Opera'; 
//	 if (agt.indexOf("staroffice") != -1) return 'Star Office'; 
//	 if (agt.indexOf("webtv") != -1) return 'WebTV'; 
//	 if (agt.indexOf("beonex") != -1) return 'Beonex'; 
//	 if (agt.indexOf("chimera") != -1) return 'Chimera'; 
//	 if (agt.indexOf("netpositive") != -1) return 'NetPositive'; 
//	 if (agt.indexOf("phoenix") != -1) return 'Phoenix'; 
//	 if (agt.indexOf("firefox") != -1) return 'Firefox'; 
//	 if (agt.indexOf("safari") != -1) return 'Safari'; 
//	 if (agt.indexOf("skipstone") != -1) return 'SkipStone'; 
//	 if (agt.indexOf("netscape") != -1) return 'Netscape'; 
//	 if (agt.indexOf("mozilla/5.0") != -1) return 'Mozilla';

	return gridHeight;
};

commonUtil.fnDwGridHeight = function(){
	
	var gridHeight = 0;
	
	var _ua = navigator.userAgent;
	var agt = _ua.toLowerCase();
	
	var trident = _ua.match(/Trident\/(\d.\d)/i);
	
	if (agt.indexOf("msie") != -1 || trident != null){
		gridHeight = 380;
	}
	else{
		gridHeight = 370;
	}
//	 if (agt.indexOf("chrome") != -1) return 'Chrome';
//	 if (agt.indexOf("opera") != -1) return 'Opera'; 
//	 if (agt.indexOf("staroffice") != -1) return 'Star Office'; 
//	 if (agt.indexOf("webtv") != -1) return 'WebTV'; 
//	 if (agt.indexOf("beonex") != -1) return 'Beonex'; 
//	 if (agt.indexOf("chimera") != -1) return 'Chimera'; 
//	 if (agt.indexOf("netpositive") != -1) return 'NetPositive'; 
//	 if (agt.indexOf("phoenix") != -1) return 'Phoenix'; 
//	 if (agt.indexOf("firefox") != -1) return 'Firefox'; 
//	 if (agt.indexOf("safari") != -1) return 'Safari'; 
//	 if (agt.indexOf("skipstone") != -1) return 'SkipStone'; 
//	 if (agt.indexOf("netscape") != -1) return 'Netscape'; 
//	 if (agt.indexOf("mozilla/5.0") != -1) return 'Mozilla';

	return gridHeight;
};

commonUtil.fnMenuHeight = function(){
	
	var menuHeight = 0;
	
	var _ua = navigator.userAgent;
	var agt = _ua.toLowerCase();
	
	var wbgb = agt.match(/msie\ \d.\d/i);
	
	if (wbgb == "msie 8.0"){
		menuHeight = 15;
	}
	else{
		menuHeight = 31;
	}
	
	return menuHeight;
};
