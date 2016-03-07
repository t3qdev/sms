
commonError = {};

//----------------------------------------------------------------------------------------------------
//Json Array 경우
//----------------------------------------------------------------------------------------------------

commonError.fnErrorSuccessFlagCheck = function(data) {
	
	if(data == null || data.length==0 || data.success_flag  == "false"){
		return false;
	}else{
		return true;
	}
	/*
	if(data != null && data.length!=0 && data.success_flag  == "true" ){
		
		//정상이면:true
		return true;
	}
	
	//에러이면:false
	return false;
	*/
};

commonError.fnLoginSuccessFlagCheck = function(data) {
	
	if(data != null && data.length!=0 && data.session_check  == "true"){
		return false;
	}else{
		return true;
	}
	/*
	if(data != null && data.length!=0 && data.success_flag  == "true" ){
		
		//정상이면:true
		return true;
	}
	
	//에러이면:false
	return false;
	*/
};
