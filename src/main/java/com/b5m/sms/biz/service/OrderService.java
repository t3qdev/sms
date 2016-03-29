package com.b5m.sms.biz.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

import com.b5m.sms.common.file.FileResultVO;
import com.b5m.sms.common.security.User;
import com.b5m.sms.vo.CodeVO;
import com.b5m.sms.vo.ExcelClientReqGudsVO;
import com.b5m.sms.vo.OrderCalculateVO;
import com.b5m.sms.vo.OrderDetailVO;
import com.b5m.sms.vo.OrderPOGudsVO;
import com.b5m.sms.vo.OrderPOVO;
import com.b5m.sms.vo.SmsMsEstmVO;
import com.b5m.sms.vo.SmsMsOrdFileVO;
import com.b5m.sms.vo.SmsMsOrdGudsVO;
import com.b5m.sms.vo.SmsMsOrdHistVO;
import com.b5m.sms.vo.SmsMsOrdVO;
import com.b5m.sms.vo.SmsMsUserVO;
import com.b5m.sms.vo.TbMsCmnCdVO;
import com.b5m.sms.vo.TbMsOrdBatchVO;
import com.b5m.sms.vo.TbMsOrdVO;


public interface OrderService {

	public List<SmsMsOrdHistVO> selectSmsMsOrdHist(SmsMsOrdHistVO smsMsOrdHistVO)  throws Exception;

	public List<SmsMsOrdHistVO> selectSmsMsOrdHistMaxSeq(SmsMsOrdHistVO smsMsOrdHistVO)  throws Exception;
	
	public void updateSmsMsOrdHist(SmsMsOrdHistVO smsMsOrdHistVO) throws Exception;

	public void insertSmsMsOrdHist(SmsMsOrdHistVO smsMsOrdHistVO) throws Exception;

	public String selectSmsMsOrdHistSeqCount(SmsMsOrdHistVO smsMsOrdHistVO)throws Exception;
	
	public void updateSmsMsOrdBact(OrderCalculateVO orderCalculateVO) throws Exception;
	
	//orderDetail페이지에 표시할 클라이언트 견적서 정보
	public OrderDetailVO selectSmsMsOrdDetail(String ordNo) throws Exception;
	
	//orderDetail페이지에 표시할 파일 정보
	public List<SmsMsOrdFileVO> selectSmsMsOrdFileByOrdNo(String ordNo) throws Exception;
	
	//코드리스트를 가져올때 사용
	public List<CodeVO> selectTbmsCmnCd(String Cd) throws Exception;
	
	//정산내용을 조회
	public OrderCalculateVO selectSmsMsOrdCalculate(String ordNo) throws Exception;
	
	//////////////////////////////////////////////////////////////////////////////////////////

	//batch : b5c 에 있는 주문+상품을   SMS 로 가져오는 batch
	public void batchSmsMsOrd() throws Exception;
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	public void insertSmsMsOrd_S(SmsMsOrdVO smsMsOrdVO)throws Exception;
	
	public String selectSmsMsOrdByB5cOrdNo(String b5cOrdNo) throws Exception;
	
	//정산내용 삽입 : 정산을 첫번째로 수행하는 경우에는 history도 생성
	public void updateSmsMsOrdCalculate(OrderCalculateVO vo,String first) throws Exception;
	
	
	//ordermapping페이지에서 상품을 선택하는 경우 mpng항목 Y로 (매핑)
	public void updateSmsMsOrdGudsMpng(SmsMsOrdGudsVO smsMsOrdGudsVO) throws Exception;

	// orderManagment 에서 사용, 클라이언트 요청 견적서를(EXCEL) 이용해서 주문이 새로 들어왔을 때, SMS_MS_ORD, SMS_MS_ORD_GUDS 에 INSERT
	public String insertExcelSmsMsOrdNSmsMsOrdGuds(Sheet sheet, User user) throws Exception;
	
	// orderManament 의 main Select
	public List<SmsMsOrdVO> selectSmsMsOrdForOrderManamentView(SmsMsOrdVO smsMsOrdVO) throws Exception;
	
	//orderDetail 상품의 정보를 저장
	public void updateSmsMsOrdGudsDetail(OrderDetailVO orderDetailVo, List<SmsMsOrdGudsVO> smsMsOrdGudsList,String wrtrEml) throws Exception;
	
	//orderPO를 저장
	public void orderPOSave(OrderPOVO orderPoVo, OrderPOGudsVO orderPoGudsVo,int gudsCnt, FileResultVO fileResultVo) throws Exception;
	
	// orderManagementView 에서 [저장]을 눌렀을 떄, 한 row update 하는 SQL
	public void updateSmsMsOrdInOrderManagementView(SmsMsOrdVO smsMsOrdVO);
	
	
	public List<SmsMsUserVO> selectSmsMsUser(SmsMsUserVO smsMsUserVO) throws Exception;
	
	//주문 관련 파일 시퀀스를 가져온다
	public String selectSmsMsOrdFileSeqNext(String ordNo) throws Exception;
	
	//주문 관련 파일 시퀀스를 
	public void insertSmsMsOrdFile(SmsMsOrdFileVO smsMsOrdFileVO) throws Exception;
	
	//정산정보를 검색
	public SmsMsEstmVO selectSmsMsEstmVO(String ordNo) throws Exception;
	
	//ordertable statcd 변경
	public void updateSmsMsOrdStatCd(SmsMsOrdVO smsMsOrdVO) throws Exception;
	
	
	// orderManagementView 에서 excel 다운 로드할 때 사용.
	public SmsMsOrdVO selectSmsMsOrdForOrderManamentViewByOrdNo(SmsMsOrdVO smsMsOrdVO)throws Exception;
	
	public List<SmsMsOrdVO>orderManageMentExcelDownload(String jsonString) throws Exception;

	
	// SMS_MS_USER 뺴고 모두 지운다 -> 배치 처음 하기 전으로 돌아가 배치를 다시 돌려보기 위함
	public void deleteBeforeFirstBatch() throws Exception;
	
	public String selectSmsMsOrdCount(SmsMsOrdVO smsMsOrdVO) throws Exception;
	
	public TbMsOrdVO selectTbMsOrdSplReqCont(String ordNo)throws Exception;
	
	public String selectSmsMsGudsBrndId(String gudsId) throws Exception;
}

