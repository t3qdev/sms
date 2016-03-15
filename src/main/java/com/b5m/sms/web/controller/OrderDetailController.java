package com.b5m.sms.web.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hslf.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.b5m.sms.biz.dao.SmsMsOrdHistDAO;
import com.b5m.sms.biz.service.GoodsService;
import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.biz.service.UserService;
import com.b5m.sms.common.file.FileResultVO;
import com.b5m.sms.common.util.DateUtil;
import com.b5m.sms.common.util.StringUtil;
import com.b5m.sms.vo.CodeVO;
import com.b5m.sms.vo.OrderDetailVO;
import com.b5m.sms.vo.SmsMsEstmGudsVO;
import com.b5m.sms.vo.SmsMsEstmVO;
import com.b5m.sms.vo.SmsMsGudsImgVO;
import com.b5m.sms.vo.SmsMsGudsVO;
import com.b5m.sms.vo.SmsMsOrdFileVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;
import com.b5m.sms.vo.SmsMsOrdUserVO;
import com.b5m.sms.vo.SmsMsUserVO;

@Controller
public class OrderDetailController extends AbstractFileController{
		
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderManagementController.class);

	@Resource(name = "orderService")
	public OrderService orderService;	
	
	@Resource(name = "userService")
	public UserService userService;
	
	@Resource(name="goodsService")
	public GoodsService goodsService;
	
	@Resource(name="smsMsOrdHistDAO")
	private SmsMsOrdHistDAO smsMsOrdHistDAO;
	
	@RequestMapping(value="/orderDetailView")
	public String orderDetail(Model model, String ordNo, String reload) throws Exception{
		
		//1.selectBox 구성용 모델
		//1-1.담당자를 고를수 있는 SmsMsUser (중국담당자list/ 한국담당자list)
		SmsMsUserVO prSmsMsUserVO= new SmsMsUserVO();
		
		List<SmsMsUserVO> SmsMsUserList = userService.selectSmsMsUser(prSmsMsUserVO);	//비어있는SmsMsUserVO를 매개변수로 넣으면 전체검색 
		List<SmsMsUserVO> cnsOprList= new ArrayList<SmsMsUserVO>();		//중국담당자리스트
		List<SmsMsUserVO> krOprList= new ArrayList<SmsMsUserVO>();			//한국담당자리스트
		
		
		for(SmsMsUserVO vo : SmsMsUserList){
			if(("N000530100").equals(vo.getOgnzDivCd())){			//N000530100 상해팀
				cnsOprList.add(vo);
				
			}
			else if(("N000530200").equals(vo.getOgnzDivCd())){		//N000530200 한국팀
				krOprList.add(vo);
			}
		}
		//1-2.기준화폐(stdXchrKindCd)
		List<CodeVO> stdXchrKindCdList = orderService.selectTbmsCmnCd("N00059");	//N00059 기준환율코드
		//1-3.견적조건(dlvMode)
		List<CodeVO> dlvModeCdList = orderService.selectTbmsCmnCd("N00052");	//N00052 배송방식코드
		//1-4.항구(dlvDest)
		List<CodeVO> dlvDestCdList = orderService.selectTbmsCmnCd("N00051");	//N00051 배송지도착코드
		
		//2.견적조건을 담은 orderDetailVO 
		OrderDetailVO orderDetail = orderService. selectSmsMsOrdDetail(ordNo);
		//주문담당자정보는 다른 테이블에 존재한다 가져와서 orderDetailVO에 채워줌						<<--getUserAlasEngNm이 null일경우 ()제거 생각해둘것
		List<SmsMsUserVO> oprList = userService.selectSmsMsUserByOrdNo(ordNo);
		for(SmsMsUserVO vo : oprList){
			if(("N000530100").equals(vo.getOgnzDivCd())){			//N000530100 상해팀
				/*String oprCns =vo.getUserAlasCnsNm()+"("+vo.getUserAlasEngNm()+")";
				orderDetail.setOprCns(oprCns);*/
				orderDetail.setOprCns(vo.getUserEml());
				
			}
			else if(("N000530200").equals(vo.getOgnzDivCd())){		//N000530200 한국팀
				/*String oprKr =vo.getUserAlasCnsNm()+"("+vo.getUserAlasEngNm()+")";
				orderDetail.setOprKr(oprKr);*/
				orderDetail.setOprKr(vo.getUserEml());
			}
		}
	
		//3.견적상품을 담은 List<smsMsOrdGudsList>			//이미지-바코드-상품명-예상요청수량-규격-가격(단가)-인박스수량-상품링크-검색 : 보류(다시)
		List<SmsMsOrdGudsVO> smsMsOrdGudsList =goodsService.selectSmsMsOrdGudsByOrdNo(ordNo);
		//주문에 존재하는 상품이 Mapping이 되었을 경우 상품테이블에서 *이미지,인박스수량,바코드*값을 가져온다
		for(SmsMsOrdGudsVO vo :smsMsOrdGudsList){
			if("Y".equals(vo.getOrdGudsMpngYn())){
				SmsMsGudsVO smsGuds = new SmsMsGudsVO();
				smsGuds=goodsService.selectSmsMsGuds(vo.getGudsId());

				//매핑이 되었다고 나오는데 실제 상품정보가 없는 경우가 존재할수 있다.
				if(smsGuds!=null){
	 				vo.setOrdGudsUpcId(smsGuds.getGudsUpcId());				//바코드 //vo.setGudsUpcId(smsGuds.getGudsUpcId());		
					vo.setGudsInbxQty(smsGuds.getGudsInbxQty());	//이미지
				}else{
					vo.setOrdGudsMpngYn("N");
				}
				//GUDS_IMG_CD  N000080100 대표이미지 	N000080200 목록이미지
				List<SmsMsGudsImgVO> gudsImgList = goodsService.selectSmsMsGudsImg(vo.getGudsId());
				if(!gudsImgList.isEmpty()){ //OPT_B5C_DISK
					if(!StringUtil.isNullOrEmpty(gudsImgList.get(0).getGudsImgSysFileNm())){
						vo.setImgSrcPath(gudsImgList.get(0).getGudsImgSysFileNm());
					}
				}
			}
		}//End_for(SmsMsOrdGudsVO vo :smsMsOrdGudsList)

		
		
	

		//4.견적파일정보를 담은 List<orderDetailFileVO>
		List<SmsMsOrdFileVO> smsMsOrdFileList= orderService.selectSmsMsOrdFileByOrdNo(ordNo); 	//=orderService.select

		for(SmsMsOrdFileVO vo : smsMsOrdFileList){
			System.out.println("filevo : "+vo);
		}
		System.out.println("orderDeatil페이지 로딩시 orderDeatil정보 :::::::::::::::::"+orderDetail);
		
		//5.model에 각종 정보를 담는다.
		model.addAttribute("ordNo", ordNo);		//주문번호	
		//selectBox구성요소
		model.addAttribute("cnsOprList",cnsOprList);		//중국팀선택
		model.addAttribute("krOprList",krOprList);		//한국팀선택
		model.addAttribute("stdXchrKindCdList",stdXchrKindCdList);		//기준화폐(stdXchrKindCd)
		model.addAttribute("dlvModeCdList",dlvModeCdList);		//견적조건(dlvMode)
		model.addAttribute("dlvDestCdList",dlvDestCdList);		//항구(dlvDest)
		//값요소
		model.addAttribute("orderDetail",orderDetail);	//주문상세정보
		model.addAttribute("smsMsOrdGudsList", smsMsOrdGudsList);	//주문상품리스트
		model.addAttribute("gudsListSize", smsMsOrdGudsList.size());	//주문상품리스트
		model.addAttribute("smsMsOrdFileList", smsMsOrdFileList);		//주문파일리스트
		
		//부모창 새로고침
		model.addAttribute("reload", reload);		
		return "orderDetail";
		
	}
	////////////////////////////////////////////////////////
	@RequestMapping(value="/orderDetailSpecialView")
	public String orderDetail(@RequestParam("file") MultipartFile[] fileArray, Model model, String ordNo) throws Exception{
		
		/////////////////////////////////////////// 스페셜 오더, 견적서 엑셀 로드 - KJY ///////////////////////////////////////////
		// 파일 load
		MultipartFile excelFile = null;
		for (MultipartFile multipartFile : fileArray) {		
			String originalFileName = multipartFile.getOriginalFilename();
			if (originalFileName.endsWith(".xls") || originalFileName.endsWith(".xlsx")) {          //엑셀 파일 확인
				LOGGER.debug("1.1.=============================" );
				excelFile = multipartFile;
			}else{
				LOGGER.debug("1.2.=============================확장자 잘못됨." );
			}
		}
		System.out.println("ordNO :-=--=-"+ordNo);
		//workbook 초기화
		Workbook wb = WorkbookFactory.create(excelFile.getInputStream());
		Sheet sheet = wb.getSheetAt(1);                    // 임시로 1번 한국어 시트로 함.

		LOGGER.debug(" 2.1.1 엑셀에서 SMS_MS_ORD 정보를 뽑아온다." );
		// 클라이언트 요청 견적서 excel 에서 받아올 변수들 초기화.     SmsMsOrdVO
		String userAlasCnsNm = null;							 //담당자   (중국의 중문 화명)
		String custId = null;								// 클라이언트
		String ordReqDt = null;							// 문의일자
		String ordHopeArvlDt = null;					// 희망 인도일자
		String	 dlvModeCdPlusdlvDestCd = null;		// 견적조건 + 항구 
		String ctrtTmplYn = null;						// 계약서 템플릿 유무
		String poSchdDt = null;							// PO예상일자
		String smplReqYn = null;						// 샘플요청유무
		String qlfcReqYn = null;							// 자격 요청 유무
		String custOrdProcCont = null;				 // 주문 프로세스
		
		String dlvModeCd = null;	 					// 견적조건
		String dlvDestCd = null;						// 항구
		
		String ordTypeCd = null;		
		String ordMemoCont = null;                   //비고
		// 엑셀에서 ExcelClientReqGudsVO 변수들 가져와서 대입.
		userAlasCnsNm = StringUtil.excelGetCell(sheet.getRow(1).getCell(2));  						//담당자
		custId = StringUtil.excelGetCell(sheet.getRow(1).getCell(4));    						// 클라이언트
		ordReqDt = StringUtil.excelGetCell(sheet.getRow(1).getCell(6)); 						// 문의일자
		if(ordReqDt!=null){
			ordReqDt = ordReqDt.replace("-", "");
			if("".equals(ordReqDt)) ordReqDt=null;
			if(ordReqDt.length()>8) ordReqDt=null;
		}
		ordHopeArvlDt = StringUtil.excelGetCell(sheet.getRow(2).getCell(2)); 				// 희망 인도일자
		if(ordHopeArvlDt!=null){
			ordHopeArvlDt = ordHopeArvlDt.replace("-", "");
			if("".equals(ordHopeArvlDt)) ordHopeArvlDt=null;
			if(ordHopeArvlDt.length()>8) ordHopeArvlDt=null;	
		}
		dlvModeCdPlusdlvDestCd = StringUtil.excelGetCell(sheet.getRow(2).getCell(4));	// 견적조건 + 항구 	
//		약자 - 풀네임(항구)
		if(dlvModeCdPlusdlvDestCd!=null){
			String [] strArr = null;
			strArr = dlvModeCdPlusdlvDestCd.trim().split(" ");

			if(strArr.length==1){
				dlvModeCd = strArr[0].trim();
			}else if(strArr.length==2){
				dlvModeCd = strArr[0].trim();
				dlvDestCd = strArr[1].trim();
			}
		}
		System.out.println("dlvModeCd: " +dlvModeCd );
		System.out.println("dlvDestCd: " +dlvDestCd );
		ctrtTmplYn = StringUtil.excelGetCell(sheet.getRow(3).getCell(2));     				// 계약서 템플릿 유무
		if("Y".equalsIgnoreCase(ctrtTmplYn)) {ctrtTmplYn="Y";}									
		else if("N".equalsIgnoreCase(ctrtTmplYn)) {ctrtTmplYn="N";}							
		else{
			ctrtTmplYn=null;
		}
		poSchdDt = StringUtil.excelGetCell(sheet.getRow(3).getCell(6));     							// PO예상일자
		if(poSchdDt!=null){
			poSchdDt = poSchdDt.replace("-", "");
			if("".equals(poSchdDt)) poSchdDt=null;
			if(poSchdDt.length()>8) poSchdDt=null;
		}
		smplReqYn = StringUtil.excelGetCell(sheet.getRow(3).getCell(4));     					// 샘플요청유무
		if("Y".equalsIgnoreCase(smplReqYn)) {smplReqYn="Y";}
		else if("N".equalsIgnoreCase(smplReqYn)) {smplReqYn="N";}
		else{
			smplReqYn=null;
		}
		qlfcReqYn = StringUtil.excelGetCell(sheet.getRow(4).getCell(2));       				// 자격 요청 유무
		if("Y".equalsIgnoreCase(qlfcReqYn)) {qlfcReqYn="Y";}
		else if("N".equalsIgnoreCase(qlfcReqYn)) {qlfcReqYn="N";}
		else{
			qlfcReqYn=null;
		}
		custOrdProcCont = StringUtil.excelGetCell(sheet.getRow(4).getCell(4));  			// 주문 프로세스
		int tempRows = sheet.getPhysicalNumberOfRows();
		ordMemoCont = StringUtil.excelGetCell(sheet.getRow(tempRows-1).getCell(1));  	//비고
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("담당자 명 : " + userAlasCnsNm);        //담당자명 : 중문화명
		System.out.println("클라이언트 : " + custId);
		System.out.println("문의일자 : " + ordReqDt);
		System.out.println("희망 인도일자 : " + ordHopeArvlDt);
		System.out.println("견적조건 + 항구  : " + dlvModeCdPlusdlvDestCd);
		System.out.println("계약서 템플릿 유무 : " + ctrtTmplYn);
		System.out.println("샘플요청유무 : " + smplReqYn);
		System.out.println("자격 요청 유무 : " + qlfcReqYn);
		System.out.println("주문 프로세스 : " + custOrdProcCont);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
		String oprCns = null;
		
		SmsMsUserVO smsMsUserVO = new SmsMsUserVO();
		smsMsUserVO.setUserAlasCnsNm(userAlasCnsNm); 								//  SMS_MS_USER 에 담당자 중국어 화명 있는지 검색
		List <SmsMsUserVO> smsMsUserVOList = orderService.selectSmsMsUser(smsMsUserVO);
		if(smsMsUserVOList.size()==1){		// 몇개 더 있을수도 있지만, 여러개가 검색되면 차라리 mapping 을 안 하는 것이 낫다.
			SmsMsOrdUserVO smsMsOrdUserVO = new SmsMsOrdUserVO();
			smsMsOrdUserVO.setOrdNo(ordNo);
			oprCns = smsMsUserVOList.get(0).getUserAlasCnsNm();
		}
		
		// 변수들을 OrderDetailVO 에 집어 넣는다.
		OrderDetailVO orderDetailVO = new OrderDetailVO();
		orderDetailVO.setOrdNo(ordNo);
		orderDetailVO.setOprCns(oprCns);             // userAlasCnsNm   를 가지고, DB에 검색해서 있으면 매핑, 없으면 NO 매핑
//		orderDetailVO.setOprKr(oprKr);				  // 필요 없음.
		orderDetailVO.setCustId(custId);
		orderDetailVO.setOrdReqDt(ordReqDt);
		orderDetailVO.setOrdHopeArvlDt(ordHopeArvlDt);
//		orderDetailVO.setStdXchrAmt(stdXchrAmt);								// 엑셀에 존재 X
//		orderDetailVO.setStdXchrKindCd(stdXchrKindCd);						// 엑셀에 존재 X	
//		orderDetailVO.setPymtPrvdModeCont(pymtPrvdModeCont);			// 엑셀에 존재 X
		orderDetailVO.setDlvModeCd(dlvModeCd);
		orderDetailVO.setDlvDestCd(dlvDestCd);
		orderDetailVO.setPoSchdDt(poSchdDt);
//		orderDetailVO.setOrdEstmDt(ordEstmDt);									// 엑셀에 존재 X
//		orderDetailVO.setOrdExpDt(ordExpDt);										// 엑셀에 존재 X
		orderDetailVO.setCtrtTmplYn(ctrtTmplYn);
		orderDetailVO.setSmplReqYn(smplReqYn);
//		orderDetailVO.setPoSchdDt(poSchdDt);									// 엑셀에 존재 X
		orderDetailVO.setQlfcReqYn(qlfcReqYn);
		orderDetailVO.setCustOrdProcCont(custOrdProcCont);
//		orderDetailVO.setOrdMemoCont(ordMemoCont);							// 엑셀에 존재 X
		orderDetailVO.setOrdMemoCont(ordMemoCont);
		
		LOGGER.debug("2.1.4.1 엑셀에서 주문의 SMS_MS_ORD_GUDS 리스트를 뽑아온다." );
		int rows = sheet.getPhysicalNumberOfRows();
		System.out.println("row 갯수 : " + rows);

		
		
		int tempNum=1;   					//ord_guds_seq에 넣을 숫자 관리용
		ordNo = ordNo;						//ordNo
		String ord_guds_seq=null;   		//NO
		String gudsId=null;					//상품id   -   b5c gudsId 와는 별개.   이 테이블 자체의 유일키 개념
		String ordGudsUpcId=null;       		//상품바코드		
		String ordGudsCnsNm=null;  		//중문 상품명
		String ordGudsQty=null;     		//상품 수량, (예상요청)수량
		String ordGudsSizeVal=null;		//주문상품크기값 ,규격
		String ordGudsSalePrc=null;		//주문상품판매가(PO단가USD)
		String ordGudsUrlAddr=null;	    // //주문상품url주소, 링크

		List<SmsMsOrdGudsVO> smsMsOrdGudsVOList = new ArrayList<SmsMsOrdGudsVO>();
		for(int i=6; i<rows-1; i++){
			ordGudsUpcId = StringUtil.excelGetCell(sheet.getRow(i).getCell(1));
			ordGudsCnsNm = StringUtil.excelGetCell(sheet.getRow(i).getCell(2));
			ordGudsQty = StringUtil.excelGetCell(sheet.getRow(i).getCell(3));
			ordGudsSizeVal =  StringUtil.excelGetCell(sheet.getRow(i).getCell(4)); 
			ordGudsSalePrc = StringUtil.excelGetCell(sheet.getRow(i).getCell(5));
			ordGudsUrlAddr = StringUtil.excelGetCell(sheet.getRow(i).getCell(7));
			
			// 상품 정보 중에서, (gudsUpcId) 가 빠지면 의미가 없다.
			if(ordGudsUpcId!=null && "".equals(ordGudsUpcId)!=true){
				ord_guds_seq =  String.valueOf(tempNum++);
				SmsMsOrdGudsVO smsMsOrdGudsVO = new SmsMsOrdGudsVO();											// SMS_MS_ORD_GUDS 에 넣을 VO
				smsMsOrdGudsVO.setOrdNo(ordNo);
				smsMsOrdGudsVO.setOrdGudsSeq(ord_guds_seq);
				smsMsOrdGudsVO.setOrdGudsMpngYn("N");
				smsMsOrdGudsVO.setOrdGudsUpcId(ordGudsUpcId);
				smsMsOrdGudsVO.setOrdGudsCnsNm(ordGudsCnsNm);
				smsMsOrdGudsVO.setOrdGudsQty(ordGudsQty);
				smsMsOrdGudsVO.setOrdGudsSalePrc(ordGudsSalePrc);
				smsMsOrdGudsVO.setOrdGudsUrlAddr(ordGudsUrlAddr);
				
				System.out.println(ord_guds_seq+"-"+ordGudsUpcId+"-"+ordGudsCnsNm+"-"+ordGudsQty+"-"+ordGudsSizeVal+"-"+ordGudsSalePrc+"-"+ordGudsUrlAddr);
				smsMsOrdGudsVOList.add(smsMsOrdGudsVO);
				LOGGER.debug(smsMsOrdGudsVO.toString());
			}
		}
		LOGGER.debug("2.1.4.2.======SMS_MS_ORD_GUDS  List Load==========완료" );
		//END////////////////////////////////////// 스페셜 오더, 견적서 엑셀 로드 - KJY ///////////////////////////////////////////END

		//		orderDetailVO    ->  오더 정보
		System.out.println("Excel 에서 읽어온 orderDetailVO ");
		System.out.println(orderDetailVO.toString());

		//		smsMsOrdGudsVOList     -> 상품정보 리스트	
		System.out.println("Excel 에서 읽어온 smsMsOrdGudsVOList ");
		for(int i=0; i<smsMsOrdGudsVOList.size();i++){
			System.out.println(smsMsOrdGudsVOList.get(i).toString());
		}

		//
		
		

		
		
		

		//1-1.담당자를 고를수 있는 SmsMsUser (중국담당자list/ 한국담당자list)
		SmsMsUserVO prSmsMsUserVO= new SmsMsUserVO();
		
		List<SmsMsUserVO> SmsMsUserList = userService.selectSmsMsUser(prSmsMsUserVO);	//비어있는SmsMsUserVO를 매개변수로 넣으면 전체검색 
		List<SmsMsUserVO> cnsOprList= new ArrayList<SmsMsUserVO>();		//중국담당자리스트
		List<SmsMsUserVO> krOprList= new ArrayList<SmsMsUserVO>();			//한국담당자리스트
		
		
		for(SmsMsUserVO vo : SmsMsUserList){
			if(("N000530100").equals(vo.getOgnzDivCd())){			//N000530100 상해팀
				cnsOprList.add(vo);
				
			}
			else if(("N000530200").equals(vo.getOgnzDivCd())){		//N000530200 한국팀
				krOprList.add(vo);
			}
		}
		//1-2.기준화폐(stdXchrKindCd)
		List<CodeVO> stdXchrKindCdList = orderService.selectTbmsCmnCd("N00059");	//N00059 기준환율코드
		//1-3.견적조건(dlvMode)
		List<CodeVO> dlvModeCdList = orderService.selectTbmsCmnCd("N00052");	//N00052 배송방식코드
		//1-4.항구(dlvDest)
		List<CodeVO> dlvDestCdList = orderService.selectTbmsCmnCd("N00051");	//N00051 배송지도착코드
		
		
		//값으로 들어온 견적조건을 코드값으로 변경
		if(orderDetailVO.getDlvModeCd()!=null){
			for(CodeVO vo : dlvModeCdList){
				if(orderDetailVO.getDlvModeCd().equals(vo.getCdVal())){
					orderDetailVO.setDlvModeCd(vo.getCd());
				}
			}
		}
		
		
		//값으로 들어온 항구를 코드값으로 변경
		if(orderDetailVO.getDlvDestCd()!=null){
			for(CodeVO vo : dlvDestCdList){
				if(orderDetailVO.getDlvDestCd().equals(vo.getCdVal())){
					orderDetailVO.setDlvDestCd(vo.getCd());
				}
			}
		}
		
		//값으로 들어온 화명을 메일값으로 변경
		if(orderDetailVO.getOprCns()!=null){
			for(SmsMsUserVO vo : cnsOprList){
				if(orderDetailVO.getOprCns().equals(vo.getUserAlasCnsNm())){
					orderDetailVO.setOprCns(vo.getUserEml());
				}
			}
		}
		
		
		//5.model에 각종 정보를 담는다.
		model.addAttribute("ordNo", ordNo);		//주문번호	
		//selectBox구성요소
		model.addAttribute("cnsOprList",cnsOprList);		//중국팀선택
		model.addAttribute("krOprList",krOprList);		//한국팀선택
		model.addAttribute("stdXchrKindCdList",stdXchrKindCdList);		//기준화폐(stdXchrKindCd)
		model.addAttribute("dlvModeCdList",dlvModeCdList);		//견적조건(dlvMode)
		model.addAttribute("dlvDestCdList",dlvDestCdList);		//항구(dlvDest)
		//값요소
		model.addAttribute("orderDetail",orderDetailVO);	//주문상세정보
		model.addAttribute("smsMsOrdGudsList", smsMsOrdGudsVOList);	//주문상품리스트
		model.addAttribute("gudsListSize", smsMsOrdGudsVOList.size());	//주문상품리스트
		return "orderDetail";
	}
	//저장
	@RequestMapping(value="/orderDetailSave")
	public String orderDetailSave(OrderDetailVO orderDetailVo, SmsMsOrdGudsVO smsMsOrdGudsVO, String ordNo, int gudsListSize, String wrtrEml,Model model) throws Exception{
		
		//DT의 형태를 date로 변경
		orderDetailVo.setOrdEstmDt(StringUtil.dateToDt(orderDetailVo.getOrdEstmDt()));
		orderDetailVo.setOrdExpDt(StringUtil.dateToDt(orderDetailVo.getOrdExpDt()));
		orderDetailVo.setOrdHopeArvlDt(StringUtil.dateToDt(orderDetailVo.getOrdHopeArvlDt()));
		orderDetailVo.setOrdReqDt(StringUtil.dateToDt(orderDetailVo.getOrdReqDt()));
		orderDetailVo.setPoSchdDt(StringUtil.dateToDt(orderDetailVo.getPoSchdDt()));
		
		
		//상품정보에 ,가 들어가면 에러발생의 가능성이있음 <=validate 처리
		List<SmsMsOrdGudsVO> smsMsOrdGudsList= new ArrayList<SmsMsOrdGudsVO>();	
		String[] ordGudsSeq= new String[0];
		String[] ordGudsUpcId = new String[0]; 
		String[] ordGudsCnsNm= new String[0];
		String[] ordGudsQty= new String[0];
		String[] ordGudsSizeVal= new String[0];
		String[] ordGudsOrgPrc= new String[0];
//		String[] ordGudsSalePrc= new String[0];
		String[] ordGudsUrlAddr= new String[0];
		String[] gudsId= new String[0];
		orderDetailVo.setOrdNo(ordNo);
	
		//각 상품 정보를 파싱한다. 		
		if(gudsListSize>0)	{		//if(smsMsOrdGudsVO.getOrdGudsSeq()!=null){			//시퀀스번호 값이 null인경우 상품이 들어오지 않았음을 의미
			String gudsSeq=smsMsOrdGudsVO.getOrdGudsSeq().replace("," , " , ");
			String gudsUpcId=smsMsOrdGudsVO.getOrdGudsUpcId().replace("," , " , ");
			String gudsCnsNm=smsMsOrdGudsVO.getOrdGudsCnsNm().replace("," , " , ");
			String gudsQty=smsMsOrdGudsVO.getOrdGudsQty().replace("," , " , ");
			String gudsSizeVal=smsMsOrdGudsVO.getOrdGudsSizeVal().replace("," , " , ");
			String gudsOrgPrc= smsMsOrdGudsVO.getOrdGudsOrgPrc().replace("," , " , ");
//			String gudsSalePrc= smsMsOrdGudsVO.getOrdGudsSalePrc().replace("," , " , ");
			String gudsUrlAddr=smsMsOrdGudsVO.getOrdGudsUrlAddr().replace("," , " , ");
			String gid=smsMsOrdGudsVO.getGudsId().replace("," , " , ");
			
			
			ordGudsSeq	=gudsSeq.split(",");
			ordGudsUpcId=gudsUpcId.split(",");			//바코드
			ordGudsCnsNm = gudsCnsNm.split(",");		//상품명	
			ordGudsQty  =gudsQty.split(",");			//상품요청수량
			ordGudsSizeVal =gudsSizeVal.split(",");		//상품규격
			ordGudsOrgPrc = gudsOrgPrc.split(",");		//상품가격
//			ordGudsSalePrc = gudsSalePrc.split(",");		//상품가격
			
			gudsId=gid.split(",");
			//String[] gudsInbxQty = smsMsOrdGudsVO.getGudsInbxQty().split(",");	//인박스수량, db에 존재안함
			ordGudsUrlAddr =gudsUrlAddr.split(",");
		}
		
		
		System.out.println(orderDetailVo);			//상품정보 
		System.out.println(smsMsOrdGudsVO);		//상품 VO
		System.out.println(ordNo);						//업데이트될 상품번호
		System.out.println(gudsListSize);			//상품의 개수 
		
		for(String s : ordGudsSeq){
			System.out.println("s " +s);
		}
		
		
		
		for(int i=0; i<gudsListSize; i++){
			SmsMsOrdGudsVO vo =new SmsMsOrdGudsVO();
			vo.setOrdNo(ordNo);
			vo.setOrdGudsSeq(ordGudsSeq[i].trim());
			vo.setOrdGudsUpcId(ordGudsUpcId[i].trim());
			vo.setOrdGudsCnsNm(ordGudsCnsNm[i].trim());
			vo.setOrdGudsQty(ordGudsQty[i].trim());
			vo.setOrdGudsSizeVal(ordGudsSizeVal[i].trim());
			vo.setOrdGudsOrgPrc(ordGudsOrgPrc[i].trim());
//			vo.setOrdGudsSalePrc(ordGudsSalePrc[i].trim());
			vo.setOrdGudsUrlAddr(ordGudsUrlAddr[i].trim());
			vo.setGudsId(gudsId[i].trim());
			smsMsOrdGudsList.add(vo);
			
		}
		
		System.out.println(orderDetailVo);
		System.out.println(smsMsOrdGudsList);
		
		orderService.updateSmsMsOrdGudsDetail(orderDetailVo,smsMsOrdGudsList,wrtrEml   );
		

		
		return "redirect:orderDetailView.do?ordNo="+ordNo+"&reload=YES";
	}
	
	@ResponseBody
	@RequestMapping(value="/orderDetailFileUpload" , method = RequestMethod.POST)
	public FileResultVO orderDetailFileUpload(MultipartHttpServletRequest request,String ordNo, String wrtrEml) throws Exception	{

		//1.로컬에 업로드한 파일 저장
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = request.getFile(itr.next());
		FileResultVO fileResultVO = uploadMultipartFileToDisk(mpf); 
		LOGGER.debug(fileResultVO.toString());
		System.out.println(fileResultVO.getSavedRealFileNm());		//원본 파일 이름
		System.out.println(fileResultVO.getSavedFileNm());				//시스템 파일이름
		System.out.println(ordNo);			//주문번호	
		System.out.println(wrtrEml);			//작성자
		
		//2.DB에 업로드 파일 정보 저장
		SmsMsOrdFileVO ordFileVo = new SmsMsOrdFileVO();
		ordFileVo.setOrdFileKindCd("N000540100");					//코드 N000540100 (주문파일종류코드 POB)
		//ordFileVo.setOrdFilepath(ordFilepath);					//경로
		ordFileVo.setOrdFileRegrEml(wrtrEml);					//등록자
		ordFileVo.setOrdFileOrgtFileNm(fileResultVO.getSavedRealFileNm());	//원래 파일이름
		ordFileVo.setOrdFileSysFileNm(fileResultVO.getSavedFileNm());  //실제 저장된 파일 이름
		ordFileVo.setOrdNo(ordNo);		//주문번호
		ordFileVo.setOrdFileSeq(orderService.selectSmsMsOrdFileSeqNext(ordNo));
		orderService.insertSmsMsOrdFile(ordFileVo);			
		
		return fileResultVO;
	}
	
	@RequestMapping(value = "/orderDetailFileDownload", method = RequestMethod.GET)
	public void doDownload(HttpServletRequest request, HttpServletResponse response, String filePath,String fileName) throws IOException {
		System.out.println("filepath : "+filePath);
		final String fullPath = OPT_B5C_DISK + filePath;
		//downloadFile(request, response, fullPath);
		
		
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// MIME Type 파일로 부터 얻어오기.
		ServletContext context = request.getSession().getServletContext();
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}
		LOGGER.debug("MIME type: " + mimeType);

		// Content 특성 정의
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// Header 값 정의
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		if(fileName!=null){
			headerValue = String.format("attachment; filename=\"%s\"", fileName);
		}
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		if(inputStream!=null)inputStream.close();
		outStream.flush();
		if(outStream!=null)outStream.close();
	}
	
	@ResponseBody
	@RequestMapping(value="/downloadExcel_Order")
	public void downloadExcel_Order(HttpServletRequest request, HttpServletResponse response,String ordNo) throws Exception{
		// get absolute path of the application
				ServletContext context = request.getSession().getServletContext();
				String appPath = context.getRealPath("") + File.separator;

				LOGGER.debug("appPath = " + appPath);

				// construct the complete absolute path of the file
				String fullPath = appPath + "WEB-INF" + File.separator + "templates" + File.separator + "ORDER_DETAIL.xlsx";

				LOGGER.info("템플릿 엑셀파일 위치 =" + fullPath);

				File templateFile = new File(fullPath);
/*				Workbook wb = WorkbookFactory.create(templateFile);
				Sheet sheet = wb.getSheetAt(0);*/
				
				XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(templateFile);
				XSSFSheet sheet = wb.getSheetAt(0);
				
				
				//1.기본정보입력 
				OrderDetailVO orderDetailVO = orderService.selectSmsMsOrdDetail(ordNo);
				
				//담당자 찾기 
				List<SmsMsUserVO> oprList = userService.selectSmsMsUserByOrdNo(ordNo);
				for(SmsMsUserVO vo : oprList){
					if(("N000530100").equals(vo.getOgnzDivCd())){			//N000530100 상해팀
						/*String oprCns =vo.getUserAlasCnsNm()+"("+vo.getUserAlasEngNm()+")";
						orderDetail.setOprCns(oprCns);*/
						orderDetailVO.setOprCns(vo.getUserEml());
					}
				}
				//1-1.견적서이름
				Row row = sheet.getRow(3);
				Cell cell = row.getCell(10);
				cell.setCellValue(orderDetailVO.getOrdNm());
				
				//1-2.기준환율
				row = sheet.getRow(4);
				cell = row.getCell(10);
				if(orderDetailVO.getStdXchrAmt()!=null)
					cell.setCellValue(orderDetailVO.getStdXchrAmt().intValue());

				//1-3.기준화폐
				row = sheet.getRow(5);
				cell = row.getCell(10);
				List<CodeVO> stdXchrKindCdList = orderService.selectTbmsCmnCd("N00059");	//N00059 기준환율코드
				if(orderDetailVO.getStdXchrKindCd()!=null){
					for(CodeVO vo : stdXchrKindCdList){
						if(orderDetailVO.getStdXchrKindCd().equals(vo.getCd())){
							orderDetailVO.setStdXchrKindCd(vo.getCdVal());
						}
					}
				}
				cell.setCellValue(orderDetailVO.getStdXchrKindCd());
				

				
				
				
				
				//1-4.견적조건
				row = sheet.getRow(6);
				cell = row.getCell(10);
				List<CodeVO> dlvModeCdList = orderService.selectTbmsCmnCd("N00052");	//N00052 배송방식코드
				//값으로 들어온 견적조건을 코드값으로 변경
				if(orderDetailVO.getDlvModeCd()!=null){
					for(CodeVO vo : dlvModeCdList){
						System.out.println(vo.getCd());
						if(orderDetailVO.getDlvModeCd().equals(vo.getCd())){
							orderDetailVO.setDlvModeCd(vo.getCdVal());
						}
					}
				}
				cell.setCellValue(orderDetailVO.getDlvModeCd());
				//1-5.견적일자
				row = sheet.getRow(7);
				cell = row.getCell(10);
				cell.setCellValue(orderDetailVO.getOrdEstmDt());
				//1-6.견적유효일자
				row = sheet.getRow(8);
				cell = row.getCell(10);
				cell.setCellValue(orderDetailVO.getOrdExpDt());
				
				
				System.out.println("엑셀에 삽입될 :"+orderDetailVO);
				
				//DB에서 상품 정보를 가져온다.
				List<SmsMsOrdGudsVO> smsMsOrdGudsList =goodsService.selectSmsMsOrdGudsByOrdNo(ordNo);


				//주문에 존재하는 상품이 Mapping이 되었을 경우 상품테이블에서 *이미지,인박스수량,바코드*값을 가져온다
				for(SmsMsOrdGudsVO vo :smsMsOrdGudsList){
					
					if("Y".equals(vo.getOrdGudsMpngYn())){
						SmsMsGudsVO smsGuds = new SmsMsGudsVO();
						smsGuds=goodsService.selectSmsMsGuds(vo.getGudsId());

						//매핑이 되었다고 나오는데 실제 상품정보가 없는 경우가 존재할수 있다.
						if(smsGuds!=null){
			 				vo.setOrdGudsUpcId(smsGuds.getGudsUpcId());				//바코드 //vo.setGudsUpcId(smsGuds.getGudsUpcId());		
							vo.setGudsInbxQty(smsGuds.getGudsInbxQty());	//인박스수량
						}else{
							vo.setOrdGudsMpngYn("N");
						}
						//GUDS_IMG_CD  N000080100 대표이미지 	N000080200 목록이미지
						List<SmsMsGudsImgVO> gudsImgList = goodsService.selectSmsMsGudsImg(vo.getGudsId());
						if(!gudsImgList.isEmpty()){ //OPT_B5C_DISK
							if(!StringUtil.isNullOrEmpty(gudsImgList.get(0).getGudsImgSysFileNm())){
								vo.setImgSrcPath(OPT_B5C_DISK+gudsImgList.get(0).getGudsImgSysFileNm());
							}
						}
					}
					System.out.println("화면에 표시될 VO :"+vo);
				}//End_for(SmsMsOrdGudsVO vo :smsMsOrdGudsList)
				
				int sourceRowNum = 11;		//참고할 스타일행
				int destinationRowNum = 12;	//~부터 생성
				int addRowCountNum =smsMsOrdGudsList.size()-1 ;		//생성할 라인수(상품4개->3개추가, 기존1줄)

				
				
				
				Row sourceRow = sheet.getRow(sourceRowNum);
				
				
				Cell newCell0 = sourceRow.getCell(0);		//브랜드
				Cell newCell1 = sourceRow.getCell(1);		//이미지
				if(smsMsOrdGudsList.get(0).getImgSrcPath()!=null){
					addPricture(smsMsOrdGudsList.get(0).getImgSrcPath(),1,(11),wb,sheet);
				}
				Cell newCell2 = sourceRow.getCell(2);		//바코드
				newCell2.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsUpcId());
				Cell newCell3 = sourceRow.getCell(3);		//상품명칭
				/*newCell3.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsKorNm());*/
				newCell3.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsCnsNm());
				Cell newCell4 = sourceRow.getCell(4);		//상품명칭(중)
				newCell4.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsCnsNm());
				Cell newCell5 = sourceRow.getCell(5);		//인박스수량
				newCell5.setCellValue(smsMsOrdGudsList.get(0).getGudsInbxQty());
				Cell newCell6 = sourceRow.getCell(6);		//단가
				//newCell6.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsSalePrc());
				newCell6.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsOrgPrc());
				Cell newCell7 = sourceRow.getCell(7);		//주문수량
				newCell7.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsQty());
				Cell newCell8 = sourceRow.getCell(8);		//금액
				newCell8.setCellFormula("G12*H12");
				Cell newCell9 = sourceRow.getCell(9);		//URL
				newCell9.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsUrlAddr());
				Cell newCell10= sourceRow.getCell(10);		//담당자
				newCell10.setCellValue(orderDetailVO.getOprCns());
				
				if(addRowCountNum>0){
					sheet.shiftRows(12, 16, addRowCountNum);
				}
				System.out.println("cellNum : "+sourceRow.getLastCellNum());
				for(int i=0; i<addRowCountNum;i++){
					Row newRow = sheet.createRow(12+i);
					newRow.setHeight(sourceRow.getHeight());
					
					for(int j=0;j<sourceRow.getLastCellNum(); j++){
						Cell oldCell = sourceRow.getCell(j);			
						Cell newCell = newRow.createCell(j);
						
			            // Copy style from old cell and apply to new cell
			            CellStyle newCellStyle = wb.createCellStyle();
			            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());			//스타일을 선언하고 기존 스타일을 넣는다
			            newCell.setCellStyle(newCellStyle);	
			            
			           
			            switch (j) {
			                case 0:		//브랜드ID
			                    //newCell.setCellValue();
			                    break;
			                case 1:		//이미지
			                	if(smsMsOrdGudsList.get(i+1).getImgSrcPath()!=null){
			                		addPricture(smsMsOrdGudsList.get(i+1).getImgSrcPath(),1,(i+12),wb,sheet);
			                	}
			                    break;
			                case 2:		//바코드
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsUpcId()!=null){
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsUpcId());
			                	}
			                    break;
			                case 3:		//상품명칭
			                	/*if(smsMsOrdGudsList.get(i+1).getOrdGudsKorNm()!=null){
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsKorNm());
			                	}		*/
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsCnsNm()!=null){
			                		System.out.println(smsMsOrdGudsList.get(i+1).getOrdGudsCnsNm());
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsCnsNm());
			                	}	
			                    break;
			                case 4:		//상품명칭(중)
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsCnsNm()!=null){
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsCnsNm());
			                	}			                    
			                    break;
			                case 5:		//인박스수량
			                    newCell.setCellValue(smsMsOrdGudsList.get(i+1).getGudsInbxQty());
			                    break;
			                case 6:		//단가
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsSalePrc()!=null){
			                		newCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			                		//newCell.setCellValue(Double.parseDouble(smsMsOrdGudsList.get(j).getOrdGudsSalePrc()));;
			                		//newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsSalePrc());
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsOrgPrc());
			                	}
			                    break;
			                case 7:		//주문수량
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsQty()!=null){
			                		newCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsQty());
			                	}
			                    break;
			                case 8:		//금액
			                		int intk=destinationRowNum+1+i;
			                		String k=Integer.toString(intk); 
			                		newCell.setCellFormula("G"+k+"*H"+k);                		
			           
			                    break;
			                case 9:		//url
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsUrlAddr()!=null){
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsUrlAddr());
			                	}
			                    break;
			                case 10:		
			                	newCell.setCellValue(orderDetailVO.getOprCns());
			                    break;
			            }//end switch
					}//end for j
				}//end for i 

				//마지막 총액
				if(addRowCountNum>0){
					Row lastRow = sheet.getRow(12+addRowCountNum);
					Cell lastCell=lastRow.getCell(8);
					lastCell.setCellFormula("SUM(I12:I"+(12+addRowCountNum)+")");
					
				}
				// 다운로드 될 템플릿 파일 이름 형식 : ORDER_DETAIL+_년월일시분초.xls
				String downloadedTemplateName = "ORDER_DETAIL" + "_" + DateUtil.sGetCurrentTime("yyyyMMdd_HHmm_ss") + ".xlsx";
				// 엑셀 다운로드
				writeExcelAttachmentForDownload(response, downloadedTemplateName, wb);

	}

	
	private void addPricture(String fileName, int col, int row, XSSFWorkbook workbook,XSSFSheet sheet) throws IOException {
		InputStream is = new FileInputStream(fileName);
		byte[] bytes = IOUtils.toByteArray(is);
		
		System.out.println(workbook);
		
		int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);		//
		
		
		
		is.close();
		
		Drawing drawing = sheet.createDrawingPatriarch();
		
		CreationHelper helper = workbook.getCreationHelper();
		ClientAnchor anchor = helper.createClientAnchor();
		
		anchor.setCol1(col);
	    anchor.setRow1(row);

	    Picture pict = drawing.createPicture(anchor, pictureIdx);
	    
	    BufferedImage bimg = ImageIO.read(new ByteArrayInputStream(pict.getPictureData().getData()));
        
        //System.out.println(bimg.getWidth() + ", " + bimg.getHeight());
        
	   
        double scale =(double)85/(double)bimg.getWidth();
        System.out.println("scale : "+scale);
	    
	    pict.resize(scale);    
	}
	
	@ResponseBody
	@RequestMapping(value="/downloadExcel_PO")
	public void downloadExcel_PO(HttpServletRequest request, HttpServletResponse response,String ordNo) throws Exception{
		// get absolute path of the application
				ServletContext context = request.getSession().getServletContext();
				String appPath = context.getRealPath("") + File.separator;

				LOGGER.debug("appPath = " + appPath);

				// construct the complete absolute path of the file
				String fullPath = appPath + "WEB-INF" + File.separator + "templates" + File.separator + "PURCHASE_PO.xlsx";

				LOGGER.info("템플릿 엑셀파일 위치 =" + fullPath);

				File templateFile = new File(fullPath);
				List<Workbook> wbList = new ArrayList<Workbook>();
				List<String> excelNmList = new ArrayList<String>();
				
				
				//1.cell을 채우는데 필요한 데이터가져오기
				SmsMsEstmVO poVo = orderService.selectSmsMsEstmVO(ordNo);
				List<SmsMsEstmGudsVO> poGudsList= goodsService.selectSmsMsEstmGuds(ordNo);
				List<SmsMsEstmGudsVO> poPrvdList= goodsService.selectSmsMsEstmGudsGroupByPrvd(ordNo);
				
				int sourceRowNum =17;				//참고할 스타일행
				
				int prvdSize = poPrvdList.size();
				int gudsSize = poGudsList.size();
				
				int gudsNo=0;	
				//String rowNoStr="";
				
				//해당 상품 공급자 수 만큼 매입PO 엑셀을 만든다.
				for(int prvdIndex=0;prvdIndex<prvdSize ;prvdIndex++){
					int rowNo =sourceRowNum;				//소스넘버체크 
					
					System.out.println(poPrvdList.get(prvdIndex).getOrdGudsPrvdNm());
					Workbook wb =  WorkbookFactory.create(templateFile);
					
					Sheet sheet = wb.getSheetAt(0);
					Row sourceRow = sheet.getRow(sourceRowNum);			//스타일을 가지고 있는 행
					gudsNo=0;			//삽입될 상품의 개수 	

					
					String poNo="IZK"+poVo.getPoDt()+String.format("%03d", (prvdIndex+1));
					
					//1-1.PO NO (IZK+날짜+번호)
					Row row = sheet.getRow(2);
					Cell cell = row.getCell(9);
					cell.setCellValue(poNo);	
					
					//1-2.PO날짜 
					row=sheet.getRow(3);
					cell = row.getCell(9);
					cell.setCellValue(StringUtil.dtToDate(poVo.getPoDt()));	
					
					//1-3.사업자번호
					row=sheet.getRow(6);
					cell = row.getCell(3);
					cell.setCellValue(poPrvdList.get(prvdIndex).getOrdGudsPrvdCrn());
					
					//1-4.상호
					row=sheet.getRow(7);
					cell = row.getCell(3);
					cell.setCellValue(poPrvdList.get(prvdIndex).getOrdGudsPrvdNm());
					
					
					
					for(int gudsIndex=0; gudsIndex<gudsSize;gudsIndex++){
						if(poPrvdList.get(prvdIndex).getOrdGudsPrvdCrn().equals(poGudsList.get(gudsIndex).getOrdGudsPrvdCrn())){		//사업자번호가 같은경우 삽입
							gudsNo++;
						}
					}
					
					sheet.shiftRows(18, 34, gudsNo);		//상품이동
					
					for(int gudsIndex=0; gudsIndex<gudsNo;gudsIndex++){	
				
							SmsMsEstmGudsVO gudsVo = poGudsList.get(gudsIndex);		//gudsVO에 현재 po상품 삽입	
							Row newRow = sheet.createRow(sourceRowNum+1+gudsIndex);			//새로운row 생성 
							newRow.setHeight(sourceRow.getHeight());
							Row nowRow=sheet.getRow(sourceRowNum+gudsIndex);			
								for(int j=1;j<sourceRow.getLastCellNum(); j++){
									
									Cell oldCell = sourceRow.getCell(j);		
									Cell newCell = newRow.createCell(j);
									Cell nowCell =nowRow.getCell(j);
						            // Copy style from old cell and apply to new cell
						            CellStyle newCellStyle = wb.createCellStyle();						          
						            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());			//스타일을 선언하고 기존 스타일을 넣는다
						            newCell.setCellStyle(newCellStyle);	
						            
						            //실제 데이터 삽입
						            switch (j) {
						            case 1:
						            	nowCell.setCellValue(gudsIndex+1);
						            	break;
					                case 2:		//2-1.바코드
					                	nowCell.setCellValue(gudsVo.getGudsUpcId());
					                    break;
					                case 3:		//2-2.상품명
					                	nowCell.setCellValue(gudsVo.getOrdGudsCnsNm());		
					                    break;
					                case 6:		//2-3.주문수량
					                	nowCell.setCellValue(Integer.parseInt(gudsVo.getOrdGudsQty()));
					                    break;
					                case 7:		//2-4.매입단가
					                	nowCell.setCellValue(Double.parseDouble(gudsVo.getOrdGudsOrgPrc()));
					                    break;
					                case 8:		//2-5.매입합계
					                	rowNo +=1;
					                	//rowNoStr=Integer.toString(rowNo); 
					                	nowCell.setCellFormula("G"+rowNo+"*H"+rowNo);                    
					                    break;
						            }//end switch
								}//end for j
					}//end for gudsIndex
					
					
					//3-1. 합계수량
					row = sheet.getRow(rowNo+1);
					cell = row.getCell(7);
					cell.setCellFormula("SUM(G18:G"+(rowNo+1)+")");
							
					//3-2. 합계금액(VAT 별도)
					row = sheet.getRow(rowNo+2);
					cell = row.getCell(7);
					cell.setCellFormula("SUM(I18:I"+(rowNo+1)+")");
					
					//4. 특정셀 셀병합(상품명,매입합계)	 : 읽을 수 없는 내용이 있습니다라는 경고를 발생시킨다.
					for(int gudsIndex=0; gudsIndex<gudsNo;gudsIndex++){
						sheet.addMergedRegion(new CellRangeAddress(sourceRowNum+gudsIndex,sourceRowNum+gudsIndex , 3, 4));
						sheet.addMergedRegion(new CellRangeAddress(sourceRowNum+gudsIndex,sourceRowNum+gudsIndex , 8, 9));
						
					}
					
					
					String downloadedTemplateName = "PURCHASE_PO" + "_" + poNo + ".xlsx";
					wbList.add(wb);	//생성된 엑셀파일을 리스트에 담는다.
					excelNmList.add(downloadedTemplateName);		//생성된 엑셀파일의 이름을 리스트에 담는다
				}//end for prvdIndex
				

				writeExcelListForDownload(response, excelNmList, wbList);
	}
}
