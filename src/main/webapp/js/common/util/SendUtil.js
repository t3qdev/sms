function fnMailSend(){
	var objFrm = document.frm;
		
	if(!confirm("确定要发送邮箱？")){
		return;
	}

	$("#btn_email").hide();
	$("#btn_fax").hide();
	
	$("#html_source").val((document.documentElement.outerHTML).replace(/<img src=/gi, "<img src2="));
	
	var option = {
			url: "emailSend.do",
			callbackFn: fnMailCallback,
			data: $(objFrm).serialize()
	};
		
	commonUtil.fnAjaxCall(option);
}

function fnMailCallback(){
	alert("邮箱已发送");
	window.close();
}

function fnStatmentSend(){
	var objFrm = document.frm;
	
	if(!confirm("确定要发送传真？")){
		return;
	}
	
	$("#btn_email").hide();
	$("#btn_fax").hide();
	
	$("#html_source").val((document.documentElement.outerHTML).replace(/<img src=/gi, "<img src2="));
	
	var option = {
			url: "statementSend.do",
			callbackFn: fnStatmentCallback,
			data: $(objFrm).serialize()
	};
		
	commonUtil.fnAjaxCall(option);
	
}

function fnDeliveryPrint(){
		
	if(!confirm("确定要打印？")){
		return;
	}

	$("#confirm").hide();
	$("#deliveryPrint").hide();
	
	$("#html_source").val((document.documentElement.outerHTML).replace(/<img src=/gi, "<img src2="));
	
	window.print();

	$("#confirm").show();
	$("#deliveryPrint").show();
}

function fnStatmentCallback(){
	alert("传真已发送");
	window.close();
}