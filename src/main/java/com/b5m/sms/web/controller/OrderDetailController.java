package com.b5m.sms.web.controller;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
import com.b5m.sms.biz.dao.TbMsCmnCdDAO;
import com.b5m.sms.biz.service.GoodsService;
import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.biz.service.UserService;
import com.b5m.sms.common.file.FileResultVO;
import com.b5m.sms.common.file.FileUtil;
import com.b5m.sms.common.util.DateUtil;
import com.b5m.sms.common.util.StringUtil;
import com.b5m.sms.vo.CodeVO;
import com.b5m.sms.vo.OrderDetailVO;
import com.b5m.sms.vo.SmsMsBrndVO;
import com.b5m.sms.vo.SmsMsEstmGudsVO;
import com.b5m.sms.vo.SmsMsEstmVO;
import com.b5m.sms.vo.SmsMsGudsImgVO;
import com.b5m.sms.vo.SmsMsGudsVO;
import com.b5m.sms.vo.SmsMsOrdFileVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;
import com.b5m.sms.vo.SmsMsOrdUserVO;
import com.b5m.sms.vo.SmsMsUserVO;
import com.b5m.sms.vo.TbMsCmnCdVO;

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
	
	@Resource(name="tbMsCmnCdDAO")
	private TbMsCmnCdDAO tbMsCmnCdDAO;
	
	@RequestMapping(value="/orderDetailView")
	public String orderDetail(Model model, String ordNo, String reload,String saved) throws Exception{
		
		//1.selectBox ������ ��
		//1-1.����ڸ� ���� �ִ� SmsMsUser (�߱������list/ �ѱ������list)
		SmsMsUserVO prSmsMsUserVO= new SmsMsUserVO();
		
		List<SmsMsUserVO> SmsMsUserList = userService.selectSmsMsUser(prSmsMsUserVO);	//����ִ�SmsMsUserVO�� �Ű������� ������ ��ü�˻� 
		List<SmsMsUserVO> cnsOprList= new ArrayList<SmsMsUserVO>();		//�߱�����ڸ���Ʈ
		List<SmsMsUserVO> krOprList= new ArrayList<SmsMsUserVO>();			//�ѱ�����ڸ���Ʈ
		
		
		for(SmsMsUserVO vo : SmsMsUserList){
			if(("N000530100").equals(vo.getOgnzDivCd())){			//N000530100 ������
				cnsOprList.add(vo);
				
			}
			else if(("N000530200").equals(vo.getOgnzDivCd())){		//N000530200 �ѱ���
				krOprList.add(vo);
			}
		}
		//1-2.����ȭ��(stdXchrKindCd)
		List<CodeVO> stdXchrKindCdList = orderService.selectTbmsCmnCd("N00059");	//N00059 ����ȯ���ڵ�
		//1-3.��������(dlvMode)
		List<CodeVO> dlvModeCdList = orderService.selectTbmsCmnCd("N00052");	//N00052 ��۹���ڵ�
		//1-4.�ױ�(dlvDest)
		List<CodeVO> dlvDestCdList = orderService.selectTbmsCmnCd("N00051");	//N00051 ����������ڵ�
		
		//2.���������� ���� orderDetailVO 
		OrderDetailVO orderDetail = orderService. selectSmsMsOrdDetail(ordNo);
		//�ֹ������������ �ٸ� ���̺� �����Ѵ� �����ͼ� orderDetailVO�� ä����						<<--getUserAlasEngNm�� null�ϰ�� ()���� �����صѰ�
		List<SmsMsUserVO> oprList = userService.selectSmsMsUserByOrdNo(ordNo);
		for(SmsMsUserVO vo : oprList){
			if(("N000530100").equals(vo.getOgnzDivCd())){			//N000530100 ������
				/*String oprCns =vo.getUserAlasCnsNm()+"("+vo.getUserAlasEngNm()+")";
				orderDetail.setOprCns(oprCns);*/
				orderDetail.setOprCns(vo.getUserEml());
				
			}
			else if(("N000530200").equals(vo.getOgnzDivCd())){		//N000530200 �ѱ���
				/*String oprKr =vo.getUserAlasCnsNm()+"("+vo.getUserAlasEngNm()+")";
				orderDetail.setOprKr(oprKr);*/
				orderDetail.setOprKr(vo.getUserEml());
			}
		}
	
		//3.������ǰ�� ���� List<smsMsOrdGudsList>			//�̹���-���ڵ�-��ǰ��-�����û����-�԰�-����(�ܰ�)-�ιڽ�����-��ǰ��ũ-�˻� : ����(�ٽ�)
		List<SmsMsOrdGudsVO> smsMsOrdGudsList =goodsService.selectSmsMsOrdGudsByOrdNo(ordNo);
		//�ֹ��� �����ϴ� ��ǰ�� Mapping�� �Ǿ��� ��� ��ǰ���̺��� *�̹���,�ιڽ�����,���ڵ�*���� �����´�
		for(SmsMsOrdGudsVO vo :smsMsOrdGudsList){
			if("Y".equals(vo.getOrdGudsMpngYn())){
				SmsMsGudsVO smsGuds = new SmsMsGudsVO();
				smsGuds=goodsService.selectSmsMsGuds(vo.getGudsId());

				//������ �Ǿ��ٰ� �����µ� ���� ��ǰ������ ���� ��찡 �����Ҽ� �ִ�.
				if(smsGuds!=null){
	 				vo.setOrdGudsUpcId(smsGuds.getGudsUpcId());				//���ڵ� //vo.setGudsUpcId(smsGuds.getGudsUpcId());		
					vo.setGudsInbxQty(smsGuds.getGudsInbxQty());	//�̹���
				}else{
					vo.setOrdGudsMpngYn("N");
				}
				//GUDS_IMG_CD  N000080100 ��ǥ�̹��� 	N000080200 ����̹���
				List<SmsMsGudsImgVO> gudsImgList = goodsService.selectSmsMsGudsImg(vo.getGudsId());
				if(!gudsImgList.isEmpty()){ 
					if(!StringUtil.isNullOrEmpty(gudsImgList.get(0).getGudsImgSysFileNm())){
						vo.setImgSrcPath(gudsImgList.get(0).getGudsImgSysFileNm());
					}
				}
			}
		}//End_for(SmsMsOrdGudsVO vo :smsMsOrdGudsList)

		
		
	

		//4.�������������� ���� List<orderDetailFileVO>
		List<SmsMsOrdFileVO> smsMsOrdFileList= orderService.selectSmsMsOrdFileByOrdNo(ordNo); 	//=orderService.select

		
		//5.model�� ���� ������ ��´�.
		model.addAttribute("ordNo", ordNo);		//�ֹ���ȣ	
		//selectBox�������
		model.addAttribute("cnsOprList",cnsOprList);		//�߱�������
		model.addAttribute("krOprList",krOprList);		//�ѱ�������
		model.addAttribute("stdXchrKindCdList",stdXchrKindCdList);		//����ȭ��(stdXchrKindCd)
		model.addAttribute("dlvModeCdList",dlvModeCdList);		//��������(dlvMode)
		model.addAttribute("dlvDestCdList",dlvDestCdList);		//�ױ�(dlvDest)
		//�����
		model.addAttribute("orderDetail",orderDetail);	//�ֹ�������
		model.addAttribute("smsMsOrdGudsList", smsMsOrdGudsList);	//�ֹ���ǰ����Ʈ
		model.addAttribute("gudsListSize", smsMsOrdGudsList.size());	//�ֹ���ǰ����Ʈ
		model.addAttribute("smsMsOrdFileList", smsMsOrdFileList);		//�ֹ����ϸ���Ʈ
		
		//�θ�â ���ΰ�ħ
		model.addAttribute("reload", reload);		
		
		//������忩�� alert
		model.addAttribute("saved", saved);
		return "orderDetail";
		
	}
	////////////////////////////////////////////////////////
	@RequestMapping(value="/orderDetailSpecialView")
	public String orderDetail(@RequestParam("file") MultipartFile[] fileArray, Model model, String ordNo) throws Exception{
		
		/////////////////////////////////////////// ����� ����, ������ ���� �ε� - KJY ///////////////////////////////////////////
		// ���� load
		MultipartFile excelFile = null;
		for (MultipartFile multipartFile : fileArray) {		
			String originalFileName = multipartFile.getOriginalFilename();
			if (originalFileName.endsWith(".xls") || originalFileName.endsWith(".xlsx")) {          //���� ���� Ȯ��
				LOGGER.debug("1.1.=============================" );
				excelFile = multipartFile;
			}else{
				LOGGER.debug("1.2.=============================Ȯ���� �߸���." );
			}
		}
		//workbook �ʱ�ȭ
		Workbook wb = WorkbookFactory.create(excelFile.getInputStream());
		Sheet sheet = wb.getSheetAt(0);                   

		LOGGER.debug(" 2.1.1 �������� SMS_MS_ORD ������ �̾ƿ´�." );
		// Ŭ���̾�Ʈ ��û ������ excel ���� �޾ƿ� ������ �ʱ�ȭ.     SmsMsOrdVO
		String userAlasCnsNm = null;							 //�����   (�߱��� �߹� ȭ��)
		String custId = null;								// Ŭ���̾�Ʈ
		String ordReqDt = null;							// ��������
		String ordHopeArvlDt = null;					// ��� �ε�����
		String	 dlvModeCdPlusdlvDestCd = null;		// �������� + �ױ� 
		String ctrtTmplYn = null;						// ��༭ ���ø� ����
		String poSchdDt = null;							// PO��������
		String smplReqYn = null;						// ���ÿ�û����
		String qlfcReqYn = null;							// �ڰ� ��û ����
		String custOrdProcCont = null;				 // �ֹ� ���μ���
		
		String dlvModeCd = null;	 					// ��������
		String dlvDestCd = null;						// �ױ�
		
		String ordTypeCd = null;		
		String ordMemoCont = null;                   //���
		// �������� ExcelClientReqGudsVO ������ �����ͼ� ����.
		userAlasCnsNm = StringUtil.excelGetCell(sheet.getRow(1).getCell(2));  						//�����
		custId = StringUtil.excelGetCell(sheet.getRow(1).getCell(4));    						// Ŭ���̾�Ʈ
		ordReqDt = StringUtil.excelGetCell(sheet.getRow(1).getCell(6)); 						// ��������
		if(ordReqDt!=null){
			ordReqDt = ordReqDt.replace("-", "");
			if("".equals(ordReqDt)) ordReqDt=null;
			if(ordReqDt.length()>8) ordReqDt=null;
		}
		ordHopeArvlDt = StringUtil.excelGetCell(sheet.getRow(2).getCell(2)); 				// ��� �ε�����
		if(ordHopeArvlDt!=null){
			ordHopeArvlDt = ordHopeArvlDt.replace("-", "");
			if("".equals(ordHopeArvlDt)) ordHopeArvlDt=null;
			if(ordHopeArvlDt.length()>8) ordHopeArvlDt=null;	
		}
		dlvModeCdPlusdlvDestCd = StringUtil.excelGetCell(sheet.getRow(2).getCell(4));	// �������� + �ױ� 	
//		���� - Ǯ����(�ױ�)
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

		// ��� �����- Code ���̺��� ���� ��������, ��Ȯ���� ������ null ó�� �Ѵ�.
		if(dlvDestCd!=null && "".equals(dlvDestCd) !=true){
			List<TbMsCmnCdVO> tbMsCmnCdVOList = null;
			tbMsCmnCdVOList = tbMsCmnCdDAO.selectCmnCdByEtcNCdVal(dlvDestCd);
			if(tbMsCmnCdVOList.size() == 1){
				dlvDestCd = tbMsCmnCdVOList.get(0).getCd();
			}else{
				dlvDestCd = null;
			}
		}
		
		ctrtTmplYn = StringUtil.excelGetCell(sheet.getRow(3).getCell(2));     				// ��༭ ���ø� ����
		if("Y".equalsIgnoreCase(ctrtTmplYn)) {ctrtTmplYn="Y";}									
		else if("N".equalsIgnoreCase(ctrtTmplYn)) {ctrtTmplYn="N";}							
		else{
			ctrtTmplYn=null;
		}
		poSchdDt = StringUtil.excelGetCell(sheet.getRow(3).getCell(6));     							// PO��������
		if(poSchdDt!=null){
			poSchdDt = poSchdDt.replace("-", "");
			if("".equals(poSchdDt)) poSchdDt=null;
			if(poSchdDt.length()>8) poSchdDt=null;
		}
		smplReqYn = StringUtil.excelGetCell(sheet.getRow(3).getCell(4));     					// ���ÿ�û����
		if("Y".equalsIgnoreCase(smplReqYn)) {smplReqYn="Y";}
		else if("N".equalsIgnoreCase(smplReqYn)) {smplReqYn="N";}
		else{
			smplReqYn=null;
		}
		qlfcReqYn = StringUtil.excelGetCell(sheet.getRow(4).getCell(2));       				// �ڰ� ��û ����
		if("Y".equalsIgnoreCase(qlfcReqYn)) {qlfcReqYn="Y";}
		else if("N".equalsIgnoreCase(qlfcReqYn)) {qlfcReqYn="N";}
		else{
			qlfcReqYn=null;
		}
		custOrdProcCont = StringUtil.excelGetCell(sheet.getRow(4).getCell(4));  			// �ֹ� ���μ���
		int tempRows = sheet.getPhysicalNumberOfRows();
		ordMemoCont = StringUtil.excelGetCell(sheet.getRow(tempRows-1).getCell(1));  	//���
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		System.out.println("����� �� : " + userAlasCnsNm);        //����ڸ� : �߹�ȭ��
//		System.out.println("Ŭ���̾�Ʈ : " + custId);
//		System.out.println("�������� : " + ordReqDt);
//		System.out.println("��� �ε����� : " + ordHopeArvlDt);
//		System.out.println("�������� + �ױ�  : " + dlvModeCdPlusdlvDestCd);
//		System.out.println("��༭ ���ø� ���� : " + ctrtTmplYn);
//		System.out.println("���ÿ�û���� : " + smplReqYn);
//		System.out.println("�ڰ� ��û ���� : " + qlfcReqYn);
//		System.out.println("�ֹ� ���μ��� : " + custOrdProcCont);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
		String oprCns = null;
		
		SmsMsUserVO smsMsUserVO = new SmsMsUserVO();
		smsMsUserVO.setUserAlasCnsNm(userAlasCnsNm); 								//  SMS_MS_USER �� ����� �߱��� ȭ�� �ִ��� �˻�
		List <SmsMsUserVO> smsMsUserVOList = orderService.selectSmsMsUser(smsMsUserVO);
		if(smsMsUserVOList.size()==1){		// � �� �������� ������, �������� �˻��Ǹ� ���� mapping �� �� �ϴ� ���� ����.
			SmsMsOrdUserVO smsMsOrdUserVO = new SmsMsOrdUserVO();
			smsMsOrdUserVO.setOrdNo(ordNo);
			oprCns = smsMsUserVOList.get(0).getUserAlasCnsNm();
		}
		
		// �������� OrderDetailVO �� ���� �ִ´�.
		OrderDetailVO orderDetailVO = new OrderDetailVO();
		orderDetailVO.setOrdNo(ordNo);
		orderDetailVO.setOprCns(oprCns);             // userAlasCnsNm   �� ������, DB�� �˻��ؼ� ������ ����, ������ NO ����
//		orderDetailVO.setOprKr(oprKr);				  // �ʿ� ����.
		orderDetailVO.setCustId(custId);
		orderDetailVO.setOrdReqDt(ordReqDt);
		orderDetailVO.setOrdHopeArvlDt(ordHopeArvlDt);
//		orderDetailVO.setStdXchrAmt(stdXchrAmt);								// ������ ���� X
//		orderDetailVO.setStdXchrKindCd(stdXchrKindCd);						// ������ ���� X	
//		orderDetailVO.setPymtPrvdModeCont(pymtPrvdModeCont);			// ������ ���� X
		orderDetailVO.setDlvModeCd(dlvModeCd);
		orderDetailVO.setDlvDestCd(dlvDestCd);
		orderDetailVO.setPoSchdDt(poSchdDt);
//		orderDetailVO.setOrdEstmDt(ordEstmDt);									// ������ ���� X
//		orderDetailVO.setOrdExpDt(ordExpDt);										// ������ ���� X
		orderDetailVO.setCtrtTmplYn(ctrtTmplYn);
		orderDetailVO.setSmplReqYn(smplReqYn);
//		orderDetailVO.setPoSchdDt(poSchdDt);									// ������ ���� X
		orderDetailVO.setQlfcReqYn(qlfcReqYn);
		orderDetailVO.setCustOrdProcCont(custOrdProcCont);
//		orderDetailVO.setOrdMemoCont(ordMemoCont);							// ������ ���� X
		orderDetailVO.setOrdMemoCont(ordMemoCont);
		
		LOGGER.debug("2.1.4.1 �������� �ֹ��� SMS_MS_ORD_GUDS ����Ʈ�� �̾ƿ´�." );
		int rows = sheet.getPhysicalNumberOfRows();

		
		
		int tempNum=1;   					//ord_guds_seq�� ���� ���� ������
		ordNo = ordNo;						//ordNo
		String ord_guds_seq=null;   		//NO
		String gudsId=null;					//��ǰid   -   b5c gudsId �ʹ� ����.   �� ���̺� ��ü�� ����Ű ����
		String ordGudsUpcId=null;       		//��ǰ���ڵ�		
		String ordGudsCnsNm=null;  		//�߹� ��ǰ��
		String ordGudsQty=null;     		//��ǰ ����, (�����û)����
		String ordGudsSizeVal=null;		//�ֹ���ǰũ�Ⱚ ,�԰�
		String ordGudsSalePrc=null;		//�ֹ���ǰ�ǸŰ�(PO�ܰ�USD)
		String ordGudsUrlAddr=null;	    // //�ֹ���ǰurl�ּ�, ��ũ

		boolean gudsExist = false;
		List<SmsMsOrdGudsVO> smsMsOrdGudsVOList = new ArrayList<SmsMsOrdGudsVO>();
		for(int i=6; i<rows-1; i++){
			ordGudsUpcId = StringUtil.getCellUpcId(sheet.getRow(i).getCell(1));
			ordGudsCnsNm = StringUtil.excelGetCell(sheet.getRow(i).getCell(2));
			ordGudsQty = StringUtil.excelGetCell(sheet.getRow(i).getCell(3));
			ordGudsSizeVal =  StringUtil.excelGetCell(sheet.getRow(i).getCell(4)); 
			ordGudsSalePrc = StringUtil.excelGetCell(sheet.getRow(i).getCell(5));
			ordGudsUrlAddr = StringUtil.excelGetCell(sheet.getRow(i).getCell(7));
			
			// ��ǰ ���� �߿���, (ordGudsCnsNm) �� ������ �ǹ̰� ����.
			if(ordGudsCnsNm!=null && "".equals(ordGudsCnsNm)!=true){
				gudsExist = true;
				ord_guds_seq =  String.valueOf(tempNum++);
				SmsMsOrdGudsVO smsMsOrdGudsVO = new SmsMsOrdGudsVO();											// SMS_MS_ORD_GUDS �� ���� VO
				smsMsOrdGudsVO.setOrdNo(ordNo);
				smsMsOrdGudsVO.setOrdGudsSeq(ord_guds_seq);
				smsMsOrdGudsVO.setOrdGudsMpngYn("N");
				smsMsOrdGudsVO.setOrdGudsUpcId(ordGudsUpcId);
				smsMsOrdGudsVO.setOrdGudsCnsNm(ordGudsCnsNm);
				smsMsOrdGudsVO.setOrdGudsQty(ordGudsQty);
				smsMsOrdGudsVO.setOrdGudsSalePrc(ordGudsSalePrc);
				smsMsOrdGudsVO.setOrdGudsUrlAddr(ordGudsUrlAddr);
				
				smsMsOrdGudsVOList.add(smsMsOrdGudsVO);
				LOGGER.debug(smsMsOrdGudsVO.toString());
			}
		}
		if(gudsExist == false){        // ��ǰ�� �ϳ��� ������, �ֹ����� X
			return "error_notException"; 
		}
		LOGGER.debug("2.1.4.2.======SMS_MS_ORD_GUDS  List Load==========�Ϸ�" );
		//END////////////////////////////////////// ����� ����, ������ ���� �ε� - KJY ///////////////////////////////////////////END



		//		smsMsOrdGudsVOList     -> ��ǰ���� ����Ʈ	
//		System.out.println("Excel ���� �о�� smsMsOrdGudsVOList ");
//		for(int i=0; i<smsMsOrdGudsVOList.size();i++){
//			System.out.println(smsMsOrdGudsVOList.get(i).toString());
//		}

		//
		
		

		
		
		

		//1-1.����ڸ� ���� �ִ� SmsMsUser (�߱������list/ �ѱ������list)
		SmsMsUserVO prSmsMsUserVO= new SmsMsUserVO();
		
		List<SmsMsUserVO> SmsMsUserList = userService.selectSmsMsUser(prSmsMsUserVO);	//����ִ�SmsMsUserVO�� �Ű������� ������ ��ü�˻� 
		List<SmsMsUserVO> cnsOprList= new ArrayList<SmsMsUserVO>();		//�߱�����ڸ���Ʈ
		List<SmsMsUserVO> krOprList= new ArrayList<SmsMsUserVO>();			//�ѱ�����ڸ���Ʈ
		
		
		for(SmsMsUserVO vo : SmsMsUserList){
			if(("N000530100").equals(vo.getOgnzDivCd())){			//N000530100 ������
				cnsOprList.add(vo);
				
			}
			else if(("N000530200").equals(vo.getOgnzDivCd())){		//N000530200 �ѱ���
				krOprList.add(vo);
			}
		}
		//1-2.����ȭ��(stdXchrKindCd)
		List<CodeVO> stdXchrKindCdList = orderService.selectTbmsCmnCd("N00059");	//N00059 ����ȯ���ڵ�
		//1-3.��������(dlvMode)
		List<CodeVO> dlvModeCdList = orderService.selectTbmsCmnCd("N00052");	//N00052 ��۹���ڵ�
		//1-4.�ױ�(dlvDest)
		List<CodeVO> dlvDestCdList = orderService.selectTbmsCmnCd("N00051");	//N00051 ����������ڵ�
		
		
		//������ ���� ���������� �ڵ尪���� ����
		if(orderDetailVO.getDlvModeCd()!=null){
			for(CodeVO vo : dlvModeCdList){
				if(orderDetailVO.getDlvModeCd().equals(vo.getCdVal())){
					orderDetailVO.setDlvModeCd(vo.getCd());
				}
			}
		}
		
		
		//������ ���� �ױ��� �ڵ尪���� ����
		if(orderDetailVO.getDlvDestCd()!=null){
			for(CodeVO vo : dlvDestCdList){
				if(orderDetailVO.getDlvDestCd().equals(vo.getCdVal())){
					orderDetailVO.setDlvDestCd(vo.getCd());
				}
			}
		}
		
		//������ ���� ȭ���� ���ϰ����� ����
		if(orderDetailVO.getOprCns()!=null){
			for(SmsMsUserVO vo : cnsOprList){
				if(orderDetailVO.getOprCns().equals(vo.getUserAlasCnsNm())){
					orderDetailVO.setOprCns(vo.getUserEml());
				}
			}
		}
		
		String isSaved ="N";
		//5.model�� ���� ������ ��´�.
		model.addAttribute("ordNo", ordNo);		//�ֹ���ȣ	
		//selectBox�������
		model.addAttribute("cnsOprList",cnsOprList);		//�߱�������
		model.addAttribute("krOprList",krOprList);		//�ѱ�������
		model.addAttribute("stdXchrKindCdList",stdXchrKindCdList);		//����ȭ��(stdXchrKindCd)
		model.addAttribute("dlvModeCdList",dlvModeCdList);		//��������(dlvMode)
		model.addAttribute("dlvDestCdList",dlvDestCdList);		//�ױ�(dlvDest)
		//�����
		model.addAttribute("orderDetail",orderDetailVO);	//�ֹ�������
		model.addAttribute("smsMsOrdGudsList", smsMsOrdGudsVOList);	//�ֹ���ǰ����Ʈ
		model.addAttribute("gudsListSize", smsMsOrdGudsVOList.size());	//�ֹ���ǰ����Ʈ
		model.addAttribute("isSaved", isSaved);
		return "orderDetail";
	}
	//����
	@RequestMapping(value="/orderDetailSave")
	public String orderDetailSave(OrderDetailVO orderDetailVo, SmsMsOrdGudsVO smsMsOrdGudsVO, String ordNo, int gudsListSize, String wrtrEml,Model model) throws Exception{
		try{
			//DT�� ���¸� date�� ����
			orderDetailVo.setOrdEstmDt(StringUtil.dateToDt(orderDetailVo.getOrdEstmDt()));
			orderDetailVo.setOrdExpDt(StringUtil.dateToDt(orderDetailVo.getOrdExpDt()));
			orderDetailVo.setOrdHopeArvlDt(StringUtil.dateToDt(orderDetailVo.getOrdHopeArvlDt()));
			orderDetailVo.setOrdReqDt(StringUtil.dateToDt(orderDetailVo.getOrdReqDt()));
			orderDetailVo.setPoSchdDt(StringUtil.dateToDt(orderDetailVo.getPoSchdDt()));
			
			
			//��ǰ������ ,�� ���� �����߻��� ���ɼ������� <=validate ó��
			List<SmsMsOrdGudsVO> smsMsOrdGudsList= new ArrayList<SmsMsOrdGudsVO>();	
			String[] ordGudsSeq= new String[0];
			String[] ordGudsUpcId = new String[0]; 
			String[] ordGudsCnsNm= new String[0];
			String[] ordGudsQty= new String[0];
			String[] ordGudsSizeVal= new String[0];
	//		String[] ordGudsOrgPrc= new String[0];
			String[] ordGudsSalePrc= new String[0];
			String[] ordGudsUrlAddr= new String[0];
			String[] gudsId= new String[0];
			orderDetailVo.setOrdNo(ordNo);
		
			//�� ��ǰ ������ �Ľ��Ѵ�. 		
			if(gudsListSize>0)	{		//if(smsMsOrdGudsVO.getOrdGudsSeq()!=null){			//��������ȣ ���� null�ΰ�� ��ǰ�� ������ �ʾ����� �ǹ�
				String gudsSeq=smsMsOrdGudsVO.getOrdGudsSeq().replace("," , " , ");
				String gudsUpcId=smsMsOrdGudsVO.getOrdGudsUpcId().replace("," , " , ");
				String gudsCnsNm=smsMsOrdGudsVO.getOrdGudsCnsNm().replace("," , " , ");
				String gudsQty=smsMsOrdGudsVO.getOrdGudsQty().replace("," , " , ");
				String gudsSizeVal=smsMsOrdGudsVO.getOrdGudsSizeVal().replace("," , " , ");
	//			String gudsOrgPrc= smsMsOrdGudsVO.getOrdGudsOrgPrc().replace("," , " , ");
				String gudsSalePrc= smsMsOrdGudsVO.getOrdGudsSalePrc().replace("," , " , ");
				String gudsUrlAddr=smsMsOrdGudsVO.getOrdGudsUrlAddr().replace("," , " , ");
				String gid=smsMsOrdGudsVO.getGudsId().replace("," , " , ");
				
				
				ordGudsSeq	=gudsSeq.split(",");
				ordGudsUpcId=gudsUpcId.split(",");			//���ڵ�
				ordGudsCnsNm = gudsCnsNm.split(",");		//��ǰ��	
				ordGudsQty  =gudsQty.split(",");			//��ǰ��û����
				ordGudsSizeVal =gudsSizeVal.split(",");		//��ǰ�԰�
	//			ordGudsOrgPrc = gudsOrgPrc.split(",");		//��ǰ����
				ordGudsSalePrc = gudsSalePrc.split(",");		//��ǰ����
				
				gudsId=gid.split(",");
				//String[] gudsInbxQty = smsMsOrdGudsVO.getGudsInbxQty().split(",");	//�ιڽ�����, db�� �������
				ordGudsUrlAddr =gudsUrlAddr.split(",");
			}
			
			
	//		System.out.println(orderDetailVo);			//��ǰ���� 
	//		System.out.println(smsMsOrdGudsVO);		//��ǰ VO
	//		System.out.println(ordNo);						//������Ʈ�� ��ǰ��ȣ
	//		System.out.println(gudsListSize);			//��ǰ�� ���� 
			
	//		for(String s : ordGudsSeq){
	//			System.out.println("s " +s);
	//		}
			
			
			
			for(int i=0; i<gudsListSize; i++){
				SmsMsOrdGudsVO vo =new SmsMsOrdGudsVO();
				vo.setOrdNo(ordNo);
				vo.setOrdGudsSeq(ordGudsSeq[i].trim());
				vo.setOrdGudsUpcId(ordGudsUpcId[i].trim());
				vo.setOrdGudsCnsNm(ordGudsCnsNm[i].trim());
				vo.setOrdGudsQty(ordGudsQty[i].trim());
				vo.setOrdGudsSizeVal(ordGudsSizeVal[i].trim());
	//			vo.setOrdGudsOrgPrc(ordGudsOrgPrc[i].trim());
				vo.setOrdGudsSalePrc(ordGudsSalePrc[i].trim());
				vo.setOrdGudsUrlAddr(ordGudsUrlAddr[i].trim());
				vo.setGudsId(gudsId[i].trim());
				smsMsOrdGudsList.add(vo);
				
			}

			orderService.updateSmsMsOrdGudsDetail(orderDetailVo,smsMsOrdGudsList,wrtrEml );
		}catch(Exception e){
			return "redirect:orderDetailView.do?ordNo="+ordNo+"&reload=YES&saved=NO";
		}

		return "redirect:orderDetailView.do?ordNo="+ordNo+"&reload=YES&saved=YES";
	}
	
	@ResponseBody
	@RequestMapping(value="/orderDetailFileUpload" , method = RequestMethod.POST)
	public String orderDetailFileUpload(MultipartHttpServletRequest request,String ordNo, String wrtrEml) throws Exception	{

		String result="success";
		try{
		//1.���ÿ� ���ε��� ���� ����
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = request.getFile(itr.next());
		FileResultVO fileResultVO = uploadMultipartFileToDisk(mpf);

		LOGGER.debug(fileResultVO.toString());
//		System.out.println(fileResultVO.getSavedRealFileNm());		//���� ���� �̸�
//		System.out.println(fileResultVO.getSavedFileNm());				//�ý��� �����̸�
//		System.out.println(ordNo);			//�ֹ���ȣ	
//		System.out.println(wrtrEml);			//�ۼ���
		
		//2.DB�� ���ε� ���� ���� ����
		SmsMsOrdFileVO ordFileVo = new SmsMsOrdFileVO();
		ordFileVo.setOrdFileKindCd("N000540100");					//�ڵ� N000540100 (�ֹ����������ڵ� POB)
		//ordFileVo.setOrdFilepath(ordFilepath);					//���
		ordFileVo.setOrdFileRegrEml(wrtrEml);					//�����
		ordFileVo.setOrdFileOrgtFileNm(fileResultVO.getSavedRealFileNm());	//���� �����̸�
		ordFileVo.setOrdFileSysFileNm(fileResultVO.getSavedFileNm());  //���� ����� ���� �̸�
		ordFileVo.setOrdNo(ordNo);		//�ֹ���ȣ
		ordFileVo.setOrdFileSeq(orderService.selectSmsMsOrdFileSeqNext(ordNo));
		
			orderService.insertSmsMsOrdFile(ordFileVo);
			
		}catch(Exception e){
			result="fail";
		}
		return result;
		//return fileResultVO;
	}
	
	@RequestMapping(value = "/orderDetailFileDownload", method = RequestMethod.GET)
	public void doDownload(HttpServletRequest request, HttpServletResponse response, String filePath,String fileName) throws IOException {
		String fullPath;
//		System.out.println("filepath : "+filePath);
		String ext=null;
		if(!StringUtil.isNullOrEmpty(filePath)){
			ext=FileUtil.getExt(filePath);
//			System.out.println("ext : "+ext);
		}
		
		
		
		if(StringUtil.containStr(imgExt, ext)){
			fullPath=OPT_B5C_IMG + filePath;
		}else{
			fullPath=OPT_B5C_ETC + filePath;
		}
		
		//downloadFile(request, response, fullPath);
	
		
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// MIME Type ���Ϸ� ���� ������.
		ServletContext context = request.getSession().getServletContext();
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}
		LOGGER.debug("MIME type: " + mimeType);

		// Content Ư�� ����
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// Header �� ����
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

				LOGGER.info("���ø� �������� ��ġ =" + fullPath);

				File templateFile = new File(fullPath);
/*				Workbook wb = WorkbookFactory.create(templateFile);
				Sheet sheet = wb.getSheetAt(0);*/
				
				XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(templateFile);
				XSSFSheet sheet = wb.getSheetAt(0);
				String oprKr_alas_eng=null;		//�ѱ������ ��Ī �����
				
				//1.�⺻�����Է� 
				OrderDetailVO orderDetailVO = orderService.selectSmsMsOrdDetail(ordNo);

				//����� ã�� 
				List<SmsMsUserVO> oprList = userService.selectSmsMsUserByOrdNo(ordNo);
				for(SmsMsUserVO vo : oprList){
					if(("N000530200").equals(vo.getOgnzDivCd())){			//N000530200 �ѱ���
						oprKr_alas_eng = vo.getUserAlasEngNm();
						orderDetailVO.setOprKr(vo.getUserAlasCnsNm());
					}
				}
				//1-1.�������̸�
				Row row = sheet.getRow(3);
				Cell cell = row.getCell(10);
				cell.setCellValue(orderDetailVO.getOrdNm());
				
				//1-2.����ȯ��
				row = sheet.getRow(4);
				cell = row.getCell(10);
				if(orderDetailVO.getStdXchrAmt()!=null)
					cell.setCellValue(orderDetailVO.getStdXchrAmt().intValue());

				//1-3.����ȭ��
				row = sheet.getRow(5);
				cell = row.getCell(10);
				List<CodeVO> stdXchrKindCdList = orderService.selectTbmsCmnCd("N00059");	//N00059 ����ȯ���ڵ�
				if(orderDetailVO.getStdXchrKindCd()!=null){
					for(CodeVO vo : stdXchrKindCdList){
						if(orderDetailVO.getStdXchrKindCd().equals(vo.getCd())){
							orderDetailVO.setStdXchrKindCd(vo.getCdVal());
						}
					}
				}
				cell.setCellValue(orderDetailVO.getStdXchrKindCd());
				cell = row.getCell(14);
				cell.setCellValue(orderDetailVO.getStdXchrKindCd());

				
				
				
				
				//1-4.��������
				row = sheet.getRow(6);
				cell = row.getCell(10);
				List<CodeVO> dlvModeCdList = orderService.selectTbmsCmnCd("N00052");	//N00052 ��۹���ڵ�
				//������ ���� ���������� �ڵ尪���� ����
				if(orderDetailVO.getDlvModeCd()!=null){
					for(CodeVO vo : dlvModeCdList){
//						System.out.println(vo.getCd());
						if(orderDetailVO.getDlvModeCd().equals(vo.getCd())){
							orderDetailVO.setDlvModeCd(vo.getCdVal());
						}
					}
				}
				cell.setCellValue(orderDetailVO.getDlvModeCd());
				cell = row.getCell(14);
				cell.setCellValue(orderDetailVO.getDlvModeCd());
				
				//1-5.��������
				row = sheet.getRow(7);
				cell = row.getCell(10);
				cell.setCellValue(orderDetailVO.getOrdEstmDt());
				//1-6.������ȿ����
				row = sheet.getRow(8);
				cell = row.getCell(10);
				cell.setCellValue(orderDetailVO.getOrdExpDt());
				row = sheet.getRow(7);
				cell = row.getCell(14);
				cell.setCellValue(orderDetailVO.getOrdExpDt());
				
//				System.out.println("������ ���Ե� :"+orderDetailVO);
				
				//DB���� ��ǰ ������ �����´�.
				List<SmsMsOrdGudsVO> smsMsOrdGudsList =goodsService.selectSmsMsOrdGudsByOrdNo(ordNo);


				//�ֹ��� �����ϴ� ��ǰ�� Mapping�� �Ǿ��� ��� ��ǰ���̺��� *�̹���,�ιڽ�����,���ڵ�*���� �����´�
				for(SmsMsOrdGudsVO vo :smsMsOrdGudsList){
					
					if("Y".equals(vo.getOrdGudsMpngYn())){
						SmsMsGudsVO smsGuds = new SmsMsGudsVO();
						smsGuds=goodsService.selectSmsMsGuds(vo.getGudsId());

						//������ �Ǿ��ٰ� �����µ� ���� ��ǰ������ ���� ��찡 �����Ҽ� �ִ�.
						if(smsGuds!=null){
			 				vo.setOrdGudsUpcId(smsGuds.getGudsUpcId());				//���ڵ� //vo.setGudsUpcId(smsGuds.getGudsUpcId());		
							vo.setGudsInbxQty(smsGuds.getGudsInbxQty());	//�ιڽ�����
						}else{
							vo.setOrdGudsMpngYn("N");
						}
						//GUDS_IMG_CD  N000080100 ��ǥ�̹��� 	N000080200 ����̹���
						List<SmsMsGudsImgVO> gudsImgList = goodsService.selectSmsMsGudsImg(vo.getGudsId());
						if(!gudsImgList.isEmpty()){ 
							if(!StringUtil.isNullOrEmpty(gudsImgList.get(0).getGudsImgSysFileNm())){
								vo.setImgSrcPath(OPT_B5C_IMG+gudsImgList.get(0).getGudsImgSysFileNm());
							}
						}
					}
				}//End_for(SmsMsOrdGudsVO vo :smsMsOrdGudsList)
				
				int sourceRowNum = 11;		//������ ��Ÿ����
				int destinationRowNum = 12;	//~���� ����
				int addRowCountNum =smsMsOrdGudsList.size()-1 ;		//������ ���μ�(��ǰ4��->3���߰�, ����1��)

				
				
				
				Row sourceRow = sheet.getRow(sourceRowNum);
				
				
				Cell newCell0 = sourceRow.getCell(0);		//�귣��
				String brndId =orderService.selectSmsMsGudsBrndId(smsMsOrdGudsList.get(0).getGudsId());
				if(brndId!=null){
					newCell0.setCellValue(brndId);
				}
				//�̹���
				if(smsMsOrdGudsList.get(0).getImgSrcPath()!=null){
					addPricture(smsMsOrdGudsList.get(0).getImgSrcPath(),1,(11),wb,sheet);
					addPricture(smsMsOrdGudsList.get(0).getImgSrcPath(),13,(11),wb,sheet);
				}
				Cell newCell2 = sourceRow.getCell(2);		//���ڵ�
				newCell2.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsUpcId());
				Cell newCell3 = sourceRow.getCell(3);		//��ǰ��Ī
				/*newCell3.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsKorNm());*/
				newCell3.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsCnsNm());
				Cell newCell4 = sourceRow.getCell(4);		//��ǰ��Ī(��)
				newCell4.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsCnsNm());
				Cell newCell5 = sourceRow.getCell(5);		//�ιڽ�����
				newCell5.setCellValue(smsMsOrdGudsList.get(0).getGudsInbxQty());
				Cell newCell6 = sourceRow.getCell(6);		//�ܰ�
				if(smsMsOrdGudsList.get(0).getOrdGudsSalePrc()!=null){
					newCell6.setCellValue(Double.parseDouble(smsMsOrdGudsList.get(0).getOrdGudsSalePrc()));
				}else{
					newCell6.setCellValue(0);
				}
				
				//newCell6.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsOrgPrc());
				Cell newCell7 = sourceRow.getCell(7);		//�ֹ�����
				newCell7.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsQty());
				Cell newCell8 = sourceRow.getCell(8);		//�ݾ�
				newCell8.setCellFormula("G12*H12");
				Cell newCell9 = sourceRow.getCell(9);		//URL
				newCell9.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsUrlAddr());
				Cell newCell10= sourceRow.getCell(10);		//�����
				if(orderDetailVO.getOprKr()!=null){
					newCell10.setCellValue(orderDetailVO.getOprKr());
				}
				
				//������� ǥ��

				Cell newCell14= sourceRow.getCell(14);		//�ܰ�
				if(smsMsOrdGudsList.get(0).getOrdGudsSalePrc()!=null){
					newCell14.setCellValue(Double.parseDouble(smsMsOrdGudsList.get(0).getOrdGudsSalePrc()));
				}else{
					newCell14.setCellValue(0);
				}
				//newCell14.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsOrgPrc());
				
				Cell newCell15= sourceRow.getCell(15);		//�ֹ�����
				newCell15.setCellValue(smsMsOrdGudsList.get(0).getOrdGudsQty());
				Cell newCell16= sourceRow.getCell(16);		//�Ѿ�
				newCell16.setCellFormula("G12*H12");
				
				
				if(addRowCountNum>0){
					sheet.shiftRows(12, 16, addRowCountNum);
				}
				for(int i=0; i<addRowCountNum;i++){
					Row newRow = sheet.createRow(12+i);
					newRow.setHeight(sourceRow.getHeight());
					
					for(int j=0;j<sourceRow.getLastCellNum(); j++){
						Cell oldCell = sourceRow.getCell(j);			
						Cell newCell = newRow.createCell(j);
						
						
			            // Copy style from old cell and apply to new cell
			            CellStyle newCellStyle = wb.createCellStyle();
			           // if(newCellStyle!=null && oldCell!=null){
			            	newCellStyle.cloneStyleFrom(oldCell.getCellStyle());			//��Ÿ���� �����ϰ� ���� ��Ÿ���� �ִ´�
			            	newCell.setCellStyle(newCellStyle);	
			         //   }
			            
			           
			            switch (j) {
			                case 0:		//�귣��ID
			                	brndId=null;
			                	brndId=orderService.selectSmsMsGudsBrndId(smsMsOrdGudsList.get(i+1).getGudsId());
			                	if(brndId!=null){
			                		newCell.setCellValue(brndId);
			                	}
			                    break;
			                case 1:		//�̹���
			                	if(smsMsOrdGudsList.get(i+1).getImgSrcPath()!=null){
			                		addPricture(smsMsOrdGudsList.get(i+1).getImgSrcPath(),1,(i+12),wb,sheet);
			                		addPricture(smsMsOrdGudsList.get(i+1).getImgSrcPath(),13,(i+12),wb,sheet);
			                	}
			                    break;
			                case 2:		//���ڵ�
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsUpcId()!=null){
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsUpcId());
			                	}
			                    break;
			                case 3:		//��ǰ��Ī
			                	/*if(smsMsOrdGudsList.get(i+1).getOrdGudsKorNm()!=null){
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsKorNm());
			                	}		*/
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsCnsNm()!=null){
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsCnsNm());
			                	}	
			                    break;
			                case 4:		//��ǰ��Ī(��)
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsCnsNm()!=null){
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsCnsNm());
			                	}			                    
			                    break;
			                case 5:		//�ιڽ�����
			                    newCell.setCellValue(smsMsOrdGudsList.get(i+1).getGudsInbxQty());
			                    break;
			                case 6:		//�ܰ�
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsSalePrc()!=null){
			                		newCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			                		System.out.println("|||"+smsMsOrdGudsList.get(i+1).getOrdGudsSalePrc()+"|||");
			                		newCell.setCellValue(Double.parseDouble(smsMsOrdGudsList.get(i+1).getOrdGudsSalePrc()));
			        			}else{
			        				newCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			        				newCell.setCellValue(0);
			        			}
			                	//newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsOrgPrc());			             
			                    break;
			                case 7:		//�ֹ�����
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsQty()!=null){
			                		newCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsQty());
			                	}
			                    break;
			                case 8:		//�ݾ�
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
			                	newCell.setCellValue(orderDetailVO.getOprKr());
			                    break;
			                case 14:		//�������-�ܰ�
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsSalePrc()!=null){
			                		newCell.setCellType(Cell.CELL_TYPE_NUMERIC);			        
			                		newCell.setCellValue(Double.parseDouble(smsMsOrdGudsList.get(i+1).getOrdGudsSalePrc()));
			        			}else{
			        				newCell.setCellValue(0);
			        			}
			                		//newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsOrgPrc());
			                	
			                    break;
			                case 15:		//�������-�ֹ�����
			                	if(smsMsOrdGudsList.get(i+1).getOrdGudsQty()!=null){
			                		newCell.setCellType(Cell.CELL_TYPE_NUMERIC);
			                		newCell.setCellValue(smsMsOrdGudsList.get(i+1).getOrdGudsQty());
			                	}
			                	
			                    break;
			                case 16:		//������� -�Ѿ�
			                	intk=destinationRowNum+1+i;
		                		k=Integer.toString(intk); 
		                		newCell.setCellFormula("G"+k+"*H"+k);       
			                    break;

			            }//end switch
					}//end for j
				}//end for i 

				//������ �Ѿ�
				if(addRowCountNum>0){
					Row lastRow = sheet.getRow(12+addRowCountNum);
					Cell lastCell=lastRow.getCell(8);
					lastCell.setCellType(Cell.CELL_TYPE_FORMULA);
					lastCell.setCellFormula("SUM(I12:I"+(12+addRowCountNum)+")");
					
					
					Cell lastCell2=lastRow.getCell(16);
					lastCell2.setCellType(Cell.CELL_TYPE_FORMULA);
					lastCell2.setCellFormula("SUM(I12:I"+(12+addRowCountNum)+")");
				}else{
					Row lastRow = sheet.getRow(12);
					
					Cell lastCell=lastRow.getCell(8);
					lastCell.setCellType(Cell.CELL_TYPE_FORMULA);
					lastCell.setCellFormula("SUM(I12:I12)");
					
					Cell lastCell2=lastRow.getCell(16);
					lastCell2.setCellType(Cell.CELL_TYPE_FORMULA);
					lastCell2.setCellFormula("SUM(I12:I12)");
				}
				// �ٿ�ε� �� ���ø� ���� �̸� ���� : ORDER_DETAIL+_����Ͻú���.xls
				 Date d = new Date();
			     SimpleDateFormat today = new SimpleDateFormat("yyyyMMdd");
			     
			     String downloadedTemplateName = "[Estimate]"+smsMsOrdGudsList.get(0).getOrdGudsCnsNm()+"_("+orderDetailVO.getCustId()+")_"+oprKr_alas_eng+"_"+today.format(d)+"_ver.2.xlsx";

				
				//String downloadedTemplateName = "[ORDER_DETAIL]" + "_" + DateUtil.sGetCurrentTime("yyyyMMdd_HHmm_ss") + ".xlsx";
				// ���� �ٿ�ε�
				writeExcelAttachmentForDownload(response, downloadedTemplateName, wb);

	}

	
	private void addPricture(String fileName, int col, int row, XSSFWorkbook workbook,XSSFSheet sheet) throws IOException {
		InputStream is = new FileInputStream(fileName);
		byte[] bytes = IOUtils.toByteArray(is);
		
		
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

				LOGGER.info("���ø� �������� ��ġ =" + fullPath);

				File templateFile = new File(fullPath);
				List<Workbook> wbList = new ArrayList<Workbook>();
				List<String> excelNmList = new ArrayList<String>();
				
				
				//1.cell�� ä��µ� �ʿ��� �����Ͱ�������
				SmsMsEstmVO poVo = orderService.selectSmsMsEstmVO(ordNo);
				List<SmsMsEstmGudsVO> poGudsList= goodsService.selectSmsMsEstmGuds(ordNo);
				List<SmsMsEstmGudsVO> poPrvdList= goodsService.selectSmsMsEstmGudsGroupByPrvd(ordNo);
				int sourceRowNum =17;				//������ ��Ÿ����
				
				int prvdSize = poPrvdList.size();
				int gudsSize = poGudsList.size();
				
				int gudsNo=0;	
				//String rowNoStr="";
				
				//�ش� ��ǰ ������ �� ��ŭ ����PO ������ �����.
				for(int prvdIndex=0;prvdIndex<prvdSize ;prvdIndex++){
					int rowNo =sourceRowNum;				//�ҽ��ѹ�üũ 
					
					//List<SmsMsEstmGudsVO> poGudsList= goodsService.selectSmsMsEstmGuds(ordNo, crn); 
					
					Workbook wb =  WorkbookFactory.create(templateFile);
					
					Sheet sheet = wb.getSheetAt(0);
					Row sourceRow = sheet.getRow(sourceRowNum);			//��Ÿ���� ������ �ִ� ��
					gudsNo=0;			//���Ե� ��ǰ�� ���� 	

					
					String poNo="IZK"+poVo.getPoDt()+String.format("%03d", (prvdIndex+1));
					
					//1-1.PO NO (IZK+��¥+��ȣ)
					Row row = sheet.getRow(2);
					Cell cell = row.getCell(9);
					cell.setCellValue(poNo);	
					
					//1-2.PO��¥ 
					row=sheet.getRow(3);
					cell = row.getCell(9);
					cell.setCellValue(StringUtil.dtToDate(poVo.getPoDt()));	
					
					//1-3.����ڹ�ȣ
					row=sheet.getRow(6);
					cell = row.getCell(3);
					cell.setCellValue(poPrvdList.get(prvdIndex).getOrdGudsPrvdCrn());
					
					//1-4.��ȣ
					row=sheet.getRow(7);
					cell = row.getCell(3);
					cell.setCellValue(poPrvdList.get(prvdIndex).getOrdGudsPrvdNm());
					
					
					String gudsNmTemp = "";
					
					List<SmsMsEstmGudsVO> tempEstmGudsList = new ArrayList<SmsMsEstmGudsVO>();
					for(int gudsIndex=0; gudsIndex<gudsSize;gudsIndex++){
						if(poPrvdList.get(prvdIndex).getOrdGudsPrvdCrn().equals(poGudsList.get(gudsIndex).getOrdGudsPrvdCrn())){		//����ڹ�ȣ�� ������� ����
							tempEstmGudsList.add(poGudsList.get(gudsIndex));
							gudsNo++;
						}
					}
					
					sheet.shiftRows(18, 34, gudsNo);		//��ǰ�̵�
					
					for(int gudsIndex=0; gudsIndex<gudsNo;gudsIndex++){	
						
							gudsNmTemp = tempEstmGudsList.get(0).getOrdGudsCnsNm();
				
							SmsMsEstmGudsVO gudsVo = tempEstmGudsList.get(gudsIndex);		//gudsVO�� ���� po��ǰ ����	
							Row newRow = sheet.createRow(sourceRowNum+1+gudsIndex);			//���ο�row ���� 
							newRow.setHeight(sourceRow.getHeight());
							Row nowRow=sheet.getRow(sourceRowNum+gudsIndex);			
								for(int j=1;j<sourceRow.getLastCellNum(); j++){
									
									Cell oldCell = sourceRow.getCell(j);		
									Cell newCell = newRow.createCell(j);
									Cell nowCell =nowRow.getCell(j);
						            // Copy style from old cell and apply to new cell
						            CellStyle newCellStyle = wb.createCellStyle();						          
						            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());			//��Ÿ���� �����ϰ� ���� ��Ÿ���� �ִ´�
						            newCell.setCellStyle(newCellStyle);	
						            
						            //���� ������ ����
						            switch (j) {
						            case 1:
						            	nowCell.setCellValue(gudsIndex+1);
						            	break;
					                case 2:		//2-1.���ڵ�
					                	nowCell.setCellValue(gudsVo.getGudsUpcId());
					                    break;
					                case 3:		//2-2.��ǰ��
					                	nowCell.setCellValue(gudsVo.getOrdGudsCnsNm());		
					                    break;
					                case 6:		//2-3.�ֹ�����
					                	nowCell.setCellValue(Integer.parseInt(gudsVo.getOrdGudsQty()));
					                    break;
					                case 7:		//2-4.���Դܰ�
					                	nowCell.setCellValue(Double.parseDouble(gudsVo.getOrdGudsOrgPrc()));
					                	//nowCell.setCellValue(Double.parseDouble(gudsVo.getOrdGudsSalePrc()));
					                    break;
					                case 8:		//2-5.�����հ�
					                	rowNo +=1;
					                	//rowNoStr=Integer.toString(rowNo); 
					                	nowCell.setCellFormula("G"+rowNo+"*H"+rowNo);                    
					                    break;
						            }//end switch
								}//end for j
					}//end for gudsIndex
					
					
					//3-1. �հ����
					row = sheet.getRow(rowNo+1);
					cell = row.getCell(7);
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula("SUM(G18:G"+(rowNo+1)+")");
							
					//3-2. �հ�ݾ�(VAT ����)
					row = sheet.getRow(rowNo+2);
					cell = row.getCell(7);
					cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
					cell.setCellFormula("SUM(I18:I"+(rowNo+1)+")");
					
					
					//4. Ư���� ������(��ǰ��,�����հ�)	 : ���� �� ���� ������ �ֽ��ϴٶ�� ��� �߻���Ų��.
					for(int gudsIndex=0; gudsIndex<gudsNo;gudsIndex++){
						sheet.addMergedRegion(new CellRangeAddress(sourceRowNum+gudsIndex, (short)(sourceRowNum+gudsIndex), 3, (short)4));
						sheet.addMergedRegion(new CellRangeAddress(sourceRowNum+gudsIndex, (short)(sourceRowNum+gudsIndex) , 8, (short)9));
						
					}
					sheet.shiftRows(18+gudsNo, 34+gudsNo, -1);			//���� ���̱�����
					
					//���� �̸��� ����� �ֱ� ���� order������ �޾ƿ´�
					OrderDetailVO orderDetailVo = orderService.selectSmsMsOrdDetail(ordNo);
					
					
					 Date d = new Date();
				     SimpleDateFormat today = new SimpleDateFormat("yyyyMMdd");
				     // String downloadedTemplateName = "[SReport]"+poGudsList.get(0).getOrdGudsCnsNm()+"("+orderDetailVo.getCustId()+")_"+getUserInfo(orderDetailVo.getOprKr()).getUserAlasEngNm()+"_"+today.format(d)+".xlsx";
				     String downloadedTemplateName = "[SReport]"+gudsNmTemp+"("+orderDetailVo.getCustId()+")_"+getUserInfo(orderDetailVo.getOprKr()).getUserAlasEngNm()+"_"+today.format(d)+"_"+((prvdIndex+101)+"").substring(1)+".xlsx";
					
				//	String downloadedTemplateName = "PURCHASE_PO" + "_" + poNo + ".xlsx";
					
					
					
					
					wbList.add(wb);	//������ ���������� ����Ʈ�� ��´�.
					excelNmList.add(downloadedTemplateName);		//������ ���������� �̸��� ����Ʈ�� ��´�
				}//end for prvdIndex
				

				writeExcelListForDownload(response, excelNmList, wbList);
	}


	public SmsMsUserVO getUserInfo(String userEml) throws Exception{
		SmsMsUserVO vo = new SmsMsUserVO();
		vo.setUserEml(userEml);	
		List<SmsMsUserVO> oprList =userService.selectSmsMsUser(vo);
		
		return oprList.get(0);
	}
}
