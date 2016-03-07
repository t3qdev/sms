package com.b5m.sms.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.b5m.sms.vo.SmsMsRoleUserVO;
import com.b5m.sms.vo.SmsMsUserVO;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("smsMsRoleUserDAO")
public class SmsMsRoleUserDAO extends EgovAbstractDAO{

	@SuppressWarnings("unchecked")
	public List<SmsMsRoleUserVO> selectSmsMsRoleUser(SmsMsRoleUserVO smsMsRoleUserVO) throws Exception{
		return  (List<SmsMsRoleUserVO>) list("smsMsRoleUserDAO.selectSmsMsRoleUser",smsMsRoleUserVO);
	}
	public void deleteSmsMsRoleUserByUserEml(SmsMsRoleUserVO smsMsRoleUserVO)throws Exception{
		delete("smsMsRoleUserDAO.deleteSmsMsRoleUserByUserEml",smsMsRoleUserVO);
	}
	public void insertSmsMsRoleUser(SmsMsRoleUserVO smsMsRoleUserVO)throws Exception{
		insert("smsMsRoleUserDAO.insertSmsMsRoleUser",smsMsRoleUserVO);
	}
}
