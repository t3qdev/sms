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
		<span>P/O확정</span>
	</h1>
<form method="POST" action="${web_ctx}/orderPOSave.do" id="orderPOForm" name="orderPOForm">    
    <div class="po_area">
    	<div class="col6">
			<div>
            	<table class="order_table2 tar">
                    <colgroup>
                    <col style="111" />
                    <col width="130" />
                    <col width="160" />
                    <col width="154" />
                    <col width="189" />
                    <col width="192" />
                    </colgroup>
                    <tbody>
                    <tr>
                    <th>P/O총금액<br>(USD)</th>
                    <th>P/O총금액<br>(KRW)</th>
                    <th>매입합계<br>(VAT포함)</th>
                    <th>매입합계<br>(VAT제외)</th>
                    <th>물류비+매입합계<br>(VAT포함)</th>
                    <th>물류비+매입합계<br>(VAT제외)</th>
                    </tr>
                    <tr>
                    <input type="hidden" id="poRegrEml" name="poRegrEml" value="${user.username }"/>
                   		<td>${poVo.poAmt }</td>
                   		<input type="hidden" id="poAmt" name="poAmt" value="${poVo.poAmt}">
                    	<td>${poVo.poXchrAmt }</td>
                    	<input type="hidden" id="poXchrAmt" name="poXchrAmt" value="${poVo.poXchrAmt}">
                    	<td>${poVo.pcSum }</td>
                    	<input type="hidden" id="pcSum" name="pcSum" value="${poVo.pcSum}">
                    	<td>${poVo.pcSumNoVat }</td>
                    	<input type="hidden" id="pcSumNoVat" name="pcSumNoVat" value="${poVo.pcSumNoVat}">
                    	<td>${poVo.dlvPcSum }</td>
                    	<input type="hidden" id="dlvPcSum" name="dlvPcSum" value="${poVo.dlvPcSum}">
                    	<td>${poVo.dlvPcSumNoVat }</td>
                    	<input type="hidden" id="dlvPcSumNoVat" name="dlvPcSumNoVat" value="${poVo.dlvPcSumNoVat}">
                    	
<!--                     <td><span title="환율정보&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222">$16,659.12</span></td> -->
<!--                     <td><span title="환율정보&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222">₩19,424,534</span></td> -->
<!--                     <td><span title="환율정보&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222">₩19,214,584</span></td> -->
<!--                     <td><span title="환율정보&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222">₩17,509,840</span></td> -->
<!--                     <td><span title="환율정보&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222">₩19,514,584</span></td> -->
<!--                     <td><span title="환율정보&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222">₩17,809,840</span></td> -->
                    </tr>
                    <tr>
                    <th colspan="2" rowspan="2">물류비 (KRW)</th>
                    <th colspan="2">수익률 (물류비 제외) </th>
                    <th colspan="2">수익률 (물류비 포함)</th>
                    </tr>
                    <tr>
                    <th>VAT포함</th>
                    <th>VAT제외</th>
                    <th>VAT포함</th>
                    <th>VAT제외</th>
                    </tr>
                    <tr>
                    	<td colspan="2">${poVo.dlvAmt }</td>
                    	<input type="hidden" id="dlvAmt" name="dlvAmt" value="${poVo.dlvAmt}">
                    	<td>${poVo.pf }</td>
                    	<input type="hidden" id="pf" name="pf" value="${poVo.pf}">
                    	<td>${poVo.pfNoVat }</td>
                    	<input type="hidden" id="pfNoVat" name="pfNoVat" value="${poVo.pfNoVat}">
                    	<td>${poVo.pfDlvAmt }</td>
                    	<input type="hidden" id="pfDlvAmt" name="pfDlvAmt" value="${poVo.pfDlvAmt}">
                    	<td>${poVo.pfDlvAmtNoVat }</td>
                    	<input type="hidden" id="pfDlvAmtNoVat" name="pfDlvAmtNoVat" value="${poVo.pfDlvAmtNoVat}">
                    	
<!--                     <td colspan="2"><span title="환율정보&#10;$42,208.00&#10;￦321,312,222&#10;￥321,312,222">₩300,000</span></td> -->
<!--                     <td>1.08%</td> -->
<!--                     <td>9.86%</td> -->
<!--                     <td>-0.46%</td> -->
<!--                     <td>8.31%</td> -->
                    </tr>
                    <tr>
                  	  <th colspan="6">비 고</th>
                    </tr>
                    <tr>
                 	   <td colspan="6">${poVo.poMemoCont }</td>
                 	   <input type="hidden" id="poMemoCont" name="poMemoCont" value="${poVo.poMemoCont}">
                    </tr>
                    
                    <tbody>
                </table>
            </div>
        </div>
        <div class="col3">
			<div>
            	<table class="order_table2 tac">
                    <colgroup>
                    <col width="105">
                    <col width="132">
                    </colgroup>
                    <tbody>
                    <tr>
                    <th>Order. NO</th>
                    	<td>${poVo.ordNo} </td>
                    	<input type="hidden" id="ordNo" name="ordNo" value="${poVo.ordNo}">
                    </tr>
                    <tr>
                    <th>P/O. NO</th>
                    	<td>${poVo.poNo}</td>
                    	<input type="hidden" id="poNo" name="poNo" value="${poVo.poNo}">
                    </tr>
                    <tr>
                    <th>클라이언트</th>
                    	<td>${poVo.custId}</td>
                    	<input type="hidden" id="custId" name="custId" value="${poVo.custId}">
                    </tr>
                    <tr>
                    <th>기준환율</th>
                    	<td>${poVo.stdXchrAmt}</td>
                    	<input type="hidden" id="stdXchrAmt" name="stdXchrAmt" value="${poVo.stdXchrAmt}">
                    </tr>
                    <tr>
                    <th>견적화폐</th>
                    	<td>${poVo.stdXchrKindCd}</td>
                    	<input type="hidden" id="stdXchrKindCd" name="stdXchrKindCd" value="${poVo.stdXchrKindCd}">
                    </tr>
                    <tr>
                    <th>견적조건</th>
                    	<td>${poVo.dlvModeCd}</td>
                    	<input type="hidden" id="dlvModeCd" name="dlvModeCd" value="${poVo.dlvModeCd}">
                    </tr>
                    <tr>
                    <th>P/O일자</th>
                    	<td>${poVo.poDt}</td>
                    	<input type="hidden" id="poDt" name="poDt" value="${poVo.poDt}">
                    </tr>
                    <tr>
                    <th>상품인도일자</th>
                    	<td>${poVo.ordArvlDt}</td>
                    	<input type="hidden" id="ordArvlDt" name="ordArvlDt" value="${poVo.ordArvlDt}">
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
	<div class="ui-layout-single">
        <section>
			<div class="ui-layout-single">
                <table class="order_table2">
                    <colgroup>
                    <col width="111" />
                    <col width="114" />
                    <col width="160" />
                    <col width="154" />
                    <col width="70" />
                    <col width="80" />
                    <col width="100" />
                    <col width="113" />
                    <col width="116" />
                    <col width="116" />
                    <col width="105" />
                    <col width="132" />
                    <col width="132" />
                    <col width="132" />
                    </colgroup>
                    <tbody>
                    <tr>
                    <th>이미지</th>
                    <th>바코드</th>
                    <th>상품명(中)</th>
                    <th>상품명(한)</th>
                    <th>주문수량</th>
                    <th>인박스수량</th>
                    <th>부가세 적용여부</th>
                    <th>매입단가 (KRW)</th>
                    <th>매입합계 (VAT포함)</th>
                    <th>매입합계 (VAT제외)</th>
                    <th>P/O단가 (USD)</th>
                    <th>P/O합계 (USD)</th>
                    <th>P/O단가 (KRW)</th>
                    <th>P/O합계 (KRW)</th>
                    </tr>
                    
                    <input type="hidden" id="gudsCnt" name="gudsCnt" value="${gudsCnt }">
                   <c:forEach var="poGuds" items="${poGudsList }">
                   	<tr>
	                    <td class="tac"><div><img src="${web_ctx}/orderDetailFileDownload.do?filePath=${poGuds.imgSrcPath}" alt="" width="60" height="60" /></div></td>
	                  <input type="hidden" id="imgSrcPath" name="imgSrcPath" value="${poGuds.imgSrcPath}">
	             
	                    <td class="tac"><div>${poGuds.gudsUpcId }</div></td>
	                    <input type="hidden" id="gudsUpcId" name="gudsUpcId" value="${poGuds.gudsUpcId}">
	                    <td><div>${poGuds.gudsCnsNm }</div></td>
	                    <input type="hidden" id="gudsCnsNm" name="gudsCnsNm" value="${poGuds.gudsCnsNm}">
	                    <td><div>${poGuds.gudsKorNm }</div></td>
	                    <input type="hidden" id="gudsKorNm" name="gudsKorNm" value="${poGuds.gudsKorNm}">
	                    <td class="tac"><div>${poGuds.ordGudsQty }</div></td>
	                    <input type="hidden" id="ordGudsQty" name="ordGudsQty" value="${poGuds.ordGudsQty}">
	                    <td class="tar"><div>${poGuds.gudsInbxQty }</div></td>
	                    <input type="hidden" id="gudsInbxQty" name="gudsInbxQty" value="${poGuds.gudsInbxQty}">
	                    
	                    <td class="tar"><div>${poGuds.vatYn } </div></td>	  
	                    <input type="hidden" id="vatYn" name="vatYn" value="${poGuds.vatYn}">                  
	                    <td class="tar"><div>${poGuds.pcPrc } </div></td>
	                    <input type="hidden" id="pcPrc" name="pcPrc" value="${poGuds.pcPrc}">
	                    <td class="tar"><div>${poGuds.pcPrcVat } </div></td>
	                    <input type="hidden" id="pcPrcVat" name="pcPrcVat" value="${poGuds.pcPrcVat}">
	                    <td class="tar"><div>${poGuds.pcPrcNoVat } </div></td>
	                    <input type="hidden" id="pcPrcNoVat" name="pcPrcNoVat" value="${poGuds.pcPrcNoVat}">
	                    <td class="tar"><div>${poGuds.poPrc } </div></td>
	                    <input type="hidden" id="poPrc" name="poPrc" value="${poGuds.poPrc}">
	                   
	                    <td class="tar"><div>${poGuds.poPrcSum }</div></td>
	                    <input type="hidden" id="poPrcSum" name="poPrcSum" value="${poGuds.poPrcSum}">
	                    <td class="tar"><div> ${poGuds.poXchrPrc }</div></td>
	                    <input type="hidden" id="poXchrPrc" name="poXchrPrc" value="${poGuds.poXchrPrc}">	                
	                    <td class="tar"><div>${poGuds.poXchrPrcSum }</div></td>
	                    <input type="hidden" id="poXchrPrcSum" name="poXchrPrcSum" value="${poGuds.poXchrPrcSum}">
	                    
	                    
	                    <input type="hidden" id="pvdrnNm" name="pvdrnNm" value="${poGuds.pvdrnNm}">
	                    <input type="hidden" id="crn" name="crn" value="${poGuds.crn}">
                    </tr>
                   
                   </c:forEach>

                    </tbody>
                    <tfoot>
                    <tr>
                    <th colspan="7">총 계</div></th>
                    <th><div>매입합계 (KRW)</div></th>
                    <th class="tar"><div>${poVo.pcSum }</div></th>
                    <th class="tar"><div>${poVo.pcSumNoVat } </div></th>
                    <th><div>P/O총계 (USD)</div></th>
                    <th class="tar"><div>${poVo.poAmt }</div></th>
                    <th><div>P/O총계 (KRW)</div></th>
                    <th class="tar"><div>${poVo.poXchrAmt }</div></th>
                    </tr>
                    </tfoot>
                </table>
            </div>
            
            
        </section>
    </div>
    
    <div class="ui-layout-single">
        <section>
            <div class="ui-layout-double">
            	<section class="ui-layout-action">
                </section>
                <section class="ui-layout-action">
                    <button class="btn-cancel">취소</button>
                    <button class="btn-submit mr10" id="check">확인</button>
                </section> 
            </div>
        </section>
    </div>
    </form>
</article>

<script>
$(function(){
	//1.취소버튼
	$('.btn-cancel').click(function(){
		//1-1.업로드한 파일/사진/상품이미지등을 삭제한다. (덮어씌워줌으로 반드시 필요 X)
		window.close();			//IE에서 안될경우 window.open("about:blank","_self").close();
	});//end click
	
	
	//버튼핸들링
	//$("#btn_proc02").button( "disable" );
	
	
	//2.확인(po확정)버튼 (파일을 유지하고 업로드한 데이터들을 DB에 넣는다)
	
	if("YES"=="${view}"){
		$("#check").button( "disable" );
	}
	
		$('#check').click(function(){
		var formData = $("#orderPOForm").serialize();
		$.ajax({
			type : "POST",
			url : '${web_ctx}/orderPOSave.do',
			data:	formData,
			async: false,
			cache : false,
			success:function(result){
				alert("성공적으로 저장 되었습니다.");
				
				window.open("about:blank","_self").close();
			}
		});//end $.ajax	

	//	
		
	});//end click
})

</script>
