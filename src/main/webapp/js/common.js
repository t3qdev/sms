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
	$('.autofinder').combobox();
	
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
	
});