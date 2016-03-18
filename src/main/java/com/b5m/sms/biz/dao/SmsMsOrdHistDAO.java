package com.b5m.sms.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsOrdHistVO;
import com.b5m.sms.vo.SmsMsRoleUserVO;
import com.b5m.sms.vo.SmsMsUserVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsOrdHistDAO")
public class SmsMsOrdHistDAO extends EgovAbstractDAO{

	@SuppressWarnings("unchecked")
	public List<SmsMsOrdHistVO> selectSmsMsOrdHist(SmsMsOrdHistVO smsMsOrdHistVO) throws Exception{
		return  (List<SmsMsOrdHistVO>) list("smsMsOrdHistDAO.selectSmsMsOrdHist",smsMsOrdHistVO);
	}
	@SuppressWarnings("unchecked")
	public List<SmsMsOrdHistVO> selectSmsMsOrdHistMaxSeq(SmsMsOrdHistVO smsMsOrdHistVO) throws Exception{
		return  (List<SmsMsOrdHistVO>) list ("smsMsOrdHistDAO.selectSmsMsOrdHistMaxSeq",smsMsOrdHistVO);
	}
	
	public void updateSmsMsOrdHist(SmsMsOrdHistVO smsMsOrdHistVO)throws Exception{
		update("smsMsOrdHistDAO.updateSmsMsOrdHist",smsMsOrdHistVO);
	}
	
	public void insertSmsMsOrdHist(SmsMsOrdHistVO smsMsOrdHistVO)throws Exception{
		insert("smsMsOrdHistDAO.insertSmsMsOrdHist",smsMsOrdHistVO);
	}
	
	public String selectSmsMsOrdHistSeqCount(SmsMsOrdHistVO smsMsOrdHistVO)throws Exception{
		return (String) select("smsMsOrdHistDAO.selectSmsMsOrdHistSeqCount",smsMsOrdHistVO);
	}
	public void deleteSmsMsOrdHist_S() throws Exception{
		delete("smsMsOrdHistDAO.deleteSmsMsOrdHist_S");
	}
	
	
	
}
