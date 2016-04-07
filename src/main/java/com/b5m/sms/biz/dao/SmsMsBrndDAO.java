package com.b5m.sms.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsBrndVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsBrndDAO")
public class SmsMsBrndDAO extends EgovAbstractDAO {
	public void insertSmsMsBrnd_S(SmsMsBrndVO smsMsBrndVO) throws Exception{
		insert("smsMsBrndDAO.insertSmsMsBrnd_S",smsMsBrndVO);
	}
	@SuppressWarnings("unchecked")
	public List<SmsMsBrndVO> selectSmsMsBrnd(SmsMsBrndVO smsMsBrndVO)throws Exception{
		return (List<SmsMsBrndVO>) list("smsMsBrndDAO.selectSmsMsBrnd", smsMsBrndVO );
	}

}


