package com.b5m.sms.web.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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

import com.b5m.sms.biz.service.GoodsService;
import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.biz.service.UserService;
import com.b5m.sms.common.security.User;
import com.b5m.sms.vo.SmsMsOrdVO;
import com.b5m.sms.vo.SmsMsUserVO;
import com.b5m.sms.vo.TbMsOrdVO;

@Controller
public class OrderManagementController extends AbstractFileController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderManagementController.class);

	@Resource(name = "userService")
	public UserService userService;

	@Resource(name = "orderService")
	public OrderService orderService;

	@Resource(name = "goodsService")
	public GoodsService goodsService;

	@RequestMapping(value = "/orderManagementView")
	public String orderManagement(HttpSession session) throws Exception {
		String result = "orderManagement";

		// 이 페이지를 들어올 때마다, Batch 실행
		smsMsOrdBatch();
		return result;
	}

	@ResponseBody
	@RequestMapping("/orderManagementSearch.ajax")
	public List<SmsMsOrdVO> orderManagementSearch(SmsMsOrdVO smsMsOrdVO,
			String rowInput, String pageInput, HttpSession session,
			Model model, String filters) throws Exception {

		int row = 0;
		int page = 0;
		if (rowInput == null || "".equals(rowInput)) {
			row = 20;
		} else {
			row = (Integer.parseInt(rowInput));
		}
		if (pageInput == null || "".equals(pageInput)) {
			page = 1;
		} else {
			page = (Integer.parseInt(pageInput));
		}
		System.out.println("rowInput " + rowInput);
		System.out.println("pageInput " + pageInput);

		smsMsOrdVO.setStart((page - 1) * row);
		smsMsOrdVO.setRow(row);

		List<SmsMsOrdVO> smsMsOrdVOList = null;
		smsMsOrdVOList = orderService
				.selectSmsMsOrdForOrderManamentView(smsMsOrdVO);
		String count = orderService.selectSmsMsOrdCount(smsMsOrdVO);

		for (int i = 0; i < smsMsOrdVOList.size(); i++) {
			if (smsMsOrdVOList.get(i).getBactPrvdDt() != null
					&& smsMsOrdVOList.get(i).getBactPrvdAmt() != null) {

				DecimalFormat df = new DecimalFormat("#,###.00");
				df.format(smsMsOrdVOList.get(i).getBactPrvdAmt());
				NumberFormat.getCurrencyInstance();
				smsMsOrdVOList.get(i).setBactPrvdDtPlusbactPrvdAmt(
						""
								+ formatterDate(smsMsOrdVOList.get(i)
										.getBactPrvdDt())
								+ "  ₩ "
								+ df.format(smsMsOrdVOList.get(i)
										.getBactPrvdAmt()));
			}

			// 한국담당자가 여러명으로 변경
			List<SmsMsUserVO> oprList = userService
					.selectSmsMsUserByOrdNo(smsMsOrdVOList.get(i).getOrdNo());
			String oprKrList = "";
			for (SmsMsUserVO vo : oprList) {
				if (("N000530200").equals(vo.getOgnzDivCd())) { // N000530200
																// 한국팀
					if (!"".equals(oprKrList)) {
						oprKrList += ",";
					}
					oprKrList += vo.getUserAlasCnsNm() + "("
							+ vo.getUserAlasEngNm() + ")";
					System.out.println("oprKrList ::::::::::::" + oprKrList);
					/*
					 * String oprKr
					 * =vo.getUserAlasCnsNm()+"("+vo.getUserAlasEngNm()+")";
					 * orderDetail.setOprKr(oprKr);
					 * orderDetail.setOprKr(vo.getUserEml());
					 */
				}
			}
			smsMsOrdVOList.get(i).setKorMng((oprKrList));
			// smsMsOrdVOList.get(i).setDpstXchrCd("N000590200"); 테스트용

			smsMsOrdVOList.get(i).setPaptDpstDt(
					formatterDate(smsMsOrdVOList.get(i).getPaptDpstDt()));
			smsMsOrdVOList.get(i).setRaptDpstDt(
					formatterDate(smsMsOrdVOList.get(i).getRaptDpstDt()));
			smsMsOrdVOList.get(i).setWrhsDlvDt(
					formatterDate(smsMsOrdVOList.get(i).getWrhsDlvDt()));
			smsMsOrdVOList.get(i).setDptrDlvDt(
					formatterDate(smsMsOrdVOList.get(i).getDptrDlvDt()));
			smsMsOrdVOList.get(i).setArvlDlvDt(
					formatterDate(smsMsOrdVOList.get(i).getArvlDlvDt()));
			smsMsOrdVOList.get(i).setPoDlvDt(
					formatterDate(smsMsOrdVOList.get(i).getPoDlvDt()));
			smsMsOrdVOList.get(i).setCount(count);
			smsMsOrdVOList.get(i).setPage(page);
			smsMsOrdVOList.get(i).setRow(row);
			System.out.println(smsMsOrdVOList.get(i).toString());
			// System.out.println("kor : "+smsMsOrdVOList.get(i).getKrwXchrAmt()+"   / cns : "+smsMsOrdVOList.get(i).getCnsXchrAmt()+" / usd : "+smsMsOrdVOList.get(i).getUsdXchrAmt());
		}

		return smsMsOrdVOList;

	}

	@ResponseBody
	@RequestMapping("/orderManagementSave.ajax")
	public String orderManagementSave(SmsMsOrdVO smsMsOrdVO) throws Exception {

		System.out.println("smsMsOrdVO.getStdXchrKindCd() : "
				+ smsMsOrdVO.getStdXchrKindCd());

		if (smsMsOrdVO.getPaptDpstDt() != null)
			smsMsOrdVO.setPaptDpstDt(smsMsOrdVO.getPaptDpstDt()
					.replace("-", ""));
		if (smsMsOrdVO.getWrhsDlvDt() != null)
			smsMsOrdVO.setWrhsDlvDt(smsMsOrdVO.getWrhsDlvDt().replace("-", ""));
		if (smsMsOrdVO.getDptrDlvDt() != null)
			smsMsOrdVO.setDptrDlvDt(smsMsOrdVO.getDptrDlvDt().replace("-", ""));
		if (smsMsOrdVO.getArvlDlvDt() != null)
			smsMsOrdVO.setArvlDlvDt(smsMsOrdVO.getArvlDlvDt().replace("-", ""));
		if (smsMsOrdVO.getPoDlvDt() != null)
			smsMsOrdVO.setPoDlvDt(smsMsOrdVO.getPoDlvDt().replace("-", ""));
		if (smsMsOrdVO.getRaptDpstDt() != null)
			smsMsOrdVO.setRaptDpstDt(smsMsOrdVO.getRaptDpstDt()
					.replace("-", ""));
		if (smsMsOrdVO.getB5cGudsRegDt() != null)
			smsMsOrdVO.setB5cGudsRegDt(smsMsOrdVO.getB5cGudsRegDt().replace(
					"-", ""));

		if ("".equals(smsMsOrdVO.getPaptDpstDt()))
			smsMsOrdVO.setPaptDpstDt(null);
		if ("".equals(smsMsOrdVO.getRaptDpstDt()))
			smsMsOrdVO.setRaptDpstDt(null);
		if ("".equals(smsMsOrdVO.getWrhsDlvDt()))
			smsMsOrdVO.setWrhsDlvDt(null);
		if ("".equals(smsMsOrdVO.getWrhsDlvDestCd()))
			smsMsOrdVO.setWrhsDlvDestCd(null);
		if ("".equals(smsMsOrdVO.getDptrDlvDt()))
			smsMsOrdVO.setDptrDlvDt(null);
		if ("".equals(smsMsOrdVO.getDptrDlvDestCd()))
			smsMsOrdVO.setDptrDlvDestCd(null);
		if ("".equals(smsMsOrdVO.getArvlDlvDt()))
			smsMsOrdVO.setArvlDlvDt(null);
		if ("".equals(smsMsOrdVO.getArvlDlvDestCd()))
			smsMsOrdVO.setArvlDlvDestCd(null);
		if ("".equals(smsMsOrdVO.getPoDlvDt()))
			smsMsOrdVO.setPoDlvDt(null);
		if ("".equals(smsMsOrdVO.getPoDlvDestCd()))
			smsMsOrdVO.setPoDlvDestCd(null);
		if ("".equals(smsMsOrdVO.getB5mBuyCont()))
			smsMsOrdVO.setB5mBuyCont(null);
		if ("".equals(smsMsOrdVO.getB5cGudsRegDt()))
			smsMsOrdVO.setB5cGudsRegDt(null);
		if ("".equals(smsMsOrdVO.getB5cGudsRegMemo()))
			smsMsOrdVO.setB5cGudsRegMemo(null);
		if ("".equals(smsMsOrdVO.getStdXchrKindCd()))
			smsMsOrdVO.setStdXchrKindCd(null);

		if (smsMsOrdVO.getPaptDpstRate() != null
				&& new BigDecimal("100").equals(smsMsOrdVO.getPaptDpstRate())) {
			// 선금 100%, 잔금 0%
			BigDecimal zero = new BigDecimal("0");
			smsMsOrdVO.setRaptDpstAmt(zero);
			smsMsOrdVO.setRaptDpstRate(zero);
			smsMsOrdVO.setRaptDpstDt(smsMsOrdVO.getPaptDpstDt());
		} else if (smsMsOrdVO.getRaptDpstRate() != null
				&& new BigDecimal("100").equals(smsMsOrdVO.getRaptDpstRate())) {
			// 선금 0%, 잔금 100%
			BigDecimal zero = new BigDecimal("0");
			smsMsOrdVO.setPaptDpstAmt(zero);
			smsMsOrdVO.setPaptDpstRate(zero);
			smsMsOrdVO.setPaptDpstDt(smsMsOrdVO.getRaptDpstDt());
		} else {

		}
		System.out.println(smsMsOrdVO.toString());
		orderService.updateSmsMsOrdInOrderManagementView(smsMsOrdVO);
		return "success";
	}

	// B5C 에서 주문정보(TB_MS_ORD , TB_MS_ORD_SMS) 와 그에 관련된 상품정보(opt단위까지)
	// (TB_MS_ORD_GUDS_OPT - TB_MS_GUDDS_OPT) 를 받아와서
	// SMS의 주문정보(TB_MS_ORD) 와 상품정보 SMS_MS_ORD_GUDS, SMS_MS_GUDS, SMS_MS_GUDS_IMG
	// 에 INSERT 해준다.
	// 발생 조건 :
	// 1. orderManagementView.do 호출 할 때마다
	// 2. 1시간 간격마다.
	@Scheduled(fixedDelay = 3600000)
	// 1시간마다 실행
	@RequestMapping(value = "/smsMsOrdBatch")
	public void smsMsOrdBatch() throws Exception {
		LOGGER.debug("=================Batch Start=================");
		orderService.batchSmsMsOrd();
		LOGGER.debug("=================Batch End=================");
	}

	@ResponseBody
	@RequestMapping(value = "/orderManagementInsert.ajax", method = RequestMethod.POST)
	public String orderManagementInsert(
			@RequestParam("file") MultipartFile[] fileArray,
			HttpSession session, HttpServletRequest request) throws Exception {
		String result = "false";
		// 파일 load
		MultipartFile excelFile = null;
		for (MultipartFile multipartFile : fileArray) {
			String originalFileName = multipartFile.getOriginalFilename();
			String originalFileNameLowerCase = originalFileName.toLowerCase();
			if (originalFileNameLowerCase.endsWith(".xls")
					|| originalFileNameLowerCase.endsWith(".xlsx")) { // 엑셀 파일
																		// 확인
				LOGGER.debug("1.1.=============================");
				excelFile = multipartFile;
			} else {
				LOGGER.debug("1.2.=============================확장자 잘못됨.");
			}
		}
		// workbook 초기화
		Workbook wb = WorkbookFactory.create(excelFile.getInputStream());
		// 시트 번호

		// 엑셀 업로드 할 때, 히스토리에 남기기 위해 user 정보 필요.
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		// 서비스 진행.
		result = orderService.insertExcelSmsMsOrdNSmsMsOrdGuds(wb, user);
		LOGGER.debug("2.1.=============================완료");
		return result;
	}

	// ordManagement.jsp 에서 엑셀다운로드 클릭하면, JQgrid 를 그대로 가져와서 엑셀 다운로드 되게 만들어준다.
	@ResponseBody
	@RequestMapping(value = "/orderManagementExcelDownload.do")
	public void orderManagementExcelDownload(
			@RequestParam Map<String, String> params,
			Map<String, Object> model, HttpServletResponse response,
			@RequestParam(value = "dataList") String dataList) throws Exception {

		Date d = new Date();
		SimpleDateFormat today = new SimpleDateFormat("yyyyMMdd");
		String filename = "[StatusReport]" + today.format(d) + ".xls";
		String sheetname = today.format(d);

		String[] colNames = { "Order Number", "申请日期", "客户名称", "订购商品", "查看详情",
				"交易规模", "上海负责人", "韩国负责人", "订购路径", "状态", "状态详情", "最终状态",
				"商品供应商汇款", "首付日期", "首付金额", "首付百分比", "入库日期", "入库地点", "出港日期",
				"出港地点", "到岸日期", "到岸地点", "P/O日期", "P/O地点", "余付", "余款结算日期",
				"余款百分比", "是否在帮韩品购买", "帮5采上传日期", "帮5采上传内容" };
		List<String> colName = new ArrayList<String>();
		for (int i = 0; i < colNames.length; i++) {
			colName.add(colNames[i]);
		}
		List<String[]> colValue = new ArrayList<String[]>();

		String a = dataList.replaceAll("&quot", "\"");
		String jsonString = a.replaceAll(";", "");

		List<SmsMsOrdVO> smsMsOrdVOList = null;
		smsMsOrdVOList = orderService.orderManageMentExcelDownload(jsonString);

		HSSFWorkbook workbook = new HSSFWorkbook();
		// Sheet명 설정
		HSSFSheet sheet = workbook.createSheet(sheetname);

		HSSFRow row;
		HSSFCell cell;

		int j = 0;
		row = sheet.createRow(0);
		// 출력 cell 생성
		for (int i = 0; i < colNames.length; i++) {
			row.createCell(j++).setCellValue(colNames[i]);
		}
		for (int i = 0; i < smsMsOrdVOList.size(); i++) {
			String ordSumAmt = null;
			if (smsMsOrdVOList.get(i).getOrdSumAmt() != null)
				ordSumAmt = smsMsOrdVOList.get(i).getOrdSumAmt().toString();

			String paptDpstAmt = null;
			if (smsMsOrdVOList.get(i).getPaptDpstAmt() != null)
				paptDpstAmt = smsMsOrdVOList.get(i).getPaptDpstAmt().toString();

			String paptDpstRate = null;
			if (smsMsOrdVOList.get(i).getPaptDpstRate() != null)
				paptDpstRate = smsMsOrdVOList.get(i).getPaptDpstRate()
						.toString();

			String raptDpstAmt = null;
			if (smsMsOrdVOList.get(i).getRaptDpstAmt() != null)
				raptDpstAmt = smsMsOrdVOList.get(i).getRaptDpstAmt().toString();

			String raptDpstRate = null;
			if (smsMsOrdVOList.get(i).getRaptDpstRate() != null)
				raptDpstRate = smsMsOrdVOList.get(i).getRaptDpstRate()
						.toString();

			int k = 0;
			row = sheet.createRow(i + 1);
			// 출력 cell 생성
			row.createCell(k++).setCellValue(smsMsOrdVOList.get(i).getOrdNo());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getOrdReqDt());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getClientNm());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getOrderedGudsNm());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getShowDetail());
			row.createCell(k++).setCellValue(ordSumAmt);
			row.createCell(k++).setCellValue(smsMsOrdVOList.get(i).getCnsMng());
			row.createCell(k++).setCellValue(smsMsOrdVOList.get(i).getKorMng());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getOrdTypeCd());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getOrdStatCd());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getHistDetail());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getOrdStatCd());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getBactPrvdDtPlusbactPrvdAmt());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getPaptDpstDt());
			row.createCell(k++).setCellValue(paptDpstAmt);
			row.createCell(k++).setCellValue(paptDpstRate);
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getWrhsDlvDt());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getWrhsDlvDestCd());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getDptrDlvDt());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getDptrDlvDestCd());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getArvlDlvDt());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getArvlDlvDestCd());
			row.createCell(k++)
					.setCellValue(smsMsOrdVOList.get(i).getPoDlvDt());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getPoDlvDestCd());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getRaptDpstDt());
			row.createCell(k++).setCellValue(raptDpstAmt);
			row.createCell(k++).setCellValue(raptDpstRate);
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getB5mBuyCont());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getB5cGudsRegDt());
			row.createCell(k++).setCellValue(
					smsMsOrdVOList.get(i).getB5cGudsRegMemo());

		}

		// 생성된 엑셀을 HTTPServletResponse에 적어서 다운로드 할 수 있도록 한다.
		writeExcelAttachmentForDownload(response, filename, workbook);

	}

	public String formatterDate(String date) {
		if (date != null && "".equals(date) != true) {
			String yyyy = date.substring(0, 4);
			String mm = date.substring(4, 6);
			String dd = date.substring(6, 8);
			return yyyy + '-' + mm + '-' + dd;
		} else {
			return date;
		}
	}

	// ordManagement.jsp 에서 엑셀다운로드 클릭하면, JQgrid 를 그대로 가져와서 엑셀 다운로드 되게 만들어준다.
	@ResponseBody
	@RequestMapping(value = "/orderManagementExcelTemplateDownload.do")
	public void orderManagementExcelTemplateDownload(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ServletContext context = request.getSession().getServletContext();
		String Path = request.getContextPath();
		String appPath = context.getRealPath("") + File.separator;
		String fullPath = appPath + "WEB-INF" + File.separator + "templates"
				+ File.separator + "[Request]EstimateRequest.xlsx";

		downloadFile(request, response, fullPath);

	}

	@ResponseBody
	@RequestMapping(value = "/orderManagementSelectTbMsOrdSplReqCont.ajax")
	public TbMsOrdVO orderManagementSelectTbMsOrdSplReqCont(String ordNo)
			throws Exception {

		// String result = "false";
		TbMsOrdVO tbMsOrdVO = null;
		tbMsOrdVO = orderService.selectTbMsOrdSplReqCont(ordNo);
		// result = tbMsOrdVO.getReqCont();
		// result = URLEncoder.encode(result , "UTF-8");

		return tbMsOrdVO;
	}

	@RequestMapping(value = "/downloadInvalidExcel", method = RequestMethod.GET)
	public void downloadInvalidExcel(String fileName,
			HttpServletResponse response) throws InvalidFormatException,
			IOException {

		// String tempDir = File.separator + "opt" + File.separator + "sms-disk"
		// + File.separator;
		String tempDir = OPT_B5C_ETC;
		String invalidExcelFileFullName = tempDir + fileName;

		File inValidExcelFile = new File(invalidExcelFileFullName);
		Workbook wb = WorkbookFactory.create(inValidExcelFile);
		// 엑셀 다운로드
		writeExcelAttachmentForDownload(response, fileName, wb);

		// 다운로드 후 파일을 삭제 한다.
		inValidExcelFile.setWritable(true);
		boolean isDeleted = inValidExcelFile.delete();

		System.out.println(isDeleted);

	}

	
	 // DELETE
	 // 개발 및 안정화 과정 중에서, DB 를 지우고 다시 Batch 해야 하는 상황 때문에 만든 함수입니다.
	 // 서비스가 진행되고 나서는 이 부분은 삭제 해야 합니다.
	 @RequestMapping(value="/deleteBeforeFirstBatch")
	 public String deleteBeforeFirstBatch(HttpServletRequest request,
	 HttpServletResponse response) throws Exception{
	 orderService.deleteBeforeFirstBatch();
	 String result = "orderManagement";
	 return result;
	 }
	
	

}
