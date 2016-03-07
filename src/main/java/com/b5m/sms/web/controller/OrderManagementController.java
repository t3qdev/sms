package com.b5m.sms.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.b5m.sms.biz.service.GoodsService;
import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.common.util.StringUtil;
import com.b5m.sms.vo.ExcelClientReqGudsVO;
import com.b5m.sms.vo.SmsMsGudsVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;
import com.b5m.sms.vo.SmsMsOrdVO;
import com.b5m.sms.vo.TbMsOrdBatchVO;

@Controller
public class OrderManagementController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderManagementController.class);
		
	
	@Resource(name = "orderService")
	public OrderService orderService;

	@Resource(name = "goodsService")
	public GoodsService goodsService;
	
	@RequestMapping(value="/orderManagementView")
	public String orderManagement(HttpSession session) throws Exception{
		String result = "orderManagement";
		try {
			smsMsOrdBatch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/orderManagementSearch.ajax")
	public List<SmsMsOrdVO> orderManagementSearch(SmsMsOrdVO smsMsOrdVO, String rowInput, String pageInput ,HttpSession session, Model model) throws Exception {

		System.out.println("rowInput : "+rowInput);
		System.out.println("pageInput : "+pageInput);
		
		int row=0;
		int page=0;
		if(rowInput==null || "".equals(rowInput)){
			row=10;
		}else{
			row = (Integer.parseInt(rowInput));
		}
		if(pageInput==null || "".equals(pageInput)){
			page = 1;
		}else{
			page = (Integer.parseInt(pageInput));
		}
		smsMsOrdVO.setStart((page-1)*row);
		smsMsOrdVO.setRow(row);
		
		System.out.println("row : " + row);
		System.out.println("page : " +page);
		System.out.println("setStart : " +smsMsOrdVO.getStart());
		System.out.println("setEnd : " + smsMsOrdVO.getEnd());

		System.out.println(smsMsOrdVO.toString());
		List<SmsMsOrdVO> smsMsOrdVOList = null;
		smsMsOrdVOList = orderService.selectSmsMsOrdForOrderManamentView(smsMsOrdVO);
		for(int i=0; i<smsMsOrdVOList.size(); i++){
			smsMsOrdVOList.get(i).setBactPrvdDtPlusbactPrvdAmt(""+smsMsOrdVOList.get(i).getBactPrvdDt()+" "+smsMsOrdVOList.get(i).getBactPrvdAmt());
			
			System.out.println(smsMsOrdVOList.get(i).toString());
			smsMsOrdVOList.get(i).setPage(page);
			smsMsOrdVOList.get(i).setRow(row);
		}

		return smsMsOrdVOList;

	}
	@ResponseBody
	@RequestMapping("/orderManagementSave.ajax")
	public void orderManagementSave(SmsMsOrdVO smsMsOrdVO) throws Exception{
		System.out.println("controller로  들어온  vo : ");
		System.out.println("1"+smsMsOrdVO.toString());
		System.out.println("2"+smsMsOrdVO.getOrdNo()+smsMsOrdVO.getOrdSumAmt());;
		System.out.println("3"+smsMsOrdVO.getPaptDpstAmt()+smsMsOrdVO.getPaptDpstDt()+smsMsOrdVO.getPaptDpstRate());
		System.out.println("4"+smsMsOrdVO.getWrhsDlvDestCd()+smsMsOrdVO.getWrhsDlvDt());
		System.out.println("5"+smsMsOrdVO.getDptrDlvDestCd()+smsMsOrdVO.getDptrDlvDt());
		System.out.println("6"+smsMsOrdVO.getArvlDlvDestCd()+smsMsOrdVO.getArvlDlvDt());
		System.out.println("7"+smsMsOrdVO.getPoDlvDt()+smsMsOrdVO.getPoDlvDestCd());
		System.out.println("8"+smsMsOrdVO.getRaptDpstAmt()+smsMsOrdVO.getRaptDpstDt()+smsMsOrdVO.getRaptDpstRate());
		System.out.println("9"+smsMsOrdVO.getB5mBuyCont());
		

		smsMsOrdVO.setPaptDpstDt(smsMsOrdVO.getPaptDpstDt().replace("-",""));
		smsMsOrdVO.setWrhsDlvDt(smsMsOrdVO.getWrhsDlvDt().replace("-",""));
		smsMsOrdVO.setDptrDlvDt(smsMsOrdVO.getDptrDlvDt().replace("-",""));
		smsMsOrdVO.setArvlDlvDt(smsMsOrdVO.getArvlDlvDt().replace("-",""));
		smsMsOrdVO.setPoDlvDt(smsMsOrdVO.getPoDlvDt().replace("-",""));
		smsMsOrdVO.setRaptDpstDt(smsMsOrdVO.getRaptDpstDt().replace("-",""));
		
		orderService.updateSmsMsOrdInOrderManagementView(smsMsOrdVO);
		
	}
	
	//B5C 에서 주문정보(TB_MS_ORD , TB_MS_ORD_SMS) 와 그에 관련된 상품정보(opt단위까지) (TB_MS_ORD_GUDS_OPT - TB_MS_GUDDS_OPT) 를 받아와서 
	//SMS의 주문정보(TB_MS_ORD) 와 상품정보 SMS_MS_ORD_GUDS, SMS_MS_GUDS, SMS_MS_GUDS_IMG  에 INSERT 해준다.
	//발생 조건 :
	//		1. orderManagementView.do  호출 할 때마다
	//      2. 1시간 간격마다.
	@Scheduled(fixedDelay = 3600000)   //1시간마다 실행
	@RequestMapping(value="/smsMsOrdBatch")
	public void smsMsOrdBatch() throws Exception{

		LOGGER.debug("=================배치 시작=================" );

		orderService.batchSmsMsOrd();
		System.out.println("batchSmsMsOrd 배치 함수 완료");

		LOGGER.debug("=================배치 종료=================" );
	}

	
	@RequestMapping(value = "/orderManagementInsert", method = RequestMethod.POST)
	public String orderManagementInsert(@RequestParam("file") MultipartFile[] fileArray, HttpSession session, HttpServletRequest request) throws Exception {
		String result = "false";
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
		//workbook 초기화
		Workbook wb = WorkbookFactory.create(excelFile.getInputStream());
		Sheet sheet = wb.getSheetAt(1);                    // 임시로 1번 한국어 시트로 함.
		
		// 서비스 진행.
		orderService.insertExcelSmsMsOrdNSmsMsOrdGuds(sheet);
		LOGGER.debug("2.1.=============================완료" );
		return "redirect:/orderManagementView.do";
	}
	
}
