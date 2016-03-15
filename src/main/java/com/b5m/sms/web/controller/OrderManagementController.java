package com.b5m.sms.web.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.b5m.sms.biz.service.GoodsService;
import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.common.security.User;
import com.b5m.sms.common.util.StringUtil;
import com.b5m.sms.vo.ExcelClientReqGudsVO;
import com.b5m.sms.vo.SmsMsGudsImgVO;
import com.b5m.sms.vo.SmsMsGudsVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;
import com.b5m.sms.vo.SmsMsOrdVO;
import com.b5m.sms.vo.TbMsOrdBatchVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class OrderManagementController  extends AbstractFileController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderManagementController.class);
		
	
	@Resource(name = "orderService")
	public OrderService orderService;

	@Resource(name = "goodsService")
	public GoodsService goodsService;
	
	@RequestMapping(value="/orderManagementView")
	public String orderManagement(HttpSession session) throws Exception{
		String result = "orderManagement";
		smsMsOrdBatch();
//		try {
//			
//			//일괄로 다운 받는데 최신을 표시하는 값이 db에 필요할듯 
//			List<SmsMsGudsImgVO> imgList = goodsService.selectSmsMsGudsImgAll();
//			for(SmsMsGudsImgVO vo :imgList){
//				if(vo.getGudsImgCdnAddr()!=null){
//					URL url = new URL(vo.getGudsImgCdnAddr());
//					//String fileName = url.getFile();
//				   //String destName = OPT_B5C_DISK + fileName.substring(fileName.lastIndexOf("/"));
//				   String destName = OPT_B5C_DISK +vo.getGudsImgSysFileNm();
//				   System.out.println(destName);
//				 
//				   InputStream is = url.openStream();
//				   OutputStream os = new FileOutputStream(destName);
//				 
//				   byte[] b = new byte[2048];
//				   int length;
//				 
//				   while ((length = is.read(b)) != -1) {
//				      os.write(b, 0, length);
//				   }
//				 
//				   is.close();
//				   os.close();
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/orderManagementSearch.ajax")
	public List<SmsMsOrdVO> orderManagementSearch(SmsMsOrdVO smsMsOrdVO, String rowInput, String pageInput ,HttpSession session, Model model, String filters) throws Exception {
		System.out.println("-------------------------------------------------------------");
		if(filters != null){
			String a = filters.replaceAll("&quot", "\"");
			String b = a.replaceAll(";", "");
			System.out.println("filters : " + b);
			
		}
		System.out.println(smsMsOrdVO.toString());
		System.out.println("rowInput : "+rowInput);
		System.out.println("pageInput : "+pageInput);
		
		int row=0;
		int page=0;
		if(rowInput==null || "".equals(rowInput)){
			row=50;
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
			if(smsMsOrdVOList.get(i).getBactPrvdDt() != null && smsMsOrdVOList.get(i).getBactPrvdAmt() !=null ){
				smsMsOrdVOList.get(i).setBactPrvdDtPlusbactPrvdAmt(""+smsMsOrdVOList.get(i).getBactPrvdDt()+" "+smsMsOrdVOList.get(i).getBactPrvdAmt());
				}
			smsMsOrdVOList.get(i).setPage(page);
			smsMsOrdVOList.get(i).setRow(row);
		}

		return smsMsOrdVOList;

	}
	@ResponseBody
	@RequestMapping("/orderManagementSave.ajax")
	public String orderManagementSave(SmsMsOrdVO smsMsOrdVO) throws Exception{
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
		
		if(smsMsOrdVO.getPaptDpstDt() != null) smsMsOrdVO.setPaptDpstDt(smsMsOrdVO.getPaptDpstDt().replace("-",""));
		if(smsMsOrdVO.getWrhsDlvDt() != null) smsMsOrdVO.setWrhsDlvDt(smsMsOrdVO.getWrhsDlvDt().replace("-",""));
		if(smsMsOrdVO.getDptrDlvDt() != null) smsMsOrdVO.setDptrDlvDt(smsMsOrdVO.getDptrDlvDt().replace("-",""));
		if(smsMsOrdVO.getArvlDlvDt() != null) smsMsOrdVO.setArvlDlvDt(smsMsOrdVO.getArvlDlvDt().replace("-",""));
		if(smsMsOrdVO.getPoDlvDt() != null) smsMsOrdVO.setPoDlvDt(smsMsOrdVO.getPoDlvDt().replace("-",""));
		if(smsMsOrdVO.getRaptDpstDt() != null) smsMsOrdVO.setRaptDpstDt(smsMsOrdVO.getRaptDpstDt().replace("-",""));
		
		if("".equals(smsMsOrdVO.getPaptDpstDt())) smsMsOrdVO.setPaptDpstDt(null);
		if("".equals(smsMsOrdVO.getRaptDpstDt())) smsMsOrdVO.setRaptDpstDt(null);
		if("".equals(smsMsOrdVO.getWrhsDlvDt())) smsMsOrdVO.setWrhsDlvDt(null);
		if("".equals(smsMsOrdVO.getWrhsDlvDestCd())) smsMsOrdVO.setWrhsDlvDestCd(null);
		if("".equals(smsMsOrdVO.getDptrDlvDt())) smsMsOrdVO.setDptrDlvDt(null);
		if("".equals(smsMsOrdVO.getDptrDlvDestCd())) smsMsOrdVO.setDptrDlvDestCd(null);
		if("".equals(smsMsOrdVO.getArvlDlvDt())) smsMsOrdVO.setArvlDlvDt(null);
		if("".equals(smsMsOrdVO.getArvlDlvDestCd())) smsMsOrdVO.setArvlDlvDestCd(null);
		if("".equals(smsMsOrdVO.getPoDlvDt())) smsMsOrdVO.setPoDlvDt(null);
		if("".equals(smsMsOrdVO.getPoDlvDestCd())) smsMsOrdVO.setPoDlvDestCd(null);
		if("".equals(smsMsOrdVO.getB5mBuyCont())) smsMsOrdVO.setB5mBuyCont(null);
		
		
		orderService.updateSmsMsOrdInOrderManagementView(smsMsOrdVO);
		return "success";
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
		
		//엑셀 업로드 할 때, 히스토리에 남기기 위해 user 정보 필요.
	    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 서비스 진행.
		orderService.insertExcelSmsMsOrdNSmsMsOrdGuds(sheet, user);
		LOGGER.debug("2.1.=============================완료" );
		return "redirect:/orderManagementView.do";
	}
	
	// ordManagement.jsp  에서 엑셀다운로드 클릭하면, JQgrid 를 그대로 가져와서 엑셀 다운로드 되게 만들어준다.
	@ResponseBody
	@RequestMapping(value="/orderManagementExcelDownload.ajax")
	public GenericExcelView orderManagementExcelDownload(@RequestParam Map<String, String> params, Map<String, Object> model, HttpServletResponse response,@RequestParam(value="dataList")String dataList) throws Exception{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=members.xls");
		
		String [] colNames = {"Order Number","申请日期","客户名称","订购商品", "查看详情","交易规模","上海负责人","韩国负责人","订购路径","状态","状态详情","最终状态","商品供应商汇款","首付日期","首付金额","首付百分比","入库日期","入库地点","出港日期","出港地点","到岸日期","到岸地点","P/O日期","P/O地点","余付","余款结算日期","余款百分比","是否在帮韩品购买"};
		List<String> colName = new ArrayList<String>();
		for(int i=0; i<colNames.length ; i++){
        	colName.add(colNames[i]);
        }
        List<String[]> colValue = new ArrayList<String[]>();

        String a = dataList.replaceAll("&quot", "\"");
        String b = a.replaceAll(";", "");

        List<SmsMsOrdVO> smsMsOrdVOList = null;
        smsMsOrdVOList = orderService.orderManageMentExcelDownload(b);

//		List<SmsMsOrdVO> smsMsOrdVOListToExcel = null;
//		smsMsOrdVOListToExcel = orderService.selectSmsMsOrdForOrderManamentView(smsMsOrdVO);

		
        System.out.println("-------------------------------------------------------------------------------");

		String[] arr1 = { "11111", "22222", "33333", "44444", "55555" };
		String[] arr2 = { "aaaaa", "bbbbb", "ccccc", "ddddd", "eeeee" };
		String[] arr3 = { "가가가", "나나나", "다다다", "라라라", "마마마" };
//		modelMap.get(key)
		colValue.add(arr1);
		colValue.add(arr2);
		colValue.add(arr3);
//
		model.put("excelName", "test");
		model.put("colName", colName);
		model.put("colValue", colValue);

		return new GenericExcelView();
    }
	
}
