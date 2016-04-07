$("html").addClass(localStorage.sms2Theme);

$(function(){
	$('button').button();
	
	$('.btn-search').button({ icons: { primary: "ui-icon-search" } });
	$('.btn-reset').button({ icons: { primary: "ui-icon-refresh" } });
	$('.btn-undo').button({ icons: { primary: "ui-icon-arrowreturnthick-1-w" } });
	$('.btn-add').button({ icons: { primary: "ui-icon-plus" } });
	$('.btn-excel').button({ icons: { primary: "ui-icon-document" } });
	$('.btn-submit').button({ icons: { primary: "ui-icon-check" } });
	$('.btn-del').button({ icons: { primary: "ui-icon-trash" } });
	$('.btn-del-i').button({ icons: { primary: "ui-icon-close"} ,text: false  });
	$('.btn-banner').button({ icons: { primary: "ui-icon-image"} ,text: false  });
	$('.btn-set').button({ icons: { primary: "ui-icon-gear"} });
	$('.btn-cancel').button({ icons: { primary: "ui-icon-close"} });
	$('.btn-submit').button({ icons: { primary: "ui-icon-check"} });
	$('.btn-save').button({ icons: { primary: "ui-icon-disk"} });
	$('.btn-download').button({ icons: { primary: "ui-icon-disk"} });
	$('.btn-proc').button();
	
	$('.ui-selector').selectmenu();
	
	$('.ui-calendar').datepicker();
	$('.ui-calendar').datepicker('option', 'dateFormat', 'yy-mm-dd' );
	
	$('body').on("change","i.file input",function(){
		$(this).parent().find("em").html($(this).val());
	});
	
	$('.sms_theme li a').each(function(){
		$(this).click(function(){
			localStorage.sms2Theme = 'theme_'+$(this).data('sms2theme');
			$("html").attr('class',localStorage.sms2Theme)
		});
	});
	
	//ajax default Setting
	$.ajaxSetup({
	   error: function(jqXHR, textStatus, errorThrown ){
	    	var statusCode = jqXHR.status;
	    	if(statusCode === 500) {
	    		alert("An error occurred!");
	    		var jsonObj =$.parseJSON(jqXHR.responseText);
	    		$('<form action="/common/ajaxError.jsp" method="POST">' + 
	    				'<input type="hidden" name="error" value="' + jsonObj.error + '">' +
	    				'<input type="hidden" name="message" value="' + jsonObj.stackTrace + '">' +
	    		'</form>').appendTo('body').submit();
	    	} else if(statusCode === 401) {
	    		alert("Unauthorized! It will be redirected login page.");
	    		location.href = "/";
	    	} else if(statusCode === 403) {
	    		alert("Unauthorized! It will be redirected login page.");
	    		location.href = "/";
	    	}
	    }
	});
	
});