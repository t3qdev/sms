package com.b5m.sms.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.CodeVO;
import com.b5m.sms.vo.OrderCalculateVO;
import com.b5m.sms.vo.OrderDetailVO;

import com.b5m.sms.vo.SmsMsOrdVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsOrdDAO")
public class SmsMsOrdDAO extends EgovAbstractDAO {
	public OrderDetailVO selectSmsMsOrdDetail(String ordNo){
		return (OrderDetailVO)select("smsMsOrdDAO.selectSmsMsOrdDetail",ordNo);
	}
	public void insertSmsMsOrd_BATCH()throws Exception{
		insert("smsMsOrdDAO.insertSmsMsOrd_BATCH");
	}
	
	public void insertSmsMsOrd_S(SmsMsOrdVO smsMsOrdVO)throws Exception{
		insert("smsMsOrdDAO.insertSmsMsOrd_S",smsMsOrdVO);
	}
	
	public String selectSmsMsOrdMaxTodaysOrdNo() throws Exception{
		return (String) select("smsMsOrdDAO.selectSmsMsOrdMaxTodaysOrdNo");
	}
	
	public String selectSmsMsOrdByB5cOrdNo(String b5cOrdNo) throws Exception{
		return (String) select("smsMsOrdDAO.selectSmsMsOrdByB5cOrdNo", b5cOrdNo);
	}
	@SuppressWarnings("unchecked")
	public List<CodeVO> selectTbMsCmnCd(String cd){
		return (List<CodeVO>) list("smsMsOrdDAO.selectTbMsCmnCd",cd);
		
	}
	//정산내용 검색
	public OrderCalculateVO selectSmsMsOrdCalculate(String ordNo){
		return (OrderCalculateVO)select("smsMsOrdDAO.selectSmsMsOrdCalculate",ordNo);
	}
	//정산내용 삽입
	public void updateSmsMsOrdCalculate(OrderCalculateVO vo){
		update("smsMsOrdDAO.updateSmsMsOrdCalculate",vo);
	}
	//order detail내용을 변경 
	public void updateSmsMsOrdDetail(OrderDetailVO orderDetailVo){
		update("smsMsOrdDAO.updateSmsMsOrdDetail",orderDetailVo);
	}
	
	@SuppressWarnings("unchecked")
	public List<SmsMsOrdVO> selectSmsMsOrdForOrderManamentView(SmsMsOrdVO smsMsOrdVO){
		return (List<SmsMsOrdVO>) list("smsMsOrdDAO.selectSmsMsOrdForOrderManamentView",smsMsOrdVO);
	}
	
	// orderManagementView 에서 [저장]을 눌렀을 떄, 한 row update 하는 SQL
	public void updateSmsMsOrdInOrderManagementView(SmsMsOrdVO smsMsOrdVO){
		update("smsMsOrdDAO.updateSmsMsOrdInOrderManagementView",smsMsOrdVO);
	}
	//ordertable statcd 변경
	public void updateSmsMsOrdStatCd(SmsMsOrdVO smsMsOrdVO){
		System.out.println("다오까지 왔다 : "+smsMsOrdVO.toString());
		update("smsMsOrdDAO.updateSmsMsOrdStatCd",smsMsOrdVO);
	}
	
}
