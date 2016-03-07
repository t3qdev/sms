function fnMailSend(){
	var objFrm = document.frm;
		
	if(!confirm("이메일를 발송하시겠습니까?")){
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
	alert("이메일이 발송되었습니다.");
	window.close();
}

function fnStatmentSend(){
	var objFrm = document.frm;
	
	if(!confirm("팩스를 발송하시겠습니까?")){
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
		
	if(!confirm("인쇄하시겠습니까?")){
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
	alert("팩스가 발송되었습니다.");
	window.close();
}