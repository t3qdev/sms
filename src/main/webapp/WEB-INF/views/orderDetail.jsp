<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="web_ctx" value="${pageContext.request.contextPath}" />

<!--  Header Include   -->
<!--jsp:include page="layout/header.jsp"/-->

<sec:authentication var="user" property="principal" />


<article>
	<h1>
		<span>상세보기</span>
	</h1>
	
	<div class="ui-layout-single">
        <section>
            <h2><span>Order Number: ${ordNo }</span></h2>
				
            <div class="ui-layout-double">
                <section class="ui-layout-action">
                	
                </section>
                <section class="ui-layout-action">

	         	<button class="mr10" id="btn_01">P/O업로드</button>
                <a class="btn-proc ml10" id="btn_proc01" href="./orderPOView.do?ordNo=${ordNo }" target="_blank">P/O확인</a>
                    <button class="btn-proc btn_pop" id="btn_proc02" data-href="./orderCalculateView.do?ordNo=${ordNo }" data-popw="570" data-poph="280">정산</button>
                	<a href="" id="excelDownload"><button class="btn-excel mr10">order Excel 다운받기</button></a>
                    
                </section>
            </div>
			<div class="ui-layout-single">
			
				<form method="post" action="${web_ctx}/orderDetailSave.do" id="orderDetailSaveForm" name="orderDetailSaveForm">
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
                    	<th colspan="8">클라이언트 견적문의서(${orderDetail.ordNm })</th>
                    </tr>
                </thead>
                
                <tbody>
				
                	<tr>
                    	<th>상해담당자</th>
                        <td><div> <select id="oprCns" name="oprCns">
                        				<c:forEach var="cnsOpr" items="${cnsOprList}">
											<option  value="${cnsOpr.userEml}">${cnsOpr.userAlasCnsNm}(${cnsOpr.userAlasEngNm})</option>											
										</c:forEach>
						</select> </div></td>
                        <th>한국담당자</th>
                        <td><div><select id="oprKr" name="oprKr">
                        				<c:forEach var="krOpr" items="${krOprList}">
											<option  value="${krOpr.userEml}">${krOpr.userAlasCnsNm}(${krOpr.userAlasEngNm})</option>
										</c:forEach>
						</select></div></td>
                        <th>클라이언트</th>
                        <td><div><input type="text" id="custId" name="custId" value="${orderDetail.custId }"></div></td>
                        <th>문의일자</th>
                        <td><div><input type="text" class="ui-calendar" id="ordReqDt" name="ordReqDt" /></div></td>
                    </tr>
                    <tr>
                    	<th>희망인도일자</th>
                        <td><div><input type="text" class="ui-calendar" id="ordHopeArvlDt" name="ordHopeArvlDt"/></div></td>
                        <th>기준환율</th>
                        <td><div><input type="text" id="stdXchrAmt" name="stdXchrAmt" value="${orderDetail.stdXchrAmt }"></div></td>
                    	<th>기준화페</th>
                      <td><div><select id="stdXchrKindCd" name="stdXchrKindCd">
                        				<c:forEach var="stdXchrKindCd" items="${stdXchrKindCdList}">
											<option  value="${stdXchrKindCd.cd}">${stdXchrKindCd.cdVal}</option>
										</c:forEach>
						</select></div></td>
  						<th>대금지불방식</th>
                        <td><div><input type="text" id="pymtPrvdModeCont" name="pymtPrvdModeCont" value="${orderDetail.pymtPrvdModeCont }"></div></td>
                    </tr>
                    <tr>
                    	<th>견적조건</th>
                        <td><div><select id="dlvModeCd" name="dlvModeCd">
                        				<c:forEach var="dlvModeCd" items="${dlvModeCdList}">
											<option  value="${dlvModeCd.cd}">${dlvModeCd.cdVal}</option>
										</c:forEach>
						</select></div></td> 
                        <th>항구</th>
                        <td><div><select id="dlvDestCd" name="dlvDestCd">
                        				<c:forEach var="dlvDestCd" items="${dlvDestCdList}">
											<option  value="${dlvDestCd.cd}">${dlvDestCd.cdVal}</option>
										</c:forEach>
						</select></div></td> 
						<th>견적일자</th>
                        <td><div><input type="text" class="ui-calendar" id="ordEstmDt" name="ordEstmDt"/></div></td>
                        <th>견적유효</th>
                        <td><div><input type="text" class="ui-calendar" id="ordExpDt" name="ordExpDt"/></div></td>                        
                    </tr>
                    <tr>
                    	<th>계약서 템플릿 유무</th>
                        <td><div><select id="ctrtTmplYn" name="ctrtTmplYn">
                        <option value="Y">유</option><option value="N">무</option></select></div></td>
                        <th>샘플요청유무</th>
                        <td><div><select id="smplReqYn" name="smplReqYn">
                        <option value="Y">유</option><option value="N">무</option></select></div></td>
                    	<th>PO예상일자</th>
                        <td><div><input type="text" class="ui-calendar" id="poSchdDt" name="poSchdDt"/></div></td>
                    	<th>자격요청유무</th>
                        <td><div><select id="qlfcReqYn" name="qlfcReqYn">
                        <option value="Y">유</option><option value="N">무</option></select></div></td>
                    </tr>
                    <tr>
                    	<th>주문프로세스</th>
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
                        <th>이미지</th>
                        <th>바코드</th>
                        <th>상품명</th>
                        <th>예상요청수량</th>
                        <th>규격</th>
                        <th>가격(단가)</th>
                        <th>인박스수량</th>
                        <th>상품링크</th>
                        <th>검색</th>
                    </tr>
                      <c:forEach var="smsMsOrdGuds" items="${smsMsOrdGudsList }" varStatus="status">
                      <tr>
                    	<td class="tac">${status.count }</td>
                    	<td class="tac"><img src="${web_ctx}/orderDetailFileDownload.do?filePath=${smsMsOrdGuds.imgSrcPath }" id="imgSrcPath_src"  width="60" height="60"></td>
                    	<input type="hidden" id="imgSrcPath" name="imgSrcPath" value="${smsMsOrdGuds.imgSrcPath}">
                    	<input type="hidden" id="ordGudsSeq" name="ordGudsSeq" value="${smsMsOrdGuds.ordGudsSeq}">
                    	<input type="hidden" id="gudsId" name="gudsId" value="${smsMsOrdGuds.gudsId}">                                	
                        <td><div><input type="text" id="ordGudsUpcId" name="ordGudsUpcId" value="${smsMsOrdGuds.ordGudsUpcId}"></div></td>
                        <td><div><input type="text" id="ordGudsCnsNm" name="ordGudsCnsNm" value="${smsMsOrdGuds.ordGudsCnsNm}"></div></td>
                        <td class="tac"><div><input type="text" id="ordGudsQty" name="ordGudsQty" value="${smsMsOrdGuds.ordGudsQty}" class="tac"></div></td>
                        <td class="tac"><div><input type="text" id="ordGudsSizeVal" name="ordGudsSizeVal" value="${smsMsOrdGuds.ordGudsSizeVal}" class="tac"></div></td>
                        <td class="tar"><div><input type="text" id="ordGudsSalePrc" name="ordGudsSalePrc" value="${smsMsOrdGuds.ordGudsSalePrc}" class="tar" title="환율정보&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222"></div></td>
<%--                         <td class="tar"><div><input type="text" id="ordGudsOrgPrc" name="ordGudsOrgPrc" value="${smsMsOrdGuds.ordGudsOrgPrc}" class="tar" title="환율정보&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222"></div></td> --%>
                        <td class="tac"><div><input type="text" id="gudsInbxQty" name="gudsInbxQty" class="tac"  value="${smsMsOrdGuds.gudsInbxQty}"></div></td>
                        <td><div><input type="text" id="ordGudsUrlAddr" name="ordGudsUrlAddr" value="${smsMsOrdGuds.ordGudsUrlAddr}"></div></a></td>
                        <td class="tac"><a href="#" class="btn-search btn_pop" data-href="orderGoodsMappingView.do?gudsNm=${smsMsOrdGuds.ordGudsCnsNm}&UpcId=${smsMsOrdGuds.ordGudsUpcId}&index=${status.index}&ordNo=${smsMsOrdGuds.ordNo }&ordGudsSeq=${smsMsOrdGuds.ordGudsSeq }" data-popw="1000" data-poph="600"></a></td>
                        </tr> 
                    </c:forEach> 

                </tbody>
                <tfoot>
                	<tr>
                    	<th>비고</th>
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
                    <button class="btn-save mr10" >저장</button>
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
                        <button class="btn-add mr10" id="orderFileUpload">등록</button>
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
                    	<th>날짜</th>
                        <th>화명</th>
                        <th>파일명</th>
                        <th>다운로드</th>
                    </tr>


	
	                   <c:forEach var="file" items="${smsMsOrdFileList}" varStatus="status">
	                  <tr>
	                    	<td class="tac">${file.ordFileRegDttm}</td>
	                        <td class="tac">${file.userAlasCnsNm }(${file.userAlasEngNm })</td>
	                        <td><div><a href="${web_ctx}/orderDetailFileDownload.do?filePath=${file.ordFileSysFileNm }&fileName=${file.ordFileOrgtFileNm }" class="ico ico_xls">${file.ordFileOrgtFileNm }</a></td>
	                        <td class="tac"><a href="${web_ctx}/orderDetailFileDownload.do?filePath=${file.ordFileSysFileNm }&fileName=${file.ordFileOrgtFileNm }" class="btn-download" id="btnFileDownload${status.index }">다운로드</a></td>
	                  </tr>
	                   </c:forEach> 
                </table>
            </div>
        </section>
    </div>
    
    
    
    
    <div id="dialog_upload" title="P/O파일 업로드">
		<form id="dialog_upload_form" action="${web_ctx}/orderPOInsert.do" method="post" enctype="multipart/form-data">
			<section class="ui-layout-form-b">
				<ul>
					<li>
						<label for="">엑셀 양식 파일</label>
						<input type="file" name="file">
					</li>
					<li>
						<label for="">이미지 파일</label>
						<input type="file" name="file" multiple accept="image/*">
					</li>
					<li>
						<label for=""></label>
						이미지 파일은 복수 등록이 가능합니다.
					</li>
				</ul>
			</section>
		</form>
	</div>
    
    
    
    
</article>

<script>




$(function(){
	
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
	
	
	
	//버튼핸들링
	//$("#btn_proc02").button( "disable" );
	
	
	$('#dialog_upload').dialog({
				modal: true,
				autoOpen: false,
				width: 500,
				height: 240,
				buttons: {
					'P/O 관련 파일 업로드': function(evt){
					/* 	$(evt.target).button({
							disabled : true
						}); */
						  $("#dialog_upload_form").attr('target', '_blank').submit();
						  $('#dialog_upload').dialog('close');
						//$('#dialog_upload_form').submit();
					}
				}
			});
	
		$('#btn_01').click(function(){
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
				
		alert("clicked");
		$.ajax({
			type : "POST",
			url : "${web_ctx}/orderDetailFileUpload.do",
			data : formData,
			async: false,
			processData : false,
			contentType : false,
			cache : false,
			success : function(result) {
				console.log(result);
				console.log(result.savedRealFileNm);
				console.log(result.savedFileNm);
					
				alert("파일 업로드 성공 ");
				window.location.reload(true);
			}//success function
			,
			error : function(jqXHR, textStatus, errorThrown) {
				//Error시, 처리
				alert("파일업로드 에러");
			}
		});//ajax
	});
	
	//order Excel 파일 다운로드
	$("#excelDownload").click(function(){
		$('#excelDownload').attr("href","${web_ctx}/downloadExcel_Order.do?ordNo="+$('#ordNo').val());
	});
	
	//팝업페이지 연결
	$(".btn_pop").each(function(){
       $(this).click(function(){
			window.open($(this).data("href"), "po", "scrollbars=yes,width=" + $(this).data("popw") + ",height=" + $(this).data("poph") + ",top=10,left=20");
		}); 
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

</script>

