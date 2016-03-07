package com.b5m.sms.biz.dao;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsEstmGudsVO;
import com.b5m.sms.vo.SmsMsEstmVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsEstmGudsDAO")
public class SmsMsEstmGudsDAO extends EgovAbstractDAO {
	
	public void insertSmsMsEstmGuds(SmsMsEstmGudsVO smsMsEstmGudsVo) throws Exception{
		insert("smsMsEstmGudsDAO.insertSmsMsEstmGuds",smsMsEstmGudsVo);
	}
	public void deleteSmsMsEstmGudsByOrdNm(String ordNo) throws Exception{
		delete("smsMsEstmGudsDAO.deleteSmsMsEstmGudsByOrdNm",ordNo);
		
	}
}