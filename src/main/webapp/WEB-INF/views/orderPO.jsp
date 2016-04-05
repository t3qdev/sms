<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="web_ctx" value="${pageContext.request.contextPath}" />
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!--  Header Include   -->
<!--jsp:include page="layout/header.jsp"/-->
<sec:authentication var="user" property="principal" />
<style>
body{min-width:1900px}
</style>
<article>

	<h1>
		<span>P/O确定</span>
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
                    <th>P/O总金额<br>(USD)</th>
                    <th>P/O总金额<br>(KRW)</th>
                    <th>采购合计<br>(包含VAT)</th>
                    <th>采购合计<br>(VAT除外)</th>
                    <th>物流费+采购合计<br>(包含VAT)</th>
                    <th>物流费+采购合计<br>(VAT除外)</th>
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
                    <th colspan="2" rowspan="2">物流费 (KRW)</th>
                    <th colspan="2">收益率 (物流费除外)</th>
                    <th colspan="2">收益率 (包含物流费)</th>
                    </tr>
                    <tr>
                    <th>包含VAT</th>
                    <th>VAT除外</th>
                    <th>包含VAT</th>
                    <th>VAT除外</th>
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
                  	  <th colspan="6">备注</th>
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
                    <th>客户名称</th>
                    	<td>${poVo.custId}</td>
                    	<input type="hidden" id="custId" name="custId" value="${poVo.custId}">
                    </tr>
                    <tr>
                    <th>标准汇率</th>
                    	<td>${poVo.stdXchrAmt}</td>
              	<input type="hidden" id="stdXchrAmt" name="stdXchrAmt" value="${poVo.stdXchrAmt}">
                    </tr>
                    <tr>
                    <th>报价货币</th>
                    	<td>${poVo.stdXchrKindCd}</td>
                    	<input type="hidden" id="stdXchrKindCd" name="stdXchrKindCd" value="${poVo.stdXchrKindCd}">
                    </tr>
                    <tr>
                    <th>报价条件</th>
                    	<td>${poVo.dlvModeCd}</td>
                    	<input type="hidden" id="dlvModeCd" name="dlvModeCd" value="${poVo.dlvModeCd}">
                    </tr>
                    <tr>
                    <th>P/O日期</th>
                    	<td>${poVo.poDt}</td>
                    	<input type="hidden" id="poDt" name="poDt" value="${poVo.poDt}">
                    </tr>
                    <tr>
                    <th>提货日期</th>
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
                    <th>图片</th>
                    <th>条码</th>
                    <th>商品名(中)</th>
                    <th>商品名(韩)</th>
                    <th>订购数量</th>
                    <th>装箱数量</th>
                    <th>是否包含VAT</th>
                    <th>采购单价 (KRW)</th>
                    <th>采购合计 (包含VAT)</th>
                    <th>采购合计 (VAT除外)</th>
                    <th>P/O单价 (USD)</th>
                    <th>P/O合计 (USD)</th>
                    <th>P/O单价 (KRW)</th>
                    <th>P/O合计 (KRW)</th>
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
                    <th colspan="7">总计</div></th>
                    <th><div>采购合计 (KRW)</div></th>
                    <th class="tar"><div>${poVo.pcSum }</div></th>
                    <th class="tar"><div>${poVo.pcSumNoVat } </div></th>
                    <th><div>P/O总计 (USD)</div></th>
                    <th class="tar"><div>${poVo.poAmt }</div></th>
                    <th><div>P/O总计 (KRW)</div></th>
                    <th class="tar"><div>${poVo.poXchrAmt }</div></th>
                    </tr>
                    </tfoot>
                </table>
            </div>
            <c:forEach var="fileResult" items="${fileResultList }">
            	<input type="hidden" id="savedFileNm" name="savedFileNm" value="${fileResult.savedFileNm }">
            	<input type="hidden" id="savedRealFileNm" name="savedRealFileNm" value="${fileResult.savedRealFileNm }">
            </c:forEach>
            
        </section>
    </div>
    
    <div class="ui-layout-single">
        <section>
            <div class="ui-layout-double">
            	<section class="ui-layout-action">
                </section>
                <section class="ui-layout-action">
                	
                    <button class="btn-cancel">取消</button>
                    <button class="btn-submit" id="check">确认</button>
                </section> 
            </div>
        </section>
    </div>
    </form>
</article>

<script>
$(function(){
	
	//각값들을 unformat해서 hidden에 넣어둔다
	
	$('#poAmt').val(unformatterAmt($('#poAmt').val()));
	$('#poXchrAmt').val(unformatterAmt($('#poXchrAmt').val()));
	$('#pcSum').val(unformatterAmt($('#pcSum').val()));
	$('#pcSumNoVat').val(unformatterAmt($('#pcSumNoVat').val()));
	$('#dlvPcSum').val(unformatterAmt($('#dlvPcSum').val()));
	
	$('#dlvPcSumNoVat').val(unformatterAmt($('#dlvPcSumNoVat').val()));
	$('#dlvAmt').val(unformatterAmt($('#dlvAmt').val()));
	$('#stdXchrAmt').val(unformatterAmt($('#stdXchrAmt').val()));
	
	
	
	
 	

	for(i=0; i<$("#gudsCnt").val(); i++){
		$('input[name="pcPrc"]').eq(i).val(unformatterAmt($('input[name="pcPrc"]').eq(i).val()));
		$('input[name="pcPrcVat"]').eq(i).val(unformatterAmt($('input[name="pcPrcVat"]').eq(i).val()));
		$('input[name="pcPrcNoVat"]').eq(i).val(unformatterAmt($('input[name="pcPrcNoVat"]').eq(i).val()));
		$('input[name="poPrc"]').eq(i).val(unformatterAmt($('input[name="poPrc"]').eq(i).val()));
		$('input[name="poPrcSum"]').eq(i).val(unformatterAmt($('input[name="poPrcSum"]').eq(i).val()));
		$('input[name="poXchrPrc"]').eq(i).val(unformatterAmt($('input[name="poXchrPrc"]').eq(i).val()));
		$('input[name="poXchrPrcSum"]').eq(i).val(unformatterAmt($('input[name="poXchrPrcSum"]').eq(i).val()));
		
	}
	

	//1.취소버튼
	$('.btn-cancel').click(function(){
		//1-1.업로드한 파일/사진/상품이미지등을 삭제한다. (덮어씌워줌으로 반드시 필요 X)
		window.close();			//IE에서 안될경우 window.open("about:blank","_self").close();
	});//end click
	
	
	//버튼핸들링
	//$("#btn_proc02").button( "disable" );
	
	
	//2.확인(po확정)버튼 (파일을 유지하고 업로드한 데이터들을 DB에 넣는다)
	
	if("YES"=="${view}"){
		$("#check").hide();
	}
	if("YES"=="${invaild}"){
		$("#check").hide();
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
				if(result=="success"){
					alert("PO确定");
					if(opener.parent && !opener.parent.closed){
						opener.parent.location.reload();
						if(opener.opener.parent && !opener.opener.parent.closed && opener.opener.parent.reLoadJqgrid){
//								opener.opener.parent.location.reload();
							opener.opener.parent.reLoadJqgrid();
						}
					}
					window.open("about:blank","_self").close();
				}else{
					alert("PO确定失败");
					window.open("about:blank","_self").close();
				}					
			}
		});//end $.ajax	

	//	
		
	});//end click
})

//use -DecimalFormat("#,##0.00");
function unformatterAmt(str){
	console.log("first : "+str)
	str=str.substring(0,str.indexOf("."));
	str=str.replace(/,/gi, "");
	console.log("end : "+str)
	return str;

}
</script>
