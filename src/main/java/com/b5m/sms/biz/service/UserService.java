package com.b5m.sms.biz.service;

import java.util.List;

import com.b5m.sms.vo.SmsMsRoleUserVO;
import com.b5m.sms.vo.SmsMsUserVO;


public interface UserService {

	public List<SmsMsUserVO> selectSmsMsUser(SmsMsUserVO smsMsUserVO)  throws Exception;

	public List<SmsMsRoleUserVO> selectSmsMsRoleUser(SmsMsRoleUserVO smsMsRoleUserVO) throws Exception;
	
	public void updateSmsMsUser(SmsMsUserVO smsMsUserVO) throws Exception;
	
	public void updateSmsMsUserNSmsMsRoleUser(SmsMsUserVO smsMsUserVO) throws Exception;
	
	public void insertSmsMsUserNSmsMsRoleUser(SmsMsUserVO smsMsUserVO) throws Exception;
	
	//주문번호와 관련된 사용자정보
	public List<SmsMsUserVO> selectSmsMsUserByOrdNo(String ordNo) throws Exception;
}

