package com.b5m.sms.web.controller;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.b5m.sms.vo.OrderPOGudsVO;
import com.b5m.sms.vo.OrderPOVO;
import com.b5m.sms.vo.SmsMsEstmGudsVO;
import com.b5m.sms.vo.SmsMsEstmVO;
import com.b5m.sms.vo.SmsMsGudsVO;
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
		//화면에 표시될 내용 (정보+상품)
		OrderPOVO poVo = new OrderPOVO();
		List<OrderPOGudsVO> poGudsList = new ArrayList<OrderPOGudsVO>();
		
		
		//1-1. SmsMsEstmVO와 SmsMsOrdVO를 DB에서 읽어온다.
		SmsMsEstmVO EstmVo = new SmsMsEstmVO();		//ordNo로 검색
		SmsMsOrdVO ordVo = new SmsMsOrdVO();			//ordNo로 검색
		
		//1-2.해당값들을 이용하여 OrderPOVO를 만든다
		
		
		
		List<SmsMsEstmGudsVO> EstmGudsList = new ArrayList<SmsMsEstmGudsVO>();
		
		
		//2. 화면에 데이터를 뿌려준다
		
		
		
		
		//페이지에서 들어온 경로가 view일경우에는 확인 버튼을 사용하지 못하도록
		
		model.addAttribute("view", yes);
		model.addAttribute("poVo", poVo);
		model.addAttribute("poGudsList", poGudsList);
		model.addAttribute("gudsCnt", poGudsList.size());
		return "orderPO";		
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/orderPOSave")
	public void orderPOSave(OrderPOVO orderPoVo, OrderPOGudsVO orderPoGudsVo,int gudsCnt, String wrtrEml) throws Exception{
		orderService.orderPOSave(orderPoVo,orderPoGudsVo,gudsCnt,wrtrEml);
		
	}
	
	@RequestMapping(value="/orderPOInsert")
	public String orderPOInsert(@RequestParam("file") MultipartFile[] fileArray, Model model, HttpServletRequest req) throws Exception{
			System.out.println(req.getContextPath());
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
			FileResultVO fileResultVO = uploadMultipartFileToDisk(excelFile); 
			for(MultipartFile mf : imgFileList){
				uploadMultipartFileToDisk(mf);
			}
			
			
			Workbook wb = WorkbookFactory.create(excelFile.getInputStream());		//엑셀 파일생성
			Sheet sheet = wb.getSheetAt(0);														//엑셀 시트선택

			
			
			
			
			
			
			//1.PO정보 VO생성
			OrderPOVO poVo = new OrderPOVO();
			List<OrderPOGudsVO> poGudsList = new ArrayList<OrderPOGudsVO>();
			
			String poAmt=StringUtil.excelGetCell(sheet.getRow(4).getCell(1));
			String poXchrAmt=StringUtil.excelGetCell(sheet.getRow(4).getCell(2));
			String pcSum=StringUtil.excelGetCell(sheet.getRow(4).getCell(3));
			String pcSumNoVat=StringUtil.excelGetCell(sheet.getRow(4).getCell(4));
			String dlvPcSum=StringUtil.excelGetCell(sheet.getRow(4).getCell(5));

			String dlvPcSumNoVat=StringUtil.excelGetCell(sheet.getRow(4).getCell(6));
			String dlvAmt=StringUtil.excelGetCell(sheet.getRow(7).getCell(1));
			String pf=StringUtil.excelGetCell(sheet.getRow(7).getCell(3));
			String pfNoVat=StringUtil.excelGetCell(sheet.getRow(7).getCell(4));
			String pfDlvAmt=StringUtil.excelGetCell(sheet.getRow(7).getCell(5));
			
			String pfDlvAmtNoVat=StringUtil.excelGetCell(sheet.getRow(7).getCell(6));
			String poMemoCont=StringUtil.excelGetCell(sheet.getRow(9).getCell(1));
			String ordNo=StringUtil.excelGetCell(sheet.getRow(3).getCell(13));
			String poNo=StringUtil.excelGetCell(sheet.getRow(4).getCell(13));
			String custId=StringUtil.excelGetCell(sheet.getRow(5).getCell(13));
		
			String stdXchrAmt=StringUtil.excelGetCell(sheet.getRow(6).getCell(13));
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

			System.out.println(poVo);
			
			
			//1-2.버전에 따른 이미지 저장방법이 다름
			
	        if(wb instanceof HSSFWorkbook) { 
	            this.excelHSSFPictureInfo((HSSFWorkbook)wb,ordNo); 
	        } 
	        else { 
	            this.excelXSSFPictureInfo((XSSFWorkbook)wb,ordNo); 
	        } 

			
			//2.상품정보 VO생성
			int rows = sheet.getPhysicalNumberOfRows();
			System.out.println("row 갯수 : " + rows);

			
			for(int i=13; i<rows-1; i++){
				OrderPOGudsVO poGudsVo = new OrderPOGudsVO();
				
				//poGudsVo.setImgSrcPath("file:///"+imgName+ordNo+i+".jpg");
				poGudsVo.setImgSrcPath(ordNo+i+".jpg");
				poGudsVo.setGudsUpcId(StringUtil.excelGetCell(sheet.getRow(i).getCell(2)));
				poGudsVo.setGudsCnsNm(StringUtil.excelGetCell(sheet.getRow(i).getCell(3)));
				poGudsVo.setGudsKorNm(StringUtil.excelGetCell(sheet.getRow(i).getCell(4)));
				poGudsVo.setOrdGudsQty(StringUtil.excelGetCell(sheet.getRow(i).getCell(5)));
				
				poGudsVo.setGudsInbxQty(StringUtil.excelGetCell(sheet.getRow(i).getCell(6)));
				poGudsVo.setVatYn(StringUtil.excelGetCell(sheet.getRow(i).getCell(7)));
				poGudsVo.setPcPrc(StringUtil.excelGetCell(sheet.getRow(i).getCell(8)));
				poGudsVo.setPcPrcVat(StringUtil.excelGetCell(sheet.getRow(i).getCell(9)));
				poGudsVo.setPcPrcNoVat(StringUtil.excelGetCell(sheet.getRow(i).getCell(10)));
				
				poGudsVo.setPoPrc(StringUtil.excelGetCell(sheet.getRow(i).getCell(11)));
				poGudsVo.setPoPrcSum(StringUtil.excelGetCell(sheet.getRow(i).getCell(12)));
				poGudsVo.setPoXchrPrc(StringUtil.excelGetCell(sheet.getRow(i).getCell(13)));
				poGudsVo.setPoXchrPrcSum(StringUtil.excelGetCell(sheet.getRow(i).getCell(14)));
				poGudsVo.setPvdrnNm(StringUtil.excelGetCell(sheet.getRow(i).getCell(15)));
			
				poGudsVo.setCrn(StringUtil.excelGetCell(sheet.getRow(i).getCell(16)));
				
				if(poGudsVo.getGudsUpcId()!=null)	{		//바코드는 반드시 존재해야하므로 바코드가 존재하는데까지
					poGudsList.add(poGudsVo);
				}
				System.out.println(poGudsVo);
			}
			
		
			
			
			
		//3.이미지를 읽어 들여 저장 

			
		//4.model에 등록 
		model.addAttribute("poVo", poVo);
		model.addAttribute("poGudsList", poGudsList);
		model.addAttribute("gudsCnt", poGudsList.size());
		
		

		return "orderPO";
	}
	
private void excelHSSFPictureInfo(HSSFWorkbook workbook, String ordNo) throws IOException {
		
		System.out.println("HSSF Excel Format.");
		
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

				FileOutputStream out = new FileOutputStream(OPT_B5C_DISK+ordNo+ rowmark + ".jpg");
				out.write(data);
				out.close();

			}
			
		}
		
	}
	
	private void excelXSSFPictureInfo(XSSFWorkbook workbook, String ordNo) throws IOException {
		
		System.out.println("XSSF Excel Format.");
		
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
					
					FileOutputStream out = new FileOutputStream(OPT_B5C_DISK+ordNo + ctMarker.getRow() + ".jpg");
					
					out.write(data);
					out.close();

					
				}
			}
		}
		
	}
	
}
