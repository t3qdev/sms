//  ======================================================================
//  Author      : 석동훈
//  Date        : 2013. 12. 02. 
//  Description : 숫자입력 체크
//
//  ------------ MODIFICATIONLOG -----------------------------------------
//  Ver  Date            Author       Modification
//  
//  ======================================================================
	//숫자만 입력가능
	fn_Number = function (obj, e){
		if(e.which=='229' || e.which=='197' && $.browser.opera) {
				setInterval(function(){
					 	obj.trigger('keyup');
					}, 100);
			   }
			       
		       if ( ! (e.which && ((e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106))|| e.which ==8 || e.which ==9|| e.which ==13|| e.which ==0 ||e.which==46 ||e.which==37 ||e.which==39 || (e.ctrlKey && e.which ==86) ) ) {
		    	   e.preventDefault(); 
		       }
		       
		       var value = obj.val().match(/[^0-9]/g);
		       if(value!=null) {
			   		obj.val(obj.val().replace(/[^0-9]/g,''));
		       }
			   
	};
	
	//숫자와 '-' 만 입력가능
	fn_NumberDash = function (obj, e){
		if(e.which=='229' || e.which=='197' && $.browser.opera) {
					setInterval(function(){
					 	obj.trigger('keyup');
					}, 100);
			   }
			       
		       if ( ! (e.which && ((e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106))|| e.which ==8 || e.which ==9|| e.which ==13|| e.which ==0|| e.which ==189 || (e.ctrlKey && e.which ==86)) ) {
				 	e.preventDefault();
		       }
			  
		       var value = obj.val().match(/[^0-9-]/g);
		       if(value!=null) {
			   		obj.val(obj.val().replace(/[^0-9-]/g,''));
		       }
	};
	
	//숫자와 '.' 만 입력가능
	fn_NumberDat = function (obj, e){
		if(e.which=='229' || e.which=='197' && $.browser.opera) {
			setInterval(function(){
				obj.trigger('keyup');
			}, 100);
		}
		
		if ( ! (e.which && ((e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106))|| e.which ==8 || e.which ==9|| e.which ==13|| e.which ==0|| e.which ==189 || (e.ctrlKey && e.which ==86)) ) {
			e.preventDefault();
		}
		
		var value = obj.val().match(/[^0-9\.]/g);
		if(value!=null) {
			obj.val(obj.val().replace(/[^0-9\.]/g,''));
		}
	};
	
	fn_DecimalPoint = function (obj, pointSize){
		
		var value=obj.val().split('.');
		
		if(value[1]!=null && value[1].length>pointSize) {
			var resultValue=value[0]+"."+value[1].substr(0,value[1].length-1);
			obj.val(resultValue);
	    }
	};
	
	fn_sCom=function (num){
		if(num!=null && num!=""){
			return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		}
		else
			return;
	};
	
	fn_rCom=function(num){
		if(num!=null && num!="")
			return parseFloat(num.replace(/,/g,""));
		else
			return "";
	};
	
	fn_EngNumber = function (obj, e){
		
		if(e.which=='229' || e.which=='197' && $.browser.opera) {
				setInterval(function(){
					 	obj.trigger('keyup');
					}, 100);
			   }
			       
		       if ( ! (e.which && ((e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106))|| e.which ==8 || e.which ==9|| e.which ==13|| e.which ==0 ||e.which==46 ||e.which==37 ||e.which==39 || (e.ctrlKey && e.which ==86) || (65 <= e.which && e.which <=90)) ) {
				 	e.preventDefault();
		       }
		       
		       var value = obj.val().match(/[^a-zA-Z0-9]/g);
		       if(value!=null) {
			   		obj.val(obj.val().replace(/[^a-zA-Z0-9]/g,''));
		       }
			   
	};
	
	/**
	 * 한글포함 문자열 길이를 구한다
	 */
	getTextLength = function(str) {
	    var len = 0;
	    for (var i = 0; i < str.length; i++) {
	        if (escape(str.charAt(i)).length == 6) {
	            len++;
	        }
	        len++;
	    }
	    return len;
	};
	
	fn_dateChk = function(date){
		var rtn=null;
		
		if(date.length==6){
			if(1>date.substr(4,2) || 12<date.substr(4,2) || 0==date.substr(0,1)){
				alert("请确认输入的日期");
				return false;
			}
		}
		else{
			alert("请确认输入的日期");
			return false;
		}
		
	 	$.ajax({
				url: "getCurrentDate.do",
				type: 'post',
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				async:false,
				dataType: "json",
				success: function(data)
				{
					if(!commonError.fnErrorSuccessFlagCheck(data)){
						alert("出错了");
						return;
					}

					if((data.currentDate).substr(0,date.length)>date){
						alert("无法输入已过的日期");
						rtn=false;
					}
					else{
						rtn=true;
					}
				}
			});
	 	
	 	return rtn;
	};
	
	fn_preDateChk = function(date){
		var rtn=null;
		
		if(date.length==6){
			if(1>date.substr(4,2) || 12<date.substr(4,2) || 0==date.substr(0,1)){
				alert("请确认输入的日期");
				return false;
			}
		}
		else{
			alert("请确认输入的日期");
			return false;
		}
		
		$.ajax({
			url: "getCurrentDate.do",
			type: 'post',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			async:false,
			dataType: "json",
			success: function(data)
			{
				if(!commonError.fnErrorSuccessFlagCheck(data)){
					alert("出错了");
					return;
				}
				
				if((data.currentDate).substr(0,date.length)<date){
					alert("无法输入未来的日期");
					rtn=false;
				}
				else{
					rtn=true;
				}
			}
		});
		
		return rtn;
	};

	fn_dateChkEng = function(date){
		var rtn=null;
		
		if(date.length==6){
			if(1>date.substr(4,2) || 12<date.substr(4,2) || 0==date.substr(0,1)){
				alert("Please check the date.");
				return false;
			}
		}
		else{
			if(date.length!=8){
				alert("Please check the date.");
				return false;
			}
		}
		
		$.ajax({
			url: "getCurrentDate.do",
			type: 'post',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			async:false,
			dataType: "json",
			success: function(data)
			{
				if(!commonError.fnErrorSuccessFlagCheck(data)){
					alert("Error");
					return;
				}
				
				if((data.currentDate).substr(0,date.length)>date){
					alert("You can not enter a date earlier than the current date.");
					rtn=false;
				}
				else{
					rtn=true;
				}
			}
		});
		
		return rtn;
	};

	fn_preDateChkEng = function(date){
		var rtn=null;
		
		if(date.length==6){
			if(1>date.substr(4,2) || 12<date.substr(4,2) || 0==date.substr(0,1)){
				alert("Please check the date.");
				return false;
			}
		}
		else{
			if(date.length!=8){
				alert("Please check the date.");
				return false;
			}
		}
		
		$.ajax({
			url: "getCurrentDate.do",
			type: 'post',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			async:false,
			dataType: "json",
			success: function(data)
			{
				if(!commonError.fnErrorSuccessFlagCheck(data)){
					alert("Error");
					return;
				}
				
				if((data.currentDate).substr(0,date.length)<date){
					alert("You can not enter a date later than the current date.");
					rtn=false;
				}
				else{
					rtn=true;
				}
			}
		});
		
		return rtn;
	};
	
	//판매흥정 제시가격 체크 상대단가 대비 85% 미만시
	function fn_bargainPriceChk(gridObj,cellName,rowid,otherProposePice,proposePrice,proposeGbn){
		if(Math.round(parseInt(fn_rCom(otherProposePice))*85/100)>parseInt(proposePrice)){
			var strAlert="";
			
			if(proposeGbn=="propose"){
				strAlert="提示单价";
			}
			else{
				strAlert="修正单价";
			}
			
			alert(strAlert+" 太低");
			$(gridObj).setCell(rowid,cellName,0);
			
		}
	}

	//구매흥정 제시가격 체크 상대단가 대비 115% 초과시
	function fn_bargainPriceChkBuy(gridObj,cellName,rowid,otherProposePice,proposePrice,proposeGbn){
		if(Math.round(parseInt(fn_rCom(otherProposePice))*115/100)<parseInt(proposePrice)){
			var strAlert="";
			
			if(proposeGbn=="propose"){
				strAlert="提示单价";
			}
			else{
				strAlert="修正单价";
			}
			
			alert(strAlert+" 太高");
			
			$(gridObj).setCell(rowid,cellName,0);
		}
	}