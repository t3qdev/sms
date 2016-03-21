<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="web_ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!--  Header Include   -->
<!--jsp:include page="layout/header.jsp"/-->

<sec:authentication var="user" property="principal" />


<article>
	<h1>
		<span>详细情报</span>
	</h1>
	
	<div class="ui-layout-single">
        <section>
            <h2><span>Order Number: ${ordNo }</span></h2>
				
            <div class="ui-layout-double">
            
                <section class="ui-layout-action">
                	
                </section>
                <section class="ui-layout-action">

	         	<button  id="btn_01">P/O上传</button> 
               <a class="btn-proc btn_pop" id="btn_proc01" data-href="./orderPOView.do?ordNo=${ordNo }" data-popw="1500" data-poph="800">P/O确认</a>
                    <button class="btn-proc btn_pop" id="btn_proc02" data-href="./orderCalculateView.do?ordNo=${ordNo }" data-popw="570" data-poph="280">结算</button>
                    <a href="" id="poExcelDownload"><button class="btn-excel">采购P/O(excel) 下载</button></a>
                	<a href="" id="excelDownload"><button class="btn-excel mr10">下载订单(excel)</button></a>
                     
                </section>
            </div>
			<div class="ui-layout-single">
			
				<form method="post" action="${web_ctx}/orderDetailSave.do" id="orderDetailSaveForm" name="orderDetailSaveForm">
				<input type="hidden" id="ordstatCd" name="ordstatCd" value="${orderDetail.ordStatCd }">
				<input type="hidden" id="wrtrEml" name="wrtrEml" value="${user.username }"/>
				<input type="hidden" id="ordNo" name="ordNo" value="${ordNo }">
            	<input type="hidden" id="gudsListSize" name="gudsListSize" value="${gudsListSize }">
                <table class="order_table">
                <colgroup>
                <col style="width:11%">
                <col style="width:14%">
                <col style="width:11%">
                <col style="width:14%">
                <col style="width:11%">
                <col style="width:14%">
                <col style="width:11%">
                <col style="width:14%">
                </colgroup>
                <thead>
                	<tr>
                    	<th colspan="8">客户询盘单(${orderDetail.ordNm })</th>
                    </tr>
                </thead>
                
                <tbody>
				
                	<tr>
                    	<th>上海负责人</th>
                        <td><div> <select id="oprCns" name="oprCns" class="selectbox">
                        				<c:forEach var="cnsOpr" items="${cnsOprList}">
											<option  value="${cnsOpr.userEml}">${cnsOpr.userAlasCnsNm}(${cnsOpr.userAlasEngNm})</option>											
										</c:forEach>
						</select> </div></td>
                        <th>韩国负责人</th>
                        <td><div><select id="oprKr" name="oprKr" class="selectbox">
                        				<c:forEach var="krOpr" items="${krOprList}">
											<option  value="${krOpr.userEml}">${krOpr.userAlasCnsNm}(${krOpr.userAlasEngNm})</option>
										</c:forEach>
						</select></div></td>
                        <th>客户</th>
                        <td><div><input type="text" id="custId" name="custId" value="${orderDetail.custId }"></div></td>
                        <th>询盘日期</th>
                        <td><div><input type="text" class="ui-calendar" id="ordReqDt" name="ordReqDt" /></div></td>
                    </tr>
                    <tr>
                    	<th>期望交货日期</th>
                        <td><div><input type="text" class="ui-calendar" id="ordHopeArvlDt" name="ordHopeArvlDt"/></div></td>
                        <th>标准汇率</th>
                        <td><div><input type="text" id="stdXchrAmt" name="stdXchrAmt" value="${orderDetail.stdXchrAmt }"></div></td>
                    	<th>报价货币</th>
                      <td><div><select id="stdXchrKindCd" name="stdXchrKindCd" class="selectbox">
                        				<c:forEach var="stdXchrKindCd" items="${stdXchrKindCdList}">
											<option  value="${stdXchrKindCd.cd}">${stdXchrKindCd.cdVal}</option>
										</c:forEach>
						</select></div></td>
  						<th>付款方式</th>
                        <td><div><input type="text" id="pymtPrvdModeCont" name="pymtPrvdModeCont" value="${orderDetail.pymtPrvdModeCont }"></div></td>
                    </tr>
                    <tr>
                    	<th>报价条款</th>
                        <td><div><select id="dlvModeCd" name="dlvModeCd" class="selectbox">
                        				<c:forEach var="dlvModeCd" items="${dlvModeCdList}">
											<option  value="${dlvModeCd.cd}">${dlvModeCd.cdVal}</option>
										</c:forEach>
						</select></div></td> 
                        <th>港口</th>
                        <td><div><select id="dlvDestCd" name="dlvDestCd" class="selectbox">
                        				<c:forEach var="dlvDestCd" items="${dlvDestCdList}">
											<option  value="${dlvDestCd.cd}">${dlvDestCd.etc} (${dlvDestCd.cdVal})</option>
										</c:forEach>
						</select></div></td> 
						<th>订单日期</th>
                        <td><div><input type="text" class="ui-calendar" id="ordEstmDt" name="ordEstmDt"/></div></td>
                        <th>订单有效日期</th>
                        <td><div><input type="text" class="ui-calendar" id="ordExpDt" name="ordExpDt"/></div></td>                        
                    </tr>
                    <tr>
                    	<th>是否有框架合同</th>
                        <td><div><select id="ctrtTmplYn" name="ctrtTmplYn" class="selectbox">
                        <option value="Y">有</option><option value="N">无</option></select></div></td>
                        <th>是否有样品需求</th>
                        <td><div><select id="smplReqYn" name="smplReqYn" class="selectbox">
                        <option value="Y">有</option><option value="N">无</option></select></div></td>
                    	<th>预估PO日期</th>
                        <td><div><input type="text" class="ui-calendar" id="poSchdDt" name="poSchdDt"/></div></td>
                    	<th>是否有自制需求</th>
                        <td><div><select id="qlfcReqYn" name="qlfcReqYn" class="selectbox">
                        <option value="Y">有</option><option value="N">无</option></select></div></td>
                    </tr>
                    <tr>
                    	<th>客户下单流程</th>
                        <td colspan="7"><div><input type="text" id="custOrdProcCont" name="custOrdProcCont" value="${orderDetail.custOrdProcCont }"></div></td>
                    </tr>
                </tbody>
                

                
                
                
                
                
                
                

                </table>
                <table class="order_table2">
                <colgroup>
                <col style="width:5%">
                <col style="width:12%">
                <col style="width:13%">
                <col style="width:18%">
                <col style="width:10%">
                
                <col style="width:8%">
                <col style="width:8%">
                <col style="width:10%">
                <col style="width:8%">
                <col style="width:5%">
                </colgroup>
                <tbody>
                	<tr>
                    	<th>NO.</th>
                        <th>图片</th>
                        <th>条码</th>
                        <th>商品名称</th>
                        <th>预计需求数量</th>
                        <th>规格</th>
                        <th>价格(单价)</th>
                        <th>装箱数量</th>
                        <th>商品链接</th>
                        <th>搜索</th>
                    </tr>
                      <c:forEach var="smsMsOrdGuds" items="${smsMsOrdGudsList }" varStatus="status">
                      <tr>
                    	<td class="tac">${status.count }</td>
                    	<c:choose>
                    		<c:when test="${smsMsOrdGuds.imgSrcPath ne null }">
                    			<td class="tac"><img src="${web_ctx}/orderDetailFileDownload.do?filePath=${smsMsOrdGuds.imgSrcPath }" id="imgSrcPath_src" name="imgSrcPath_src" width="60" height="60"></td>	
                    		</c:when>
                    		<c:otherwise>
                    			<td class="tac"><img src="" id="imgSrcPath_src"  name="imgSrcPath_src" width="60" height="60"></td>
                    		</c:otherwise>
                    	
                    	</c:choose>
                    	

                    	<input type="hidden" id="imgSrcPath" name="imgSrcPath" value="${smsMsOrdGuds.imgSrcPath}">
                    	<input type="hidden" id="ordGudsSeq" name="ordGudsSeq" value="${smsMsOrdGuds.ordGudsSeq}">
                    	<input type="hidden" id="gudsId" name="gudsId" value="${smsMsOrdGuds.gudsId}">                                	
                        <td><div><input type="text" id="ordGudsUpcId" name="ordGudsUpcId" value="${smsMsOrdGuds.ordGudsUpcId}" readonly></div></td>
                        <td><div><input type="text" id="ordGudsCnsNm" name="ordGudsCnsNm" value="${smsMsOrdGuds.ordGudsCnsNm}"></div></td>
                        <td class="tac"><div><input type="text" id="ordGudsQty" name="ordGudsQty" value="${smsMsOrdGuds.ordGudsQty}" class="tac"></div></td>
                        <td class="tac"><div><input type="text" id="ordGudsSizeVal" name="ordGudsSizeVal" value="${smsMsOrdGuds.ordGudsSizeVal}" class="tac"></div></td>
<%--                         <td class="tar"><div><input type="text" id="ordGudsSalePrc" name="ordGudsSalePrc" value="${smsMsOrdGuds.ordGudsSalePrc}" class="tar" title="환율정보&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222"></div></td> --%>
                        <td class="tar"><div><input type="text" id="ordGudsOrgPrc" name="ordGudsOrgPrc" value="${smsMsOrdGuds.ordGudsOrgPrc}" class="tar" title="汇率&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222"></div></td>
                        <td class="tac"><div><input type="text" id="gudsInbxQty" name="gudsInbxQty" class="tac"  value="${smsMsOrdGuds.gudsInbxQty}" readonly></div></td>
                        <td><div><input type="text" id="ordGudsUrlAddr" name="ordGudsUrlAddr" value="${smsMsOrdGuds.ordGudsUrlAddr}"></div></a></td>
                        
                        <td class="tac"><a href="#" class="btn-search btn_pop" id="mappingBtn" data-href="orderGoodsMappingView.do?gudsNm=${smsMsOrdGuds.ordGudsCnsNm}&UpcId=${smsMsOrdGuds.ordGudsUpcId}&index=${status.index}&ordNo=${smsMsOrdGuds.ordNo }&ordGudsSeq=${smsMsOrdGuds.ordGudsSeq }" data-popw="1000" data-poph="600"></a></td>
                        </tr> 
                    </c:forEach> 

                </tbody>
                <tfoot>
                	<tr>
                    	<th>备注</th>
                        <th colspan="9">
                        	<div>
								<textarea id="ordMemoCont" name="ordMemoCont" form="orderDetailSaveForm">${orderDetail.ordMemoCont }</textarea>
                            </div>
                        </th>
                    </tr>
                </tfoot>
                </table>
                <!-- form -->
            </div>
            
            
        </section>
    </div>
    
    <div class="ui-layout-single">
        <section>
            <div class="ui-layout-double">
            	<section class="ui-layout-action">
                </section>
                <section class="ui-layout-action">
<!--                     <button class="btn-proc btn_pop" id="btn_proc01" data-href="./orderPo.html" data-popw="1300" data-poph="800">P/O확정</button> -->
                    <button class="btn-save mr10" id="detailSave">保存</button>
                </section> 
            </div>
        </section>
    </div>
    </form>
    
    <div class="ui-layout-single">
        <section>
            <h2><span>Proposed Deal</span></h2>
			<div class="ui-layout-single">
            	<section class="ui-layout-form-b">
                	<div class="ml10 tar">
                        <i class="file"><input id="orderFile" name="orderFile" type="file"><em>No file selected...</em></i>
                        <button class="btn-add mr10" id="orderFileUpload">上传</button>
                    </div>
				</section>    
            </div>
            
            <div class="ui-layout-single">
            	<table class="order_table2">
                <colgroup>
                <col style="width:15%">
                <col style="width:15%">
                <col style="width:35%">
                <col style="width:35%">
                </colgroup>
                <tbody>
                	<tr>
                    	<th>日期</th>
                        <th>花名</th>
                        <th>文件名</th>
                        <th>下载</th>
                    </tr>


	
	                   <c:forEach var="file" items="${smsMsOrdFileList}" varStatus="status">
	                  <tr>
	                    	<td class="tac">${file.ordFileRegDttm}</td>
	                        <td class="tac">${file.userAlasCnsNm }(${file.userAlasEngNm })</td>
	                        <c:choose>
		                        <c:when test="${fn:contains(file.ordFileSysFileNm, 'xls')}">
		                        	<td><div><a href="${web_ctx}/orderDetailFileDownload.do?filePath=${file.ordFileSysFileNm }&fileName=${file.ordFileOrgtFileNm }" class="ico ico_xls fileDown">${file.ordFileOrgtFileNm }</a></td>
		                        </c:when>
		                        <c:otherwise>
		                        	<td><div><a href="${web_ctx}/orderDetailFileDownload.do?filePath=${file.ordFileSysFileNm }&fileName=${file.ordFileOrgtFileNm }" class="ico ico_jpg fileDown">${file.ordFileOrgtFileNm }</a></td>
		                        </c:otherwise>
	                        </c:choose>
<%-- 	                        <td><div><a href="${web_ctx}/orderDetailFileDownload.do?filePath=${file.ordFileSysFileNm }&fileName=${file.ordFileOrgtFileNm }" class="ico ico_xls fileDown">${file.ordFileOrgtFileNm }</a></td> --%>
	                        <td class="tac"><a href="${web_ctx}/orderDetailFileDownload.do?filePath=${file.ordFileSysFileNm }&fileName=${file.ordFileOrgtFileNm }" class="btn-download fileDown" id="btnFileDownload${status.index }">다운로드</a></td>
	                  </tr>
	                   </c:forEach> 
	                   
	                    <c:if test="${fn:length(smsMsOrdFileList) ==0}">
		                    <tr>
		                    	<td colspan="4" class="tac nodata">请上传文件。</td>
		                    </tr>
	                   </c:if>
	                   
                </tbody>
                </table>
            </div>
        </section>
    </div>
    
    
    
    
    <div id="dialog_upload" title="P/O文件上传">
		<form id="dialog_upload_form" action="${web_ctx}/orderPOInsert.do?ordNo=${ordNo }&wrtrEml=${user.username }" method="post" enctype="multipart/form-data">
			<section class="ui-layout-form-b">
				<ul>
					<li>
						<label for="">excel样式文件</label>
						<input type="file" name="file">
					</li>
					<li>
						<label for="">图片</label>
						<input type="file" name="file" multiple accept="image/*">
					</li>
					<li>
						<label for=""></label>
						图片可重复登录。
					</li>
				</ul>
			</section>
		</form>
	</div>
    
    
    
    
</article>

<script>




$(function(){
	if("YES" =="${saved}"){
		alert("订单保存成功");
	}
	if("NO"=="${saved}"){
		alert("订单保存失败");
	}
	
	if("YES"=="${reload}"){
		opener.parent.location.reload();
	}
	//팝업페이지 연결
	$(".btn_pop").each(function(){
       $(this).click(function(){
			window.open($(this).data("href"), "po", "scrollbars=yes,width=" + $(this).data("popw") + ",height=" + $(this).data("poph") + ",top=10,left=20");
		}); 
    });
	

	//0.권한 관리를 위한 유저권한 체크
	var roles = new Array();
	<c:forEach var="role" items="${user.authorities }">
		roles.push("${role.name}");
	</c:forEach>
	
	console.log(roles.indexOf("N000580300"));
	
	//버튼별 권한테이블
	var RolesSave = new Array("N000580100","N000580200","N000580300");		//1.저장버튼
	var RolesOrderDown = new Array("N000580100","N000580200","N000580300");		//2.주문상세다운로드
	var RolesPoUpload = new Array("N000580100","N000580300");		//3.po업로드
	var RolesPoCheck = new Array("N000580100","N000580300","N000580400");		//4.po확인
	var RolesPoCalc = new Array("N000580100","N000580400");		//5.po정산
	var RolesPoZip = new Array("N000580100","N000580300");		//6.매입po
	var RolesMapping = new Array("N000580100","N000580200","N000580300");		//7.상품매핑
	var RolesFileDown = new Array("N000580100","N000580300");		//8.첨부파일다운 
	
	
	//권한에 따라 버튼 표시/숨김
	if(!checkIndex(RolesSave,roles)){			//1.저장버튼
		$("#detailSave").hide();	
	}
	if(!checkIndex(RolesOrderDown,roles)){			//2.주문상세다운로드
		$("#excelDownload").hide();	//주문내역 다운로드
	}
	if(!checkIndex(RolesPoUpload,roles)){			//3.po업로드
		$('#btn_01').hide();	//PO업로드
	}
	if(!checkIndex(RolesPoCheck,roles)){			//4.po확인
		$("#btn_proc01").hide();	//PO확인
	}
	if(!checkIndex(RolesPoCalc,roles)){			//5.po정산
		$("#btn_proc02").hide();	//정산
	}
	if(!checkIndex(RolesPoZip,roles)){			//6.매입po
		$('#poExcelDownload').hide();
	}
	if(!checkIndex(RolesSave,roles)){			//7.상품매핑
		$(".btn-search").hide();		
	}
	if(!checkIndex(RolesSave,roles)){			//8.첨부파일다운 
		$("#orderFileUpload").hide();//파일업로드
		$("#orderFile").hide();	//파일선택창
		//$(".fileDown").unbind("click");
		$(".fileDown").click(function(a) {
			a.preventDefault();
			});
		 //$(".fileDown").hide();		//상품 매핑
	}
	
	


	//1.날짜정보는 초기화시 세팅해줘야함
	
	$("#ordReqDt").val(dtToDate("${orderDetail.ordReqDt}"));		//문의일자
	$("#ordHopeArvlDt").val(dtToDate("${orderDetail.ordHopeArvlDt}"));	//희망인도일자
	$("#ordEstmDt").val(dtToDate("${orderDetail.ordEstmDt}"));	//견적일자
	$("#ordExpDt").val(dtToDate("${orderDetail.ordExpDt}"));	//견적유효
	$("#poSchdDt").val(dtToDate("${orderDetail.poSchdDt}"));	//PO예상일자
	
	
	//2.select박스세팅
	$("#oprCns").val("${orderDetail.oprCns}");	//상해담당자
	$("#oprKr").val("${orderDetail.oprKr}");	//한국담당자
	$("#stdXchrKindCd").val("${orderDetail.stdXchrKindCd}");	//기준화폐
	$("#dlvModeCd").val("${orderDetail.dlvModeCd}");	//견적조건
	$("#dlvDestCd").val("${orderDetail.dlvDestCd}");	//항구
	$("#ctrtTmplYn").val("${orderDetail.ctrtTmplYn}");	//계획서템플릿유무
	$("#smplReqYn").val("${orderDetail.smplReqYn}");	//샘플요청 유무
	$("#qlfcReqYn").val("${orderDetail.qlfcReqYn}");	//자격요청 유무
	
	
	//버튼제어를 위한 변수 
	var ordStatCd = '${orderDetail.ordStatCd }';
	
	//버튼핸들링
	if(ordStatCd==''){
		$('#btn_01').hide();	//PO업로드
		$("#btn_proc01").hide();	//PO확인
		$('#poExcelDownload').hide();	//매입PO다운
		$("#btn_proc02").hide();	//정산
		$(".btn-search").hide();		//상품 매핑
		$("#excelDownload").hide();	//주문내역 다운로드
		$("#orderFileUpload").hide();//파일업로드
		$("#orderFile").hide();	//파일선택창
	}
	if(ordStatCd=='N000550100' || ordStatCd=='N000550400' ){
		$('#btn_01').hide();	//PO업로드
	}
	if(ordStatCd=='N000550100' || ordStatCd=='N000550200'){
		$("#btn_proc01").hide();	//PO확인
		$('#poExcelDownload').hide(); 
		$("#btn_proc02").hide();	//정산
		
	}
	if(ordStatCd=='N000550300' || ordStatCd=='N000550400'){
		$("#detailSave").hide();		//detail저장
		$("#mappingBtn").unbind("click"); 	//매핑연결버튼
		$(":text").attr("disabled","true");
		$(".selectbox").attr("disabled","true");
	}
/* 	if('${isSaved}'=='N'){
		$(".btn-search").hide();
	} */
	
		
	
	$('#dialog_upload').dialog({
				modal: true,
				autoOpen: false,
				width: 500,
				height: 240,
				buttons: {
					'上传P/O 相关文件': function(evt){

						$("#dialog_upload_form").attr('target', 'popUp');
						var newWindow=window.open("",'popUp',1500,800);
						$("#dialog_upload_form").submit();
						  $('#dialog_upload').dialog('close');
						//$('#dialog_upload_form').submit();
					}
				}
			});
	
		$('#btn_01').click(function(){dialog_upload_form
				//$('#dialog_upload').find('input[name=file]').val('');
				$('#dialog_upload').dialog('open');
		})
				
	//주문관련 파일 업로드 
	$('#orderFileUpload').click(function(){
		
		var formData = new FormData();
		$.each($('#orderFile')[0].files, function(i, file) {
			formData.append('file-' + i, file);
		});
		var wrtr = $('#wrtrEml').val();
		var ordNo=$('#ordNo').val();
		formData.append("wrtrEml",wrtr);
		formData.append("ordNo",ordNo);
				
		$.ajax({
			type : "POST",
			url : "${web_ctx}/orderDetailFileUpload.do",
			data : formData,
			async: false,
			processData : false,
			contentType : false,
			cache : false,
			success : function(result) {
				if(result=="success"){
					alert("上传成功");
					window.location.reload(true);
				}else{
					alert("上传失败");
				}
					
				
				
			}//success function
/* 			,error : function(jqXHR, textStatus, errorThrown) {
				//Error시, 처리
				alert("上传文件失败");
			} */
		});//ajax
	});
	
	//order Excel 파일 다운로드
	$("#excelDownload").click(function(){
		$('#excelDownload').attr("href","${web_ctx}/downloadExcel_Order.do?ordNo="+$('#ordNo').val());
	});
	
	//PO Excel 파일 다운로드
	$("#poExcelDownload").click(function(){
		$('#poExcelDownload').attr("href","${web_ctx}/downloadExcel_PO.do?ordNo="+$('#ordNo').val());
	});
	
	
	
	
})//end function

//20170121 => 2017-01-21 
function dtToDate(dt){
	var date;
	if(dt.length==8){
		date=dt.substr(0,4)+'-'+dt.substr(4,2)+'-'+dt.substr(6,2);
		return date;
	}else{
		return dt;
	}
}
//2017-01-21 => 20170121
function dateToDt(date){
	var dt;
	dt=date.replace(/-/gi,"");
	return dt;
}

//array와 array를 비교해여 해당값이 존재하는지 체크 
function checkIndex(btnRole, userRole){
	var flag=false;
	btnRole.forEach(function(role){
			if(userRole.indexOf(role)!=-1){
				flag=true;
				return true;
			}
		});
	return flag;
}

</script>

