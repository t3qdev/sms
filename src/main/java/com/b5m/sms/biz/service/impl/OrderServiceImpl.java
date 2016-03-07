package com.b5m.sms.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.b5m.sms.biz.dao.SmsMsEstmDAO;
import com.b5m.sms.biz.dao.SmsMsEstmGudsDAO;
import com.b5m.sms.biz.dao.SmsMsGudsDAO;
import com.b5m.sms.biz.dao.SmsMsGudsImgDAO;
import com.b5m.sms.biz.dao.SmsMsOrdDAO;
import com.b5m.sms.biz.dao.SmsMsOrdFileDAO;
import com.b5m.sms.biz.dao.SmsMsOrdGudsDAO;
import com.b5m.sms.biz.dao.SmsMsOrdHistDAO;
import com.b5m.sms.biz.dao.SmsMsOrdUserDAO;
import com.b5m.sms.biz.dao.SmsMsUserDAO;
import com.b5m.sms.biz.dao.TbMsOrdDAO;
import com.b5m.sms.biz.dao.TbMsOrdSplDAO;
import com.b5m.sms.biz.service.OrderService;
import com.b5m.sms.common.util.StringUtil;
import com.b5m.sms.vo.CodeVO;
import com.b5m.sms.vo.ExcelClientReqGudsVO;
import com.b5m.sms.vo.OrderCalculateVO;
import com.b5m.sms.vo.OrderDetailVO;
import com.b5m.sms.vo.OrderPOGudsVO;
import com.b5m.sms.vo.OrderPOVO;
import com.b5m.sms.vo.SmsMsEstmGudsVO;
import com.b5m.sms.vo.SmsMsEstmVO;
import com.b5m.sms.vo.SmsMsGudsImgVO;
import com.b5m.sms.vo.SmsMsGudsVO;
import com.b5m.sms.vo.SmsMsOrdFileVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;
import com.b5m.sms.vo.SmsMsOrdHistVO;
import com.b5m.sms.vo.SmsMsOrdUserVO;
import com.b5m.sms.vo.SmsMsOrdVO;
import com.b5m.sms.vo.SmsMsUserVO;
import com.b5m.sms.vo.TbMsOrdBatchVO;
import com.b5m.sms.vo.TbMsOrdVO;


@Service("orderService")
public class OrderServiceImpl implements OrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Resource(name="smsMsOrdHistDAO")
	private SmsMsOrdHistDAO smsMsOrdHistDAO;
	
	@Resource(name="smsMsOrdDAO")
	private SmsMsOrdDAO smsMsOrdDAO;
	
	@Resource(name="smsMsOrdFileDAO")
	private SmsMsOrdFileDAO smsMsOrdFileDAO;

	@Resource(name="tbMsOrdDAO")
	private TbMsOrdDAO tbMsOrdDAO;
	
	@Resource(name="tbMsOrdSplDAO")
	private TbMsOrdSplDAO tbMsOrdSplDAO;
	
	@Resource(name="smsMsOrdGudsDAO")
	private SmsMsOrdGudsDAO smsMsOrdGudsDAO;
	
	@Resource(name="smsMsGudsImgDAO")
	private SmsMsGudsImgDAO smsMsGudsImgDAO;
	
	@Resource(name="smsMsUserDAO")
	private SmsMsUserDAO smsMsUserDAO;
	
	@Resource(name="smsMsOrdUserDAO")
	private SmsMsOrdUserDAO smsMsOrdUserDAO;
	
	@Resource(name="smsMsGudsDAO")
	private SmsMsGudsDAO smsMsGudsDAO;
	
	@Resource(name="smsMsEstmDAO")
	private SmsMsEstmDAO smsMsEstmDAO;
	
	@Resource(name="smsMsEstmGudsDAO")
	private SmsMsEstmGudsDAO smsMsEstmGudsDAO;
	
	@Override
	public List<SmsMsOrdHistVO> selectSmsMsOrdHist(SmsMsOrdHistVO smsMsOrdHistVO)
			throws Exception {
		// TODO Auto-generated method stub
		return (List<SmsMsOrdHistVO>) smsMsOrdHistDAO.selectSmsMsOrdHist(smsMsOrdHistVO);
	}

	@Override
	public List<SmsMsOrdHistVO> selectSmsMsOrdHistMaxSeq(
			SmsMsOrdHistVO smsMsOrdHistVO) throws Exception {
		// TODO Auto-generated method stub
		return (List<SmsMsOrdHistVO>) smsMsOrdHistDAO.selectSmsMsOrdHistMaxSeq(smsMsOrdHistVO);
	}

	@Override
	public void updateSmsMsOrdHist(SmsMsOrdHistVO smsMsOrdHistVO)
			throws Exception {
		// TODO Auto-generated method stub
		smsMsOrdHistDAO.updateSmsMsOrdHist(smsMsOrdHistVO);
	}

	@Override
	public void insertSmsMsOrdHist(SmsMsOrdHistVO smsMsOrdHistVO)
			throws Exception {
		// TODO Auto-generated method stub
		smsMsOrdHistDAO.insertSmsMsOrdHist(smsMsOrdHistVO);
	}

	@Override
	public String selectSmsMsOrdHistSeqCount(SmsMsOrdHistVO smsMsOrdHistVO)
			throws Exception {
		// TODO Auto-generated method stub
		return smsMsOrdHistDAO.selectSmsMsOrdHistSeqCount(smsMsOrdHistVO);
	}

	@Override
	public void updateSmsMsOrdBact(OrderCalculateVO orderCalculateVO) throws Exception {
		//smsMsOrdDAO.updateSmsMsOrdBact(orderCalculateVO);
		
	}

	@Override
	public OrderDetailVO selectSmsMsOrdDetail(String ordNo) throws Exception {
		return smsMsOrdDAO.selectSmsMsOrdDetail(ordNo);
	}

	@Override
	public List<SmsMsOrdFileVO> selectSmsMsOrdFileByOrdNo(String ordNo) throws Exception {
		return smsMsOrdFileDAO.selectSmsMsOrdFileByOrdNo(ordNo);
	}

	@Override
	public List<CodeVO> selectTbmsCmnCd(String Cd) throws Exception {
		return smsMsOrdDAO.selectTbMsCmnCd(Cd);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	public void batch1(List <TbMsOrdVO> tbMsOrdVOList,String ordTypeCd) throws Exception{
		String ordNo = null;
		String b5cOrdNo = null;
		String ordStatCd = null;
		ordStatCd = "N000550100";       // 주문상태 코드 : 접수(N000550100)
		String custId=null;
		String ordHistSeq=null;
		String ordHistWrtrEml="SYSTEM";
		String ordHistRegDttm=null;
		String ordHistHistCont="주문 접수";
		String ordReqDt=null;     //주문등록일
//		if("N000620100".equals(ordTypeCd)){
//			b5cOrdNoList = tbMsOrdDAO.selectTbMsOrdForSmsMsOrd();
//		}else if("N000620200".equals(ordTypeCd)){
//			b5cOrdNoList = tbMsOrdSplDAO.selectTbMsOrdSplForSmsMsOrd();
//		}
		
		for(int i=0; i<tbMsOrdVOList.size();i++){
			ordNo = smsMsOrdDAO.selectSmsMsOrdMaxTodaysOrdNo();
			b5cOrdNo = tbMsOrdVOList.get(i).getB5cOrdNo();
			ordReqDt = tbMsOrdVOList.get(i).getSysReqDttm();
			custId = tbMsOrdVOList.get(i).getCustId();					
			SmsMsOrdVO smsMsOrdVO = new SmsMsOrdVO();
			smsMsOrdVO.setOrdNo(ordNo);
			smsMsOrdVO.setB5cOrdNo(b5cOrdNo);
			smsMsOrdVO.setOrdStatCd(ordStatCd);
			smsMsOrdVO.setOrdTypeCd(ordTypeCd);
			smsMsOrdVO.setOrdReqDt(ordReqDt);
			smsMsOrdVO.setCustId(custId);
			smsMsOrdDAO.insertSmsMsOrd_S(smsMsOrdVO);  

			// SMS_MS_ORD_HIST 추가.
			SmsMsOrdHistVO smsMsOrdHistVO = new SmsMsOrdHistVO();
			smsMsOrdHistVO.setOrdNo(ordNo);
			ordHistSeq = smsMsOrdHistDAO.selectSmsMsOrdHistSeqCount(smsMsOrdHistVO);
			 int tempSeq = Integer.parseInt(ordHistSeq)+1;
			 ordHistSeq=Integer.toString(tempSeq);
			smsMsOrdHistVO.setOrdHistSeq(ordHistSeq+1);
			smsMsOrdHistVO.setOrdStatCd(ordStatCd);       // 주문상태 코드 : 접수(N000550100)
			smsMsOrdHistVO.setOrdHistWrtrEml(ordHistWrtrEml);
//			smsMsOrdHistVO.setOrdHistRegDttm(ordHistRegDttm);     // SQL 문에서 NOW() 사용
			smsMsOrdHistVO.setOrdHistHistCont(ordHistHistCont);
			
			smsMsOrdHistDAO.insertSmsMsOrdHist(smsMsOrdHistVO);
			LOGGER.debug("이동한 smsMsOrdHistVO : "+smsMsOrdHistVO.toString());
			LOGGER.debug("insertSmsMsOrdHist 등록 ");
		}
		LOGGER.debug("이동한 SMS_MS_ORD 개수 : "+tbMsOrdVOList.size());
	}
	public void batch2(List <TbMsOrdBatchVO> tbMsOrdBatchVOList)throws Exception{
		String ordNo = null;
		String b5cOrdNo = null;
		String ordGudsSeq = null;
		String gudsId = null;
		String ordGudsMpngYn = null;
		String ordGudsKorNm = null;
		String ordGudsCnsNm = null;
		String ordGudsOrgPrc = null;
		String ordGudsSalePrc = null;
		String ordGudsQty = null;
		
		String brndId = null;
		String gudsKorNm = null;
		String gudsCnsNm = null;
		String gudsUpcId = null;
		String gudsVatRfndYn = null;
		
		String gudsIdOfB5m = null;    // b5m 에서의 gudsId 를 가져오기 위함. - 원래는 opt id가 필요한데, IMG 는 gudsID + sllrID 가 필요.
		
		for(int i=0; i<tbMsOrdBatchVOList.size();i++){
			System.out.println(tbMsOrdBatchVOList.get(i).toString());
			b5cOrdNo = tbMsOrdBatchVOList.get(i).getB5cOrdNo();
			
			//SMS_MS_ORD_GUDS 에 넣을 변수들.
			ordNo = smsMsOrdDAO.selectSmsMsOrdByB5cOrdNo(b5cOrdNo);
			ordGudsSeq = smsMsOrdGudsDAO.selectSmsMsOrdGudsSeqCount(ordNo);
			gudsId = smsMsOrdGudsDAO.selectSmsMsOrdGudsGudsIdCount();
			ordGudsMpngYn = "Y";
			ordGudsKorNm = tbMsOrdBatchVOList.get(i).getGudsNm();
			ordGudsCnsNm = tbMsOrdBatchVOList.get(i).getGudsCnsNm();
			ordGudsOrgPrc = tbMsOrdBatchVOList.get(i).getGudsOptOrgPrc();  // b5c 의 opt = sms 의 guds
			ordGudsSalePrc = tbMsOrdBatchVOList.get(i).getOrdGudsSalePrc();
			ordGudsQty = tbMsOrdBatchVOList.get(i).getOrdGudsQty();
			
			SmsMsOrdGudsVO smsMsOrdGudsVO = new SmsMsOrdGudsVO();
			smsMsOrdGudsVO.setOrdNo(ordNo);
			smsMsOrdGudsVO.setOrdGudsSeq(ordGudsSeq);
			smsMsOrdGudsVO.setGudsId(gudsId);
			smsMsOrdGudsVO.setOrdGudsMpngYn(ordGudsMpngYn);
			smsMsOrdGudsVO.setOrdGudsKorNm(ordGudsKorNm);
			smsMsOrdGudsVO.setOrdGudsCnsNm(ordGudsCnsNm);
			smsMsOrdGudsVO.setOrdGudsOrgPrc(ordGudsOrgPrc);
			smsMsOrdGudsVO.setOrdGudsSalePrc(ordGudsSalePrc);
			smsMsOrdGudsVO.setOrdGudsQty(ordGudsQty);
	
			smsMsOrdGudsDAO.insertSmsMsOrdGuds_S(smsMsOrdGudsVO);               
			System.out.println(smsMsOrdGudsVO.toString());
			System.out.println("Insert   SmsMsOrdGuds  성공 : " +i);
			
			SmsMsGudsVO smsMsGudsVO = new SmsMsGudsVO();
			gudsId = gudsId;
			brndId = tbMsOrdBatchVOList.get(i).getBrndId();
			gudsKorNm = tbMsOrdBatchVOList.get(i).getGudsNm();
			gudsCnsNm = tbMsOrdBatchVOList.get(i).getGudsCnsNm();
			gudsUpcId = tbMsOrdBatchVOList.get(i).getGudsOptUpcId();
			gudsVatRfndYn = tbMsOrdBatchVOList.get(i).getGudsVatRfndYn();
			
			smsMsGudsVO.setGudsId(gudsId);
			smsMsGudsVO.setBrndId(brndId);
			smsMsGudsVO.setGudsKorNm(gudsKorNm);
			smsMsGudsVO.setGudsCnsNm(gudsCnsNm);
			smsMsGudsVO.setGudsUpcId(gudsUpcId);
			smsMsGudsVO.setGudsVatRfndYn(gudsVatRfndYn);
	
			smsMsGudsDAO.insertSmsMsGuds_S(smsMsGudsVO);
 			System.out.println(smsMsGudsVO.toString());
			System.out.println("Insert    SmsMsOrdGuds 성공 : " +i);
			

	///////////////////////////////////////////////////////////////////////////////////////////		
	//		insert SMS_MS_BRND
	//		생략
	///////////////////////////////////////////////////////////////////////////////////////////		

	//		insert SMS_MS_GUDS_IMG
			TbMsOrdBatchVO tbMsOrdBatchVO = new TbMsOrdBatchVO();
			gudsId = gudsId;
			brndId = brndId;
			gudsIdOfB5m = tbMsOrdBatchVOList.get(i).getGudsIdOfB5m();
			
			tbMsOrdBatchVO.setGudsId(gudsId);
			tbMsOrdBatchVO.setBrndId(brndId);
			tbMsOrdBatchVO.setGudsIdOfB5m(gudsIdOfB5m);
			smsMsGudsImgDAO.insertTbMsGudsImgToSmsMsGudsImg(tbMsOrdBatchVO);            
			
		}
	}
	
	//batch 3 : 1+2             b5c 에 있는 주문+상품을   SMS 로 가져오는 batch
	public void batchSmsMsOrd() throws Exception{

		List <TbMsOrdVO> tbMsOrdVOList1= null;
		List <TbMsOrdVO> tbMsOrdVOList2= null;		

		// tbMsOrdDAO,  tbMsOrdSplDAO      그냥 오더 + 스페셜 오더 두번 해야 함.
		tbMsOrdVOList1 = tbMsOrdDAO.selectTbMsOrdForSmsMsOrd();        // 그냥 오더
		tbMsOrdVOList2 = tbMsOrdSplDAO.selectTbMsOrdSplForSmsMsOrd();   // 스페셜 오더
		batch1(tbMsOrdVOList1,"N000620100");
		batch1(tbMsOrdVOList2,"N000620200");

		List <TbMsOrdBatchVO> tbMsOrdBatchVOList1 = null;
		List <TbMsOrdBatchVO> tbMsOrdBatchVOList2 = null;
		tbMsOrdBatchVOList1 = tbMsOrdDAO.selectTbMsOrdGudsOptForSmsMsOrdGuds();
		tbMsOrdBatchVOList2 = tbMsOrdSplDAO.selectTbMsOrdGudsOptForSmsMsOrdGuds();
		batch2(tbMsOrdBatchVOList1);
		batch2(tbMsOrdBatchVOList2);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void insertSmsMsOrd_S(SmsMsOrdVO smsMsOrdVO) throws Exception {
		// TODO Auto-generated method stub
		smsMsOrdDAO.insertSmsMsOrd_S(smsMsOrdVO);
	}

	@Override
	public String selectSmsMsOrdByB5cOrdNo(String b5cOrdNo) throws Exception {
		// TODO Auto-generated method stub
		return smsMsOrdDAO.selectSmsMsOrdByB5cOrdNo(b5cOrdNo);
	}

		//정산내용 검색
		@Override
		public OrderCalculateVO selectSmsMsOrdCalculate(String ordNo) throws Exception {
			return smsMsOrdDAO.selectSmsMsOrdCalculate(ordNo);
		}
		//정산내용 삽입
		@Override
		public void updateSmsMsOrdCalculate(OrderCalculateVO vo, String first) throws Exception {
			 
			
			 
			 //첫정산 삽입인경우 history추가
			 if("Y".equals(first)){
				 SmsMsOrdHistVO histVo = new SmsMsOrdHistVO();
				 histVo.setOrdNo(vo.getOrdNo());
				 String seq=smsMsOrdHistDAO.selectSmsMsOrdHistSeqCount(histVo);
				 int tempSeq = Integer.parseInt(seq)+1;
				 seq=Integer.toString(tempSeq);
				 histVo.setOrdHistSeq(seq);
				 histVo.setOrdHistWrtrEml(vo.getBactRegrEml());
				 histVo.setOrdHistHistCont("정산");			//자동생성되는 정산코멘트
				 histVo.setOrdStatCd("N000550400");		//주문상태코드 : N000550400 정산
				 System.out.println(histVo);
				 smsMsOrdHistDAO.insertSmsMsOrdHist(histVo);
				 
				 //주문상태저장
				 SmsMsOrdVO statVo = new SmsMsOrdVO();
				 statVo.setOrdNo(vo.getOrdNo());
				 statVo.setOrdStatCd("N000550400");
				 smsMsOrdDAO.updateSmsMsOrdStatCd(statVo);
			 }
			 
			 vo.setBactRegDt(StringUtil.dateToDt(vo.getBactRegDt()));
			 smsMsOrdDAO.updateSmsMsOrdCalculate(vo);
			 System.out.println("굳");
		}

		@Override
		public void updateSmsMsOrdGudsMpng(SmsMsOrdGudsVO smsMsOrdGudsVO) 	throws Exception {
			smsMsOrdGudsDAO.updateSmsMsOrdGudsMpng(smsMsOrdGudsVO);
		}

		// orderManagment 에서 사용, 클라이언트 요청 견적서를(EXCEL) 이용해서 주문이 새로 들어왔을 때, SMS_MS_ORD, SMS_MS_ORD_GUDS 에 INSERT
		@Override
		public void insertExcelSmsMsOrdNSmsMsOrdGuds(Sheet sheet) throws Exception {
			// TODO Auto-generated method stub
			LOGGER.debug(" 2.1.1 엑셀에서 SMS_MS_ORD 정보를 뽑아온다." );
			// 클라이언트 요청 견적서 excel 에서 받아올 변수들 초기화.     SmsMsOrdVO
			String userAlasCnsNm = null;							 //담당자   (중문 화명)
			String custId = null;								// 클라이언트
			String ordReqDt = null;							// 문의일자
			String ordHopeArvlDt = null;					// 희망 인도일자
			String	 dlvModeCdPlusdlvDestCd = null;		// 견적조건 + 항구 
			String ctrtTmplYn = null;						// 계약서 템플릿 유무
			String smplReqYn = null;						// 샘플요청유무
			String qlfcReqYn = null;							// 자격 요청 유무
			String custOrdProcCont = null;				 // 주문 프로세스
			
			String dlvModeCd = null;	 					// 견적조건
			String dlvDestCd = null;						// 항구
			
			String ordTypeCd = null;
			// 엑셀에서 ExcelClientReqGudsVO 변수들 가져와서 대입.
			userAlasCnsNm = StringUtil.excelGetCell(sheet.getRow(1).getCell(2));  						//담당자
			custId = StringUtil.excelGetCell(sheet.getRow(1).getCell(4));    						// 클라이언트
			ordReqDt = StringUtil.excelGetCell(sheet.getRow(1).getCell(6)); 						// 문의일자
			if(ordReqDt!=null){
				ordReqDt = ordReqDt.replace("-", "");
				if("".equals(ordReqDt)) ordReqDt=null;
				if(ordReqDt.length()>8) ordReqDt=null;
				ordHopeArvlDt = StringUtil.excelGetCell(sheet.getRow(2).getCell(2)); 				// 희망 인도일자
				ordHopeArvlDt = ordHopeArvlDt.replace("-", "");
			}
			if(ordHopeArvlDt!=null){
				if(ordHopeArvlDt.length()>8) ordHopeArvlDt=null;	
			}
			dlvModeCdPlusdlvDestCd = StringUtil.excelGetCell(sheet.getRow(2).getCell(4));	// 견적조건 + 항구 	
//			약자 - 풀네임(항구)
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

			// 변수들을 SMS_MS_ORD 에 집어 넣는다.
			SmsMsOrdVO smsMsOrdVO = new SmsMsOrdVO();     						// 1. SMS_MS_ORD 에 넣을 VO
			smsMsOrdVO.setUserAlasCnsNm(userAlasCnsNm);// 담당자명 : 중문화명
			smsMsOrdVO.setCustId(custId);
			smsMsOrdVO.setOrdReqDt(ordReqDt);
			smsMsOrdVO.setOrdHopeArvlDt(ordHopeArvlDt);
			smsMsOrdVO.setDlvModeCd(dlvModeCd);
			smsMsOrdVO.setDlvDestCd(dlvDestCd);
			smsMsOrdVO.setCtrtTmplYn(ctrtTmplYn);
			smsMsOrdVO.setSmplReqYn(smplReqYn);
			smsMsOrdVO.setQlfcReqYn(qlfcReqYn);
			smsMsOrdVO.setCustOrdProcCont(custOrdProcCont);
		
			String ordNo = null;
			ordNo = smsMsOrdDAO.selectSmsMsOrdMaxTodaysOrdNo();
			smsMsOrdVO.setOrdNo(ordNo);
//				smsMsOrdVO.setB5cOrdNo(b5cOrdNo);  // 방우차이 아니니까 필요 없음.  null
			smsMsOrdVO.setOrdTypeCd("N000620300");  // 주문유형코드		오프라인
			smsMsOrdVO.setOrdStatCd("N000550100");		 // 주문상태 코드 : 접수(N000550100)
			LOGGER.debug("2.1.2.1  SMS_MS_ORD 에 넣을 VO : "+smsMsOrdVO.toString() );
			smsMsOrdDAO.insertSmsMsOrd_S(smsMsOrdVO);  											// SMS_MS_ORD 에 INSERT		//////////////////////////////////////////////
			LOGGER.debug("2.1.2.2======SMS_MS_ORD 에 INSERT==========완료" );

			
			SmsMsUserVO smsMsUserVO = new SmsMsUserVO();
			smsMsUserVO.setUserAlasCnsNm(userAlasCnsNm); 								//  SMS_MS_USER 에 담당자 중국어 화명 있는지 검색
			List <SmsMsUserVO> smsMsUserVOList = smsMsUserDAO.selectSmsMsUser(smsMsUserVO);
			if(smsMsUserVOList.size()==1){		// 몇개 더 있을수도 있지만, 여러개가 검색되면 차라리 mapping 을 안 하는 것이 낫다.
				SmsMsOrdUserVO smsMsOrdUserVO = new SmsMsOrdUserVO();
				smsMsOrdUserVO.setOrdNo(ordNo);
				smsMsOrdUserVO.setUserEml(smsMsUserVOList.get(0).getUserEml());
				smsMsOrdUserDAO.insertSmsMsOrdUser_S(smsMsOrdUserVO);						// SMS_MS_ORD_USER 에 INSERT		//////////////////////////////////////////////
			}
			
			// SMS_MS_ORD_HIST 추가.
			SmsMsOrdHistVO smsMsOrdHistVO = new SmsMsOrdHistVO();
			smsMsOrdHistVO.setOrdNo(ordNo);
			String ordHistSeq = smsMsOrdHistDAO.selectSmsMsOrdHistSeqCount(smsMsOrdHistVO);
			int tempSeq = Integer.parseInt(ordHistSeq)+1;
			ordHistSeq=Integer.toString(tempSeq);
			smsMsOrdHistVO.setOrdHistSeq(ordHistSeq);
			smsMsOrdHistVO.setOrdStatCd("N000550100");       // 주문상태 코드 : 접수(N000550100)
			smsMsOrdHistVO.setOrdHistWrtrEml(custId);  				// Excel 을 클라이언트가 올렸으니까, 클라이언트 이름으로
//				smsMsOrdHistVO.setOrdHistRegDttm(ordHistRegDttm);     // SQL 문에서 NOW() 사용
			smsMsOrdHistVO.setOrdHistHistCont("주문 접수");
			LOGGER.debug("2.1.3.1.  SMS_MS_ORD_HIST 에 넣을 VO : "+smsMsOrdHistVO.toString());

			smsMsOrdHistDAO.insertSmsMsOrdHist(smsMsOrdHistVO);								// SMS_MS_ORD_HIST 에 INSERT		//////////////////////////////////////////////
			LOGGER.debug("2.1.3.2.======SMS_MS_ORD_HIST 에 INSERT==========완료");
			
			////////////////////////////////////////////////////////////////////////////////////////////////////////////
			LOGGER.debug("2.1.4.1 엑셀에서 주문의 상품 리스트를 뽑아온다." );
			int rows = sheet.getPhysicalNumberOfRows();
			System.out.println("row 갯수 : " + rows);

			int tempNum=1;   					//ord_guds_seq에 넣을 숫자 관리용
			ordNo = ordNo;						//ordNo
			String ord_guds_seq=null;   		//NO
			String gudsId=null;					//상품id   -   b5c gudsId 와는 별개.   이 테이블 자체의 유일키 개념
			String gudsUpcId=null;       		//상품바코드		
			String ordGudsCnsNm=null;  		//중문 상품명
			String ordGudsQty=null;     		//상품 수량, (예상요청)수량
			String ordGudsSizeVal=null;		//주문상품크기값 ,규격
			String ordGudsSalePrc=null;		//주문상품판매가(PO단가USD)
			String ordGudsUrlAddr=null;	    // //주문상품url주소, 링크

			boolean gudsExist = false;
			for(int i=6; i<rows-1; i++){
				
				
				gudsUpcId = StringUtil.excelGetCell(sheet.getRow(i).getCell(1));
				ordGudsCnsNm = StringUtil.excelGetCell(sheet.getRow(i).getCell(2));
				ordGudsQty = StringUtil.excelGetCell(sheet.getRow(i).getCell(3));
				ordGudsSizeVal =  StringUtil.excelGetCell(sheet.getRow(i).getCell(4)); 
				ordGudsSalePrc = StringUtil.excelGetCell(sheet.getRow(i).getCell(5));
//				유효기간 =   ?????= StringUtil.excelGetCell(sheet.getRow(i).getCell(6));      //skip 무시
				ordGudsUrlAddr = StringUtil.excelGetCell(sheet.getRow(i).getCell(7));
				
				// 상품 정보 중에서, (gudsUpcId) 가 빠지면 의미가 없다.
				if(gudsUpcId!=null && "".equals(gudsUpcId)!=true){
					gudsExist = true;
					ord_guds_seq =  String.valueOf(tempNum++);
					SmsMsOrdGudsVO smsMsOrdGudsVO = new SmsMsOrdGudsVO();											// SMS_MS_ORD_GUDS 에 넣을 VO
					smsMsOrdGudsVO.setOrdNo(ordNo);
					smsMsOrdGudsVO.setOrdGudsSeq(ord_guds_seq);
//					gudsId = smsMsOrdGudsDAO.selectSmsMsOrdGudsGudsIdCount();
//					smsMsOrdGudsVO.setGudsId(gudsId);
					smsMsOrdGudsVO.setOrdGudsMpngYn("N");
					smsMsOrdGudsVO.setGudsUpcId(gudsUpcId);
					smsMsOrdGudsVO.setOrdGudsCnsNm(ordGudsCnsNm);
					smsMsOrdGudsVO.setOrdGudsQty(ordGudsQty);
					smsMsOrdGudsVO.setOrdGudsSalePrc(ordGudsSalePrc);
					smsMsOrdGudsVO.setOrdGudsUrlAddr(ordGudsUrlAddr);
					
					System.out.println(ord_guds_seq+"-"+gudsUpcId+"-"+ordGudsCnsNm+"-"+ordGudsQty+"-"+ordGudsSizeVal+"-"+ordGudsSalePrc+"-"+ordGudsUrlAddr);
					
					smsMsOrdGudsDAO.insertSmsMsOrdGuds_S(smsMsOrdGudsVO);
					LOGGER.debug(smsMsOrdGudsVO.toString());
				}
			}
			if(gudsExist == false){        // 상품이 하나도 없으면, 주문생성 X
				throw new Exception("Goods list is empty!!"); 
			}
			

			LOGGER.debug("2.1.4.2.======SMS_MS_ORD_GUDS 에 INSERT==========완료" );
		}



		// orderManament 의 main Select
		public List<SmsMsOrdVO> selectSmsMsOrdForOrderManamentView(SmsMsOrdVO smsMsOrdVO) throws Exception{
			return smsMsOrdDAO.selectSmsMsOrdForOrderManamentView(smsMsOrdVO);
		}
		
		//변경된 detail정보를 update
		@Override
		public void updateSmsMsOrdGudsDetail(OrderDetailVO orderDetailVo, List<SmsMsOrdGudsVO> smsMsOrdGudsList, String wrtrEml) throws Exception {		
			
			//1. 주문상세정보 업데이트
			smsMsOrdDAO.updateSmsMsOrdDetail(orderDetailVo);		
			
			//2. 주문사용자 업데이트
			SmsMsOrdUserVO opr= new SmsMsOrdUserVO();
			opr.setOrdNo(orderDetailVo.getOrdNo());
			
			System.out.println("inservice**********************:"+orderDetailVo);

			smsMsOrdUserDAO.deleteSmsMsOrdUserByOrdNo(orderDetailVo.getOrdNo());
			if(orderDetailVo.getOprCns()!=null){
				opr.setUserEml(orderDetailVo.getOprCns());
				smsMsOrdUserDAO.insertSmsMsOrdUser_S(opr);
			}
			if(orderDetailVo.getOprKr()!=null){
				opr.setUserEml(orderDetailVo.getOprKr());
				smsMsOrdUserDAO.insertSmsMsOrdUser_S(opr);
			}
			
			
			
			//3.주문상품List 업데이트
			for(SmsMsOrdGudsVO vo :smsMsOrdGudsList){
				//바코드를 임의로 변경한경우 매핑해제
				SmsMsGudsVO tempVo = new SmsMsGudsVO();					//상품명+upc아이디로 해당상품을 조회후 존재하지 않는 경우 매핑을 해제
				tempVo.setGudsId(vo.getGudsId());
				tempVo.setGudsUpcId(vo.getGudsUpcId());
				if(smsMsGudsDAO.selectSmsMsGudsByVO(tempVo)==null){
					vo.setOrdGudsMpngYn("N");
				}
				smsMsOrdGudsDAO.updateSmsMsOrdGudsDetail(vo);		//상품정보업데이트
			}
			
			
			//히스토리업데이트			
			 SmsMsOrdHistVO histVo = new SmsMsOrdHistVO();
			 histVo.setOrdNo(orderDetailVo.getOrdNo());
			 String seq=smsMsOrdHistDAO.selectSmsMsOrdHistSeqCount(histVo);
			 System.out.println("seq :"+seq);
			 if(seq!=null){
				 int tempSeq = Integer.parseInt(seq)+1;
				 seq=Integer.toString(tempSeq);
			 }else{
				 seq="0";
			 }

			 histVo.setOrdHistSeq(seq);
			 histVo.setOrdHistWrtrEml(wrtrEml);
			 
			 histVo.setOrdHistHistCont("진행");			//자동생성되는 진행코멘트
			 histVo.setOrdStatCd("N000550200");		//주문상태코드 : N000550200 진행
			 System.out.println(histVo);
			 smsMsOrdHistDAO.insertSmsMsOrdHist(histVo);
			 
			 //주문상태저장
			 SmsMsOrdVO statVo = new SmsMsOrdVO();
			 statVo.setOrdNo(orderDetailVo.getOrdNo());
			 statVo.setOrdStatCd("N000550200");
			 smsMsOrdDAO.updateSmsMsOrdStatCd(statVo);
			
		
			
		}

		@Override
		public void orderPOSave(OrderPOVO orderPoVo, OrderPOGudsVO orderPoGudsVo, int gudsCnt,String wrtrEml) throws Exception {

			
			//1.po정보를 DB에 삽입
			SmsMsEstmVO smsMsEstmVo = new SmsMsEstmVO();
			
			smsMsEstmVo.setOrdNo(orderPoVo.getOrdNo());
			smsMsEstmVo.setPoNo(orderPoVo.getPoNo());
			smsMsEstmVo.setDlvModeCd(orderPoVo.getDlvModeCd());
			smsMsEstmVo.setDlvAmt(orderPoVo.getDlvAmt());
			
			smsMsEstmVo.setPoDt(StringUtil.dateToDt(orderPoVo.getPoDt()));
			smsMsEstmVo.setPoRegrEml(orderPoVo.getPoRegrEml());
			smsMsEstmVo.setPoSumAmt(orderPoVo.getPoAmt());
			smsMsEstmVo.setStdXchrKindCd(orderPoVo.getStdXchrKindCd());
			
			
			smsMsEstmVo.setStdXchrAmt(orderPoVo.getStdXchrAmt());
			smsMsEstmVo.setOrdArvlDt(StringUtil.dateToDt(orderPoVo.getOrdArvlDt()));
			smsMsEstmVo.setPoMemoCont(orderPoVo.getPoMemoCont());
			
			
			smsMsEstmDAO.deleteSmsMsEstm(orderPoVo.getOrdNo());		//기존 PO정보 삭제
			smsMsEstmDAO.insertSmsMsEstm(smsMsEstmVo);					//새로운 PO정보 삽입
			
			
			
			//2.1 po올라온 상품이 smsDb에 없는 경우 : 새로생성
			//2.2 바코드값으로 db에서검색 ->po올라온 상품의 (인박스)정보가 smsDb의 (인박스)와 다른경우  : 인박스정보만 가져온다
			//2.3 기타정보를 po에 삽입한다
			
			
			if(gudsCnt>0)	{  //비어있는 경우 공백을 넣어서 NullPoint Exception을 방지		
				String[] imgSrcPath=orderPoGudsVo.getImgSrcPath().replace("," , " , ").split(",");
				String[] gudsUpcId=orderPoGudsVo.getGudsUpcId().replace("," , " , ").split(",");
				String[] gudsCnsNm=orderPoGudsVo.getGudsCnsNm().replace("," , " , ").split(",");
				String[] gudsKorNm=orderPoGudsVo.getGudsKorNm().replace("," , " , ").split(",");
				String[] ordGudsQty=orderPoGudsVo.getOrdGudsQty().replace("," , " , ").split(",");
				
				String[] gudsInbxQty=orderPoGudsVo.getGudsInbxQty().replace("," , " , ").split(",");
				String[] vatYn=orderPoGudsVo.getVatYn().replace("," , " , ").split(",");
				String[] pcPrc=orderPoGudsVo.getPcPrc().replace("," , " , ").split(",");
				String[] pcPrcVat=orderPoGudsVo.getPcPrcVat().replace("," , " , ").split(",");
				String[] pcPrcNoVat=orderPoGudsVo.getPcPrcNoVat().replace("," , " , ").split(",");
				
				String[] poPrc=orderPoGudsVo.getPoPrc().replace("," , " , ").split(",");
				String[] poPrcSum=orderPoGudsVo.getPoPrcSum().replace("," , " , ").split(",");
				String[] poXchrPrc=orderPoGudsVo.getPoXchrPrc().replace("," , " , ").split(",");
				String[] poXchrPrcSum=orderPoGudsVo.getPoXchrPrcSum().replace("," , " , ").split(",");
				String[] pvdrnNm=orderPoGudsVo.getPvdrnNm().replace("," , " , ").split(",");
				
				String[] crn = orderPoGudsVo.getCrn().replace("," , " , ").split(",");
				
				//3-0.현재 주문번호로 생성되어있는 기존의 상품정보를 삭제한다.
				smsMsEstmGudsDAO.deleteSmsMsEstmGudsByOrdNm(orderPoVo.getOrdNo());
				
				
				List<SmsMsEstmGudsVO> smsMsEstmGudsList = new ArrayList<SmsMsEstmGudsVO>();
				String newGudsId = smsMsGudsDAO.selectSmsMsGudsGudsId();
				for(int i=0; i<gudsCnt; i++){
					//3-1.바코드로 smsMsGuds테이블을  검색한다 
					System.out.println(gudsUpcId[i].trim());
					SmsMsGudsVO smsMsGudsVo = smsMsGudsDAO.selectSmsMsGudsByUpcId(gudsUpcId[i].trim());
					System.out.println("검색직후 smsMsGudsVo : "+smsMsGudsVo);
					smsMsEstmGudsList.add(new SmsMsEstmGudsVO());
					//3-2.기존에 상품테이블에 존재안함 : gudsId생성후 새로운 상품 삽입,
					if(smsMsGudsVo==null){
							newGudsId=Integer.toString((Integer.parseInt(newGudsId)+1));
							smsMsEstmGudsList.get(i).setGudsId(newGudsId);
							smsMsEstmGudsList.get(i).setOrdGudsKorNm(gudsKorNm[i].trim());
							smsMsEstmGudsList.get(i).setOrdGudsCnsNm(gudsCnsNm[i].trim());
							
							//새로운 상품 생성
							smsMsGudsVo = new SmsMsGudsVO();
							 smsMsGudsVo.setGudsId(newGudsId);
							 System.out.println("newGudsId : " +newGudsId);
							 //smsMsGudsVo.setBrndId(brndId);	 브랜드?
							 smsMsGudsVo.setGudsKorNm(gudsKorNm[i].trim());
							 smsMsGudsVo.setGudsCnsNm(gudsCnsNm[i].trim());
							 smsMsGudsVo.setGudsUpcId(gudsUpcId[i].trim());
							 smsMsGudsVo.setGudsVatRfndYn(vatYn[i].trim());
							 //smsMsGudsVo.setGudsUrlAddr(gudsUrlAddr[i].trim());		항목이없음
							 smsMsGudsVo.setGudsInbxQty(gudsInbxQty[i].trim());
							
							 //SmsMsGuds 테이블에 삽입
							 System.out.println(smsMsGudsVo);
							//smsMsGudsDAO.deleteSmsMsGuds(smsMsGudsVo);				
							smsMsGudsDAO.insertSmsMsGuds_S(smsMsGudsVo);
							//이미지테이블에 이미지 삽입					
							SmsMsGudsImgVO smsMsGudsImgVO= new SmsMsGudsImgVO();																			
							smsMsGudsImgVO.setGudsId(newGudsId);
							smsMsGudsImgVO.setGudsImgCd("N000080200");//상품이미지코드 N000080100(대표이미지) N000080200(목록이미지) 목록이미지로 사용
							smsMsGudsImgVO.setGudsImgOrgtFileNm(imgSrcPath[i]);
							smsMsGudsImgVO.setGudsImgSysFileNm(imgSrcPath[i]);
							
							smsMsGudsImgDAO.deleteSmsMsGudsImg(smsMsGudsImgVO);			//매핑이 잘못되어져 이미지가 들어오는 경우 해당이미지를 삭제
							smsMsGudsImgDAO.insertSmsMsGudsImg(smsMsGudsImgVO);
							
					//3-3.기존에 상품테이블에 존재함 : 인박스 수량만 업데이트 후 DB정보로 변환 
					}else{
						smsMsGudsVo.setGudsInbxQty(gudsInbxQty[i]);
						smsMsGudsDAO.updateSmsMsGudsByInbxQty(smsMsGudsVo);
						
						smsMsEstmGudsList.get(i).setGudsId(smsMsGudsVo.getGudsId());
						smsMsEstmGudsList.get(i).setOrdGudsKorNm(smsMsGudsVo.getGudsKorNm());
						smsMsEstmGudsList.get(i).setOrdGudsCnsNm(smsMsGudsVo.getGudsCnsNm());						
					}
					
					//smsMsGuds테이블을  검색결과와 상관없이 입력값을 받는 부분
					smsMsEstmGudsList.get(i).setOrdNo(orderPoVo.getOrdNo());		
					smsMsEstmGudsList.get(i).setOrdGudsQty(ordGudsQty[i].trim());
					smsMsEstmGudsList.get(i).setOrdGudsOrgPrc(pcPrc[i].trim());
					smsMsEstmGudsList.get(i).setOrdGudsSalePrc(poPrc[i].trim());
					smsMsEstmGudsList.get(i).setOrdGudsPrvdNm(pvdrnNm[i].trim());
					smsMsEstmGudsList.get(i).setOrdGudsPrvdCrn(crn[i].trim());
					
					//3_4.값을 estm_guds테이블에 저장한다.
					System.out.println(smsMsEstmGudsList.get(i));
					smsMsEstmGudsDAO.insertSmsMsEstmGuds(smsMsEstmGudsList.get(i));
					

				


				}//end for(int i=0; i<gudsCnt; i++){
			
				
			}//end if(gudsCnt>0)
			
			//5.히스토리추가
			
			 SmsMsOrdHistVO histVo = new SmsMsOrdHistVO();
			 histVo.setOrdNo(orderPoVo.getOrdNo());
			 
			 System.out.println("ordNo :"+orderPoVo.getOrdNo());
			 String seq=smsMsOrdHistDAO.selectSmsMsOrdHistSeqCount(histVo);
			 System.out.println("seq :"+seq);
			 if(seq!=null){
				 int tempSeq = Integer.parseInt(seq)+1;
				 seq=Integer.toString(tempSeq);
			 }else{
				 seq="0";
			 }
			 System.out.println("seq :"+seq);
			 System.out.println("ordNo :"+orderPoVo.getOrdNo());
			 
			 histVo.setOrdHistSeq(seq);
			 histVo.setOrdHistWrtrEml(wrtrEml);
			 
			 histVo.setOrdHistHistCont("P/O확정");			//자동생성되는 확정코멘트
			 histVo.setOrdStatCd("N000550300");		//주문상태코드 : N000550300 확정
			 System.out.println(histVo);
			 //주문테이블의 스탯도 변경해줘야한다
			 SmsMsOrdVO statVo = new SmsMsOrdVO();
			 statVo.setOrdNo(orderPoVo.getOrdNo());
			 statVo.setOrdStatCd("N000550300");
			 smsMsOrdDAO.updateSmsMsOrdStatCd(statVo);
			 
			 smsMsOrdHistDAO.insertSmsMsOrdHist(histVo);
			
		}//end public void orderPOSave(..)


		// orderManagementView 에서 [저장]을 눌렀을 떄, 한 row update 하는 SQL
		public void updateSmsMsOrdInOrderManagementView(SmsMsOrdVO smsMsOrdVO){
					smsMsOrdDAO.updateSmsMsOrdInOrderManagementView(smsMsOrdVO);
		}
		
		public List<SmsMsUserVO> selectSmsMsUser(SmsMsUserVO smsMsUserVO) throws Exception{
			return smsMsUserDAO.selectSmsMsUser(smsMsUserVO);
		}

		@Override
		public String selectSmsMsOrdFileSeqNext(String ordNo) throws Exception {
			return smsMsOrdFileDAO.selectSmsMsOrdFileSeqNext(ordNo);
		}

		@Override
		public void insertSmsMsOrdFile(SmsMsOrdFileVO smsMsOrdFileVO) throws Exception {
			smsMsOrdFileDAO.insertSmsMsOrdFile(smsMsOrdFileVO);
			
		}
		
}//end class