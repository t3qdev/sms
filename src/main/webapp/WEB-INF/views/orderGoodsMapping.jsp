<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="web_ctx" value="${pageContext.request.contextPath}" />

<!--  Header Include   -->
<!--jsp:include page="layout/header.jsp"/-->
<style>
body{min-width:760px; background:#fff}
</style>
<body>
<article>
	<h1>
		<span>商品配对</span>
	</h1>
    <div class="ui-layout-single">
    
        <section>
            <!--<h2><span>브랜드</span></h2> -->
            <div class="ui-layout-double">
                <section class="ui-layout-action">
                	
                </section>
                <section class="ui-layout-action">
					<div class="mr10">
                        <input type="text" id="searchText" name="searchText" size="50" style="height:30px; line-height:30px; box-sizing:border-box" />
                        <button class="btn-search">搜索</button>
                    </div>
                </section>
            </div>

        </section>
    </div>
	<div class="ui-layout-single">
        <section>

			<div class="ui-layout-single">
                
                <table class="order_table2">
                <colgroup>
                <col style="width:30px">
                <col style="width:80px">
                <col style="width:100px">
                <col style="width:25%">
                <col style="width:80px">
                <col style="width:50px">
                </colgroup>
                    <input type="hidden" id="index" name="index" value="${index }">
                    <input type="hidden" id="ordNo" name="ordNo" value="${ordNo }">
                    <input type="hidden" id="ordGudsSeq" name="ordGudsSeq" value="${ordGudsSeq }">
                <tbody id="tbody">
                	<tr>
                    	<th>-</th>
                        <th>图片</th>
                        <th>条码</th>
                        <th>商品名</th>
                        <th>规格</th>
                        <th>装箱数量</th>
                    </tr>
                    
   
                    <c:forEach var="smsMsGuds" items="${smsMsGudsList }" varStatus="status">
              
	                    <tr>
	                    	<td class="tac"><input type="radio" name="p_match" value="${status.count }"></td>
	                        <td class="tac"><img src="${web_ctx}/orderDetailFileDownload.do?filePath=${ smsMsGuds.imgSrcPath}" width="60" height="60"></td>
	                        <td class="tac">${smsMsGuds.gudsUpcId }</td>
	                        <td>${smsMsGuds.gudsCnsNm }</td>
	                        <td class="tac"></td>		<!-- 규격값, 사용안함 (DB존재 안함) -->
	                        <td class="tac">${smsMsGuds.gudsInbxQty }</td>
	                        <input type="hidden" id="gudsId${status.count }" name="gudsId${status.count }" value="${smsMsGuds.gudsId }">
	                         <input type="hidden" id="imgSrcPath${status.count }" name="imgSrcPath${status.count }" value="${smsMsGuds.imgSrcPath }">
	                         <input type="hidden" id="gudsKorNm${status.count }" name="gudsKorNm${status.count }" value="${smsMsGuds.gudsKorNm }">
	                         <input type="hidden" id="gudsCnsNm${status.count }" name="gudsCnsNm${status.count }" value="${smsMsGuds.gudsCnsNm }">	                       
	                         <input type="hidden" id="gudsUpcId${status.count }" name="gudsUpcId${status.count }" value="${smsMsGuds.gudsUpcId }">     
	                         <input type="hidden" id="gudsInbxQty${status.count }" name="gudsInbxQty${status.count }" value="${smsMsGuds.gudsInbxQty }">
	                    </tr>
                    </c:forEach>

				
				 <c:if test="${fn:length(smsMsGudsList) ==0}">
                    <tr>
                    	<td colspan="6" class="tac nodata">没有相关的数据。</td>
                    </tr>
                   </c:if>
                </tbody>

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
                    <button class="btn-cancel">取消</button>
                    <button class="btn-submit mr10" id="check">确认</button>
                </section> 
            </div>
        </section>
    </div>
    
    
    
    
</article>

</article>

<script>
$(function(){
	   $.ajaxSetup({cache:false});   
	//1.취소버튼
	$('.btn-cancel').click(function(){
		window.close();			//IE에서 안될경우 window.open("about:blank","_self").close();
	});//end click
	
	//2.검색버튼 : 해당검색어 검색을 해서 페이지를 다시로딩
	$('.btn-search').on("click",function(event){
		var searchText = $('#searchText').val();
		$.ajax({
			type : "POST",
			url : '${web_ctx}/orderGoodsMappingSearch.do',
			data:	{"searchText" : searchText},
			async: false,
			cache : false,
			success:function(result){
		 		var tbodyHtml = 	"<tr>	<th>-</th><th>图片</th><th>条码</th> <th>商品名</th><th>规格</th><th>装箱数量</th></tr>";
				$(result).each(function(index,item){
					if(item.gudsUpcId== null) {item.gudsUpcId="";}
					if(item.gudsCnsNm== null) {item.gudsCnsNm="";}
					if(item.gudsInbxQty== null) {item.gudsInbxQty="";}
					tbodyHtml+= '<tr><td class="tac"><input type="radio" name="p_match" value=" '+(index+1)+' "></td>';
					tbodyHtml+= '<td class="tac"><img src="${web_ctx}/orderDetailFileDownload.do?filePath='+item.imgSrcPath+'" width="60" height="60"></td>';
					tbodyHtml+='<td class="tac">'+item.gudsUpcId+'</td>';
					tbodyHtml+='<td>'+item.gudsCnsNm+'</td>';
					tbodyHtml+='<td class="tac"></td>';
					tbodyHtml+='<td class="tac">'+item.gudsInbxQty+'</td>';
					tbodyHtml+='<input type="hidden" id="gudsId'+(index+1)+'" name="gudsId'+(index+1)+'" value="'+item.gudsId+'">';
					tbodyHtml+='<input type="hidden" id="imgSrcPath'+(index+1)+'" name="imgSrcPath'+(index+1)+'" value="'+item.imgSrcPath+'">';
					tbodyHtml+='<input type="hidden" id="gudsKorNm'+(index+1)+'" name="gudsKorNm'+(index+1)+'" value="'+item.gudsKorNm+'">';
					tbodyHtml+='<input type="hidden" id="gudsCnsNm'+(index+1)+'" name="gudsCnsNm'+(index+1)+'" value="'+item.gudsCnsNm+'">';	                       
					tbodyHtml+='<input type="hidden" id="gudsUpcId'+(index+1)+'" name="gudsUpcId'+(index+1)+'" value="'+item.gudsUpcId+'">';
					tbodyHtml+='<input type="hidden" id="gudsInbxQty'+(index+1)+'" name="gudsInbxQty'+(index+1)+'" value="'+item.gudsInbxQty+'"> </tr>';
				})
				if(result.length==0){
					tbodyHtml+=' <tr><td colspan="6" class="tac nodata">没有相关的数据。</td></tr>';
				}
				$('#tbody').html(tbodyHtml);
			}
		});//end $.ajax	
	});//end Click
	
	
	//3.확인버튼 : 선택값을 부모선택자로 옮기고 매핑을 Y로 설정(gudsId를 변경)
	$('#check').on("click",function(event){
		var radioIndex=$('input:radio[name="p_match"]:checked').val().trim();
		
		var ordNo =$('#ordNo').val(); 
		var ordGudsSeq=$('#ordGudsSeq').val();
		
		if(radioIndex==null) alert("请选择商品");
		else{			
			var gudsUpcId =$('#gudsUpcId'+radioIndex).val();
			var gudsId = $('#gudsId'+radioIndex).val();
			var imgSrcPath=$('#imgSrcPath'+radioIndex).val();
			var gudsInbxQty=$('#gudsInbxQty'+radioIndex).val();
			$.ajax({
				type : "POST",
				url : '${web_ctx}/orderGoodsMappingSave.do',
				data:	{
					"gudsUpcId" : gudsUpcId,
					"ordNo" :ordNo,
					"ordGudsSeq":ordGudsSeq,
					"gudsId" : gudsId
					
				},
				async: false,
				cache : false,
				success:function(){
					var imgPath = "${web_ctx}/orderDetailFileDownload.do?filePath="+imgSrcPath;
					$(opener.document).find("input[name=ordGudsUpcId]").eq(${index }).val(gudsUpcId);
					$(opener.document).find("input[name=imgSrcPath]").eq(${index }).val(imgSrcPath);
					$(opener.document).find("input[name=gudsInbxQty]").eq(${index }).val(gudsInbxQty);
					
					 $(opener.document).find("img[name=imgSrcPath_src]").eq(${index }).attr("src",imgPath);		
					alert("商品以正常配对");
					//opener.document.orderDetailSaveForm.submit(); 
					window.close();			//IE에서 안될경우 window.open("about:blank","_self").close();
				}
			});//end $.ajax	
		}//end else
	});//end Click
		

	
	
	
	

})//end func

</script>
