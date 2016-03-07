package com.b5m.sms.biz.dao;

import java.util.List;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsUserVO;

@Repository("smsMsUserDAO")
public class SmsMsUserDAO extends EgovAbstractDAO{

	@SuppressWarnings("unchecked")
	public List<SmsMsUserVO> selectSmsMsUser(SmsMsUserVO smsMsUserVO) throws Exception{
		return (List<SmsMsUserVO>) list("smsMsUserDAO.selectSmsMsUser",smsMsUserVO);
	}
	public void updateSmsMsUser(SmsMsUserVO smsMsUserVO)throws Exception{
        update("smsMsUserDAO.updateSmsMsUser", smsMsUserVO);
	}
	public void updateSmsMsUserWithoutPassword(SmsMsUserVO smsMsUserVO)throws Exception{
        update("smsMsUserDAO.updateSmsMsUserWithoutPassword", smsMsUserVO);
	}
	public void insertSmsMsUser(SmsMsUserVO smsMsUserVO)throws Exception{
		insert("smsMsUserDAO.insertSmsMsUser",smsMsUserVO);
	}
	//주문에 연결된 유저정보를 가져옴
	public List<SmsMsUserVO> selectSmsMsUserbyOrdNo(String ordNo) throws Exception{
		return (List<SmsMsUserVO>) list("smsMsUserDAO.selectSmsMsUserbyOrdNo",ordNo);
	}


}
