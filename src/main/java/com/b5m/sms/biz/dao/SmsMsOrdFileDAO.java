package com.b5m.sms.biz.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsOrdFileVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsOrdFileDAO")
public class SmsMsOrdFileDAO extends EgovAbstractDAO {	
	//주문번호에 해당하는 파일정보를 가져온다
	@SuppressWarnings("unchecked")
	public List<SmsMsOrdFileVO> selectSmsMsOrdFileByOrdNo(String ordNo) throws Exception{
		return (List<SmsMsOrdFileVO>)list("smsMsOrdFileDAO.selectSmsMsOrdFileByOrdNo",ordNo);
	}
	//다음 파일 시퀀스를 가져온다
	public String selectSmsMsOrdFileSeqNext(String ordNo){ 
		return (String)select("smsMsOrdFileDAO.selectSmsMsOrdFileSeqNext",ordNo);
	}
	//새로운 파일정보를 저장한다
	public void insertSmsMsOrdFile(SmsMsOrdFileVO smsMsOrdFileVO){
		insert("smsMsOrdFileDAO.insertSmsMsOrdFile",smsMsOrdFileVO);
	}

	public void deleteSmsMsOrdFile_S(){
		delete("smsMsOrdFileDAO.deleteSmsMsOrdFile_S");
	}
	
}
