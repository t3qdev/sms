package com.b5m.sms.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsOrdGudsVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsOrdGudsDAO")
public class SmsMsOrdGudsDAO extends EgovAbstractDAO {
	
	@SuppressWarnings("unchecked")
	public List<SmsMsOrdGudsVO> selectSmsMsOrdGudsByOrdNo(String ordNo)	throws Exception {
		return (List<SmsMsOrdGudsVO>)list("smsMsOrdGudsDAO.selectSmsMsOrdGudsByOrdNo",ordNo);
	}

	public String selectSmsMsOrdGudsSeqCount(String ordNo) throws Exception{
		return (String) select ("smsMsOrdGudsDAO.selectSmsMsOrdGudsSeqCount",ordNo);
	}
	public String selectSmsMsOrdGudsGudsIdCount() throws Exception{
		return (String) select ("smsMsOrdGudsDAO.selectSmsMsOrdGudsGudsIdCount");
	}	
	
	public void insertSmsMsOrdGuds_S(SmsMsOrdGudsVO smsMsOrdGudsVO) throws Exception{
		insert("smsMsOrdGudsDAO.insertSmsMsOrdGuds_S", smsMsOrdGudsVO);
	}
	
	public void updateSmsMsOrdGudsMpng(SmsMsOrdGudsVO smsMsOrdGudsVO) throws Exception{
		update("smsMsOrdGudsDAO.updateSmsMsOrdGudsMpng",smsMsOrdGudsVO);
	}
	
	public void updateSmsMsOrdGudsDetail(SmsMsOrdGudsVO smsMsOrdGudsVo){
		update("smsMsOrdGudsDAO.updateSmsMsOrdGudsDetail",smsMsOrdGudsVo);
	}
	public void insertSmsMsOrdGudsFromExcel(SmsMsOrdGudsVO smsMsOrdGudsVO) throws Exception{
		insert("smsMsOrdGudsDAO.insertSmsMsOrdGudsFromExcel", smsMsOrdGudsVO);
	}
	
}
