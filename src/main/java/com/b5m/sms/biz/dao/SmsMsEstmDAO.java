package com.b5m.sms.biz.dao;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsEstmVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsEstmDAO")
public class SmsMsEstmDAO extends EgovAbstractDAO {
	public void insertSmsMsEstm(SmsMsEstmVO smsMsEstmVo) throws Exception{
		insert("smsMsEstmDAO.insertSmsMsEstm",smsMsEstmVo);
	}
	public void deleteSmsMsEstm(String ordNo) throws Exception{
		delete("smsMsEstmDAO.deleteSmsMsEstm",ordNo);
		
	}
}
