package com.b5m.sms.web.controller;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Select;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.b5m.sms.biz.service.GoodsService;
import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.common.file.FileResultVO;
import com.b5m.sms.common.util.StringUtil;
import com.b5m.sms.vo.CodeVO;
import com.b5m.sms.vo.OrderDetailVO;
import com.b5m.sms.vo.OrderPOGudsVO;
import com.b5m.sms.vo.OrderPOVO;
import com.b5m.sms.vo.SmsMsEstmGudsVO;
import com.b5m.sms.vo.SmsMsEstmVO;
import com.b5m.sms.vo.SmsMsGudsImgVO;
import com.b5m.sms.vo.SmsMsGudsVO;
import com.b5m.sms.vo.SmsMsOrdFileVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;
import com.b5m.sms.vo.SmsMsOrdVO;



@Controller
public class OrderPOController extends AbstractFileController{
	
	@Resource(name = "orderService")
	public OrderService orderService;	
	
	@Resource(name="goodsService")
	public GoodsService goodsService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderPOController.class);
		
	@RequestMapping(value="/orderPOView")
	public String orderPO(Model model, String ordNo) throws Exception{
		String yes="YES";
		
		//0.화면에 표시될 내용 (정보+상품) 해당 VO를 모두 채워넣으면 화면에 값 표시 완료 
		OrderPOVO poVo = new OrderPOVO();
		List<OrderPOGudsVO> poGudsList = new ArrayList<OrderPOGudsVO>();
		
		
		//계산을 위해 필요한 변수들
		BigDecimal zeroB =new BigDecimal("0");
		DecimalFormat dF = new DecimalFormat("#,##0.00");
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2) ;
		//PO상품정보 표시용
		BigDecimal pcPrcNoVat=new BigDecimal("0");
		BigDecimal pcPrcVat=new BigDecimal("0");
		BigDecimal poPrcSum=new BigDecimal("0");
		BigDecimal poXchrPrc=new BigDecimal("0");
		BigDecimal poXchrPrcSum=new BigDecimal("0");
		//PO정보 표시용
		BigDecimal poXchrAmt=new BigDecimal("0");
		BigDecimal pcSum=new BigDecimal("0");
		BigDecimal pcSumNoVat=new BigDecimal("0");
		BigDecimal dlvPcSum=new BigDecimal("0");
		BigDecimal dlvPcSumNoVat=new BigDecimal("0");
		
		BigDecimal pf=new BigDecimal("0");
		BigDecimal pfNoVat=new BigDecimal("0");
		BigDecimal pfDlvAmt=new BigDecimal("0");
		BigDecimal pfDlvAmtNoVat=new BigDecimal("0");
		
	
		//1-1. SmsMsEstmVO와 SmsMsOrdVO(custId)를 DB에서 읽어온다.
		SmsMsEstmVO estmVo = orderService.selectSmsMsEstmVO(ordNo);
		
		//1-2. 바로 가져올수 있는 값
		poVo.setOrdNo(ordNo);
		poVo.setPoNo(estmVo.getPoNo());
		poVo.setDlvDestCd(estmVo.getDlvDestCd());		//견적서에 존재하지 않는값 DB에만 존재중
		poVo.setDlvAmt(dF.format(new BigDecimal(estmVo.getDlvAmt())));				//물류비
		
		poVo.setPoDt(StringUtil.dtToDate(estmVo.getPoDt()));						//요청일자
		poVo.setPoRegrEml(estmVo.getPoRegrEml());		//PO등록자이메일
		poVo.setPoAmt(dF.format(new BigDecimal(estmVo.getPoSumAmt())));		//PO총금액 usd
		List<CodeVO> dlvModeCdList = orderService.selectTbmsCmnCd("N00052");	//N00052 배송방식코드
		//값으로 들어온 견적조건을 코드값으로 변경
		if(estmVo.getDlvModeCd()!=null){
			for(CodeVO vo : dlvModeCdList){
				System.out.println(vo.getCd());
				if(estmVo.getDlvModeCd().equals(vo.getCd())){
					estmVo.setDlvModeCd(vo.getCdVal());
				}
			}
		}
		poVo.setDlvModeCd(estmVo.getDlvModeCd());		//배송방식코드
		//기준화폐(stdXchrKindCd)
		List<CodeVO> stdXchrKindCdList = orderService.selectTbmsCmnCd("N00059");	//N00059 기준환율코드
		if(estmVo.getStdXchrKindCd()!=null){
			for(CodeVO vo : stdXchrKindCdList){
				if(estmVo.getStdXchrKindCd().equals(vo.getCd())){
					estmVo.setStdXchrKindCd(vo.getCdVal());
				}
			}
		}
		
		
		poVo.setStdXchrKindCd(estmVo.getStdXchrKindCd());		//환율종류코드 :환율종류코드는 환율테이블을 참조해서 값으로 변경해야함 
		
		poVo.setStdXchrAmt(dF.format(new BigDecimal(estmVo.getStdXchrAmt())));				//환율금액

		poVo.setOrdArvlDt(StringUtil.dtToDate(estmVo.getOrdArvlDt()));			//주문도착일자
		poVo.setPoMemoCont(estmVo.getPoMemoCont());	//PO메모
	
		
		//계산에 사용될 변수  : null가능성
		BigDecimal poAll=new BigDecimal(estmVo.getPoSumAmt());
		BigDecimal xchr = new BigDecimal(estmVo.getStdXchrAmt());
		BigDecimal dlv = new BigDecimal(estmVo.getDlvAmt());
		
		//1-4.주문테이블에서 얻어와야 할값
		OrderDetailVO ordVo = orderService.selectSmsMsOrdDetail(ordNo);
		poVo.setCustId(ordVo.getCustId());
		
		
		//2-1. SmsMsEstmGudsVO를 가져온다
		List<SmsMsEstmGudsVO>  estmGudsList = goodsService.selectSmsMsEstmGuds(ordNo);
		
		//2-2. po상품 개수 만큼 OrderPOGudsVO를 생성한다
		for(SmsMsEstmGudsVO vo:estmGudsList){
			OrderPOGudsVO poGudsVo = new OrderPOGudsVO();
			//2-2-1.단순삽입데이터
			poGudsVo.setGudsKorNm(vo.getOrdGudsKorNm());
			poGudsVo.setGudsCnsNm(vo.getOrdGudsCnsNm());
			poGudsVo.setOrdGudsQty(vo.getOrdGudsQty());
			poGudsVo.setPcPrc(dF.format(new BigDecimal(vo.getOrdGudsOrgPrc())));			//매입단가 orgPrc
			poGudsVo.setPoPrc(dF.format(new BigDecimal(vo.getOrdGudsSalePrc())));			//po단가  saleprc
			poGudsVo.setPvdrnNm(vo.getOrdGudsPrvdNm());		//사업자 이름
			poGudsVo.setCrn(vo.getOrdGudsPrvdCrn());			//사업자등록번호
			
			//계산에 사용될 변수 : null가능성 
			BigDecimal qty = new BigDecimal(vo.getOrdGudsQty());
			BigDecimal pcPrc = new BigDecimal(vo.getOrdGudsOrgPrc());	
			BigDecimal poPrc = new BigDecimal(vo.getOrdGudsSalePrc());
			//System.out.println("계산 사용변수2 :" +qty+"               "+orgPrc+"               "+salePrc);

			
			
			
			//2-2-2. SmsMsGuds 테이블에서 가져온다
			SmsMsGudsVO smsMsGudsVo =goodsService.selectSmsMsGuds(vo.getGudsId());

			if(smsMsGudsVo!=null){
				poGudsVo.setGudsInbxQty(smsMsGudsVo.getGudsInbxQty());
				poGudsVo.setGudsUpcId(smsMsGudsVo.getGudsUpcId());
				poGudsVo.setVatYn(smsMsGudsVo.getGudsVatRfndYn());
			}
			//2-2-3. SmsMsGudsImg 테이블에서 가져온다 (목록 이미지 코드(N000080200)를 가져온다)
			SmsMsGudsImgVO smsMsGudsImgVo =new SmsMsGudsImgVO();
			smsMsGudsImgVo.setGudsId(vo.getGudsId());
			smsMsGudsImgVo.setGudsImgCd("N000080200");
			smsMsGudsImgVo=goodsService.selectSmsMsGudsImgByCd(smsMsGudsImgVo);
			String imgSrc =smsMsGudsImgVo.getGudsImgSysFileNm();
			poGudsVo.setImgSrcPath(imgSrc);
			
			
			
			
			
		

			//2-2-4. 계산을 통해 얻는 값
			pcPrcVat=pcPrc.multiply(qty);							//매입합계(부가세포함)
			pcPrcNoVat=pcPrcVat.divide(new BigDecimal("1.1"),8,RoundingMode.HALF_UP);						//매입합계(부가세 제외)
			poPrcSum=poPrc.multiply(qty);							//po합계
			poXchrPrc=poPrc.multiply(xchr);							//po단가
			poXchrPrcSum=poPrcSum.multiply(xchr);				//po합계
			
			pcSum=pcSum.add(pcPrcVat);		
			pcSumNoVat=pcSumNoVat.add(pcPrcNoVat);
			
			
			poGudsVo.setPcPrcNoVat(dF.format(pcPrcNoVat));		//setPcPrc * OrdGudsQty
			poGudsVo.setPcPrcVat(dF.format(pcPrcVat));				//pcPrcNoVat *1.1
			poGudsVo.setPoPrcSum(dF.format(poPrcSum));			//setPoPrc * OrdGudsQty
			poGudsVo.setPoXchrPrc(dF.format(poXchrPrc));		//setPoPrc * estmVo.getStdXchrAmt()
			poGudsVo.setPoXchrPrcSum(dF.format(poXchrPrcSum));		//poPrcSum* estmVo.getStdXchrAmt()
					

			//OrderPOGudsList에 VO삽입
			poGudsList.add(poGudsVo);
					
			
		}
		System.out.println("pcSum"+pcSum);

	
		
		poXchrAmt=poAll.multiply(xchr);
		
		
		dlvPcSum=pcSum.add(dlv);
		dlvPcSumNoVat=pcSumNoVat.add(dlv);
		
		pf=poXchrAmt.subtract(pcSum);
		if(pcSum.compareTo(zeroB)!=0){
			pf=pf.divide(poXchrAmt,8,RoundingMode.HALF_UP);
		}
		System.out.println("poXchrAmt"+poXchrAmt);
		System.out.println("pcPrcNoVat"+pcPrcNoVat);
		System.out.println("pcSum"+pcSum);
		pfNoVat=poXchrAmt.subtract(pcSumNoVat);
		if(pcPrcNoVat.compareTo(zeroB)!=0){
			pfNoVat=pfNoVat.divide(poXchrAmt,8,RoundingMode.HALF_UP);	
		}
		pfDlvAmt=poXchrAmt.subtract(dlvPcSum);
		if(dlvPcSum.compareTo(zeroB)!=0){
			pfDlvAmt=pfDlvAmt.divide(poXchrAmt,8,RoundingMode.HALF_UP);
		}
		pfDlvAmtNoVat=poXchrAmt.subtract(dlvPcSumNoVat);
		pfDlvAmtNoVat=pfDlvAmtNoVat.divide(poXchrAmt,8,RoundingMode.HALF_UP);
		
		//1-3. 계산을 통해서 얻어야 하는 값 (9)
		poVo.setPoXchrAmt(dF.format(poXchrAmt));		//po총금액 krw	=estmVo.getPoSumAmt()*estmVo.getStdXchrAmt()
		poVo.setPcSum(dF.format(pcSum));		//매입합계 부가세포함			+=pcprcvat
		poVo.setPcSumNoVat(dF.format(pcSumNoVat));		//매입합계 부가세 제외		+=pcprcNoVat
		poVo.setDlvPcSum(dF.format(dlvPcSum));			//물류비_매입합계				=pcSum+estmVo.getDlvAmt()
		poVo.setDlvPcSumNoVat(dF.format(dlvPcSumNoVat));	//물류비_매입합계 부가세제외 =pcSumNoVat +estmVo.getDlvAmt()
		
		poVo.setPf(nf.format(pf));			//수익 vat포함						=(poXchrAmt-(+=pcprcvat))/poXchrAmt
		poVo.setPfNoVat(nf.format(pfNoVat));		//수익 vat제외			=((poXchrAmt-(+=pcprcNoVat))/poXchrAmt
		poVo.setPfDlvAmt(nf.format(pfDlvAmt));		//수익 vat포함 +물류비	=((poXchrAmt-(dlvPcSum))/poXchrAmt
		poVo.setPfDlvAmtNoVat(nf.format(pfDlvAmtNoVat));	//수익 vat제외+물류비	=((poXchrAmt-(dlvPcSumNoVat))/poXchrAmt
	

		
		//3.화면에 해당값등 표시 
		//페이지에서 들어온 경로가 view일경우에는 확인 버튼을 사용하지 못하도록
		model.addAttribute("view", yes);
		model.addAttribute("poVo", poVo);
		model.addAttribute("poGudsList", poGudsList);
		model.addAttribute("gudsCnt", poGudsList.size());
		
		return "orderPO";		
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/orderPOSave")
	public String orderPOSave(OrderPOVO orderPoVo, OrderPOGudsVO orderPoGudsVo,int gudsCnt,FileResultVO fileResultVo) throws Exception{
		String result="success";
		try{
			System.out.println("save [orderPO]"+orderPoVo);
			System.out.println("save [orderPoGudsVo]"+orderPoGudsVo);

			
			orderService.orderPOSave(orderPoVo,orderPoGudsVo,gudsCnt,fileResultVo);
		}catch(Exception e){
			e.printStackTrace();
			result="fail";
		}
		return result;
	}
	
	@RequestMapping(value="/orderPOInsert")
	public String orderPOInsert(@RequestParam("file") MultipartFile[] fileArray, Model model, HttpServletRequest req, String ordNo,String wrtrEml) throws Exception{
			List<String> imgFileNameList = new ArrayList<String>();
			
			List<MultipartFile> imgFileList = new ArrayList<MultipartFile>();
			MultipartFile excelFile = null;
			LOGGER.debug("1.=============================" );

			// 허용되는 파일 확장자는 고정되어 있음
			for (MultipartFile multipartFile : fileArray) {		
			LOGGER.debug("2.  ====== inner For ");


				String originalFileName = multipartFile.getOriginalFilename();
				if (originalFileName.endsWith(".png") || originalFileName.endsWith(".jpg")) {
					LOGGER.debug("2.1=============================" );
					imgFileNameList.add(originalFileName.trim());
					imgFileList.add(multipartFile);					//PO파일에 바로저장
				} else if (originalFileName.endsWith(".xls") || originalFileName.endsWith(".xlsx")) {
					LOGGER.debug("2.2.=============================" );
					excelFile = multipartFile;						//엑셀파일
				}
			}
			LOGGER.debug("3.=============================" );

			//이미지 파일과 엑셀파일 로컬에 저장
			List<FileResultVO> fileResultList = new ArrayList<FileResultVO>();
			
			//DB에 파일정보를 유지하기 위한 정보 
			FileResultVO fileResultVO = uploadMultipartFileToDisk(excelFile); 
			fileResultList.add(fileResultVO);
			
			for(MultipartFile mf : imgFileList){
				FileResultVO imgfileResultVO =uploadMultipartFileToDisk(mf);
				fileResultList.add(imgfileResultVO);
			}
			
			//계산을 위한 포맷터
			DecimalFormat dF = new DecimalFormat("#,##0.00");
			DecimalFormat qty = new DecimalFormat("0");
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2) ;
			
			
			
			Workbook wb = WorkbookFactory.create(excelFile.getInputStream());		//엑셀 파일생성
			Sheet sheet = wb.getSheetAt(0);														//엑셀 시트선택

			
			
			
			
			
			
			//1.PO정보 VO생성
			OrderPOVO poVo = new OrderPOVO();
			List<OrderPOGudsVO> poGudsList = new ArrayList<OrderPOGudsVO>();
			
			String poAmt=dF.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(4).getCell(1))));
			String poXchrAmt=dF.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(4).getCell(2))));
			String pcSum=dF.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(4).getCell(3))));
			String pcSumNoVat=dF.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(4).getCell(4))));
			String dlvPcSum=dF.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(4).getCell(5))));

			String dlvPcSumNoVat=dF.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(4).getCell(6))));
			String dlvAmt=dF.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(7).getCell(1))));
			String pf=nf.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(7).getCell(3))));
			String pfNoVat=nf.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(7).getCell(4))));
			String pfDlvAmt=nf.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(7).getCell(5))));
			
			String pfDlvAmtNoVat=nf.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(7).getCell(6))));
			String poMemoCont=StringUtil.excelGetCell(sheet.getRow(9).getCell(1));
			//String ordNo=StringUtil.excelGetCell(sheet.getRow(3).getCell(13));	//엑셀값은 사용안함
			String poNo=StringUtil.excelGetCell(sheet.getRow(4).getCell(13));
			String custId=StringUtil.excelGetCell(sheet.getRow(5).getCell(13));
		
			String stdXchrAmt=dF.format(new BigDecimal(StringUtil.excelGetCell(sheet.getRow(6).getCell(13))));
			String stdXchrKindCd=StringUtil.excelGetCell(sheet.getRow(7).getCell(13));
			String dlvModeCd=StringUtil.excelGetCell(sheet.getRow(8).getCell(13));
			String poDt=StringUtil.excelGetCell(sheet.getRow(9).getCell(13));
			String ordArvlDt=StringUtil.excelGetCell(sheet.getRow(10).getCell(13));

			
			poVo.setPoAmt(poAmt);
			poVo.setPoXchrAmt(poXchrAmt);
			poVo.setPcSum(pcSum);
			poVo.setPcSumNoVat(pcSumNoVat);
			poVo.setDlvPcSum(dlvPcSum);
			
			poVo.setDlvPcSumNoVat(dlvPcSumNoVat);
			poVo.setDlvAmt(dlvAmt);
			poVo.setPf(pf);
			poVo.setPfNoVat(pfNoVat);
			poVo.setPfDlvAmt(pfDlvAmt);
			
			poVo.setPfDlvAmtNoVat(pfDlvAmtNoVat);
			poVo.setPoMemoCont(poMemoCont);
			poVo.setOrdNo(ordNo);
			poVo.setPoNo(poNo);
			poVo.setCustId(custId);
			
			poVo.setStdXchrAmt(stdXchrAmt);
			poVo.setStdXchrKindCd(stdXchrKindCd);
			poVo.setDlvModeCd(dlvModeCd);
			poVo.setPoDt(poDt);
			poVo.setOrdArvlDt(ordArvlDt);

	
			
			
			//1-2.버전에 따른 이미지 저장방법이 다름
			
	        if(wb instanceof HSSFWorkbook) { 
	            this.excelHSSFPictureInfo((HSSFWorkbook)wb,ordNo); 
	        } 
	        else { 
	            this.excelXSSFPictureInfo((XSSFWorkbook)wb,ordNo); 
	        } 

			
			//2.상품정보 VO생성
			int rows = sheet.getPhysicalNumberOfRows();

			
			for(int i=13; i<rows-1; i++){

				
				OrderPOGudsVO poGudsVo = new OrderPOGudsVO();
				
				//poGudsVo.setImgSrcPath("file:///"+imgName+ordNo+i+".jpg");
				Row getRow=sheet.getRow(i);
				if(StringUtil.getCellUpcId(getRow.getCell(2))!=null){			//physicalNumber는 엑셀에 따라 더 커질수 있으므로 바코드를 기준으로 값을 관리한다.
					poGudsVo.setImgSrcPath(ordNo+i+".jpg");
					poGudsVo.setGudsUpcId(StringUtil.getCellUpcId(getRow.getCell(2)));
					poGudsVo.setGudsCnsNm(StringUtil.excelGetCell(getRow.getCell(3)));
					poGudsVo.setGudsKorNm(StringUtil.excelGetCell(getRow.getCell(4)));
					System.out.println("제품수량 : "+StringUtil.excelGetCell(getRow.getCell(5)));
					poGudsVo.setOrdGudsQty(qty.format(new BigDecimal(StringUtil.excelGetCell(getRow.getCell(5)))));
					
					poGudsVo.setGudsInbxQty( qty.format(new BigDecimal(StringUtil.excelGetCell(getRow.getCell(6)))));
					poGudsVo.setVatYn(StringUtil.excelGetCell(getRow.getCell(7)));
					System.out.println("pcprc value :"+StringUtil.excelGetCell(getRow.getCell(8)));
					System.out.println("bigDecimal로 변경 :" +new BigDecimal(StringUtil.excelGetCell(getRow.getCell(8))));
					
					poGudsVo.setPcPrc(dF.format(new BigDecimal(StringUtil.excelGetCell(getRow.getCell(8)))));
					poGudsVo.setPcPrcVat(dF.format(new BigDecimal(StringUtil.excelGetCell(getRow.getCell(9)))));
					poGudsVo.setPcPrcNoVat(dF.format(new BigDecimal(StringUtil.excelGetCell(getRow.getCell(10)))));
					
					poGudsVo.setPoPrc(dF.format(new BigDecimal(StringUtil.excelGetCell(getRow.getCell(11)))));
					poGudsVo.setPoPrcSum(dF.format(new BigDecimal(StringUtil.excelGetCell(getRow.getCell(12)))));
					poGudsVo.setPoXchrPrc(dF.format(new BigDecimal(StringUtil.excelGetCell(getRow.getCell(13)))));
					poGudsVo.setPoXchrPrcSum(dF.format(new BigDecimal(StringUtil.excelGetCell(getRow.getCell(14)))));
					poGudsVo.setPvdrnNm(StringUtil.excelGetCell(getRow.getCell(15)));
				
					poGudsVo.setCrn(StringUtil.excelGetCell(getRow.getCell(16)));
					
					if(poGudsVo.getGudsUpcId()!=null)	{		//바코드는 반드시 존재해야하므로 바코드가 존재하는데까지
						poGudsList.add(poGudsVo);
					}
				}
			}
			
		
			
			
			
		//3.이미지를 읽어 들여 저장 

			
		//4.model에 등록 
		model.addAttribute("poVo", poVo);
		model.addAttribute("poGudsList", poGudsList);
		model.addAttribute("gudsCnt", poGudsList.size());
		model.addAttribute("fileResultList",fileResultList);
		
		
		

		return "orderPO";
	}
	
private void excelHSSFPictureInfo(HSSFWorkbook workbook, String ordNo) throws IOException {
		
		
		List<HSSFPictureData> pictures = workbook.getAllPictures();
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		// Make a mapping of Interger and XSSFPictureData
		// Map<Integer, HSSFPictureData> map = new HashMap<Integer, HSSFPictureData>();
		
		for(HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
			HSSFClientAnchor anchor = (HSSFClientAnchor)shape.getAnchor();
			
			int rowIndex = anchor.getRow1();
			if(shape instanceof HSSFPicture) {
				int rowmark = rowIndex;
				HSSFPicture picture = (HSSFPicture)shape;
				int pictureIndex = picture.getPictureIndex() - 1;
				HSSFPictureData pictureData = pictures.get(pictureIndex);
				
				// map.put(rowmark, pictureData);
				
				// System.out.println(rowmark);
				
				byte[] data = pictureData.getData();
				String ext = pictureData.suggestFileExtension();

				FileOutputStream out = new FileOutputStream(OPT_B5C_IMG+ordNo+ rowmark + ".jpg");
				out.write(data);
				out.close();

			}
			
		}
		
	}
	
	private void excelXSSFPictureInfo(XSSFWorkbook workbook, String ordNo) throws IOException {
		
		
		List<XSSFPictureData> pictures = workbook.getAllPictures();
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		//Make a mapping of Interger and XSSFPictureData
		Map<Integer, XSSFPictureData> map = new HashMap<Integer, XSSFPictureData>();
		
		for(POIXMLDocumentPart dr : sheet.getRelations()) {
			if(dr instanceof XSSFDrawing) {
				XSSFDrawing drawing = (XSSFDrawing)dr;
				List<XSSFShape> shapes = drawing.getShapes();
				
				for(XSSFShape shape : shapes) {
					XSSFPicture picture = (XSSFPicture)shape;
					XSSFClientAnchor anchor = picture.getPreferredSize();
					
					CTMarker ctMarker = anchor.getFrom();
					
					XSSFPictureData pictureData = picture.getPictureData();
					
					byte[] data = pictureData.getData();
					String ext = pictureData.suggestFileExtension();
					
					FileOutputStream out = new FileOutputStream(OPT_B5C_IMG+ordNo + ctMarker.getRow() + ".jpg");
					
					out.write(data);
					out.close();

					
				}
			}
		}
		
	}
	
}