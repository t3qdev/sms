package com.b5m.sms.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.b5m.sms.biz.dao.SmsMsRoleUserDAO;
import com.b5m.sms.biz.dao.SmsMsUserDAO;
import com.b5m.sms.biz.service.UserService;
import com.b5m.sms.vo.SmsMsRoleUserVO;
import com.b5m.sms.vo.SmsMsUserVO;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource(name="smsMsUserDAO")
	private SmsMsUserDAO smsMsUserDAO;
	
	@Resource(name="smsMsRoleUserDAO")
	private SmsMsRoleUserDAO smsMsRoleUserDAO;
	
	@Override
	public List<SmsMsUserVO> selectSmsMsUser(SmsMsUserVO smsMsUserVO) throws Exception {
		// TODO Auto-generated method stub
		return (List<SmsMsUserVO>) smsMsUserDAO.selectSmsMsUser(smsMsUserVO);
	}

	@Override
	public List<SmsMsRoleUserVO> selectSmsMsRoleUser(
			SmsMsRoleUserVO smsMsRoleUserVO) throws Exception {
		// TODO Auto-generated method stub
		return smsMsRoleUserDAO.selectSmsMsRoleUser(smsMsRoleUserVO) ;
	}

	@Override
	public void updateSmsMsUser(SmsMsUserVO smsMsUserVO) throws Exception {
		smsMsUserDAO.updateSmsMsUser(smsMsUserVO);
		
	}

	
	

	@Override
	public void updateSmsMsUserNSmsMsRoleUser(SmsMsUserVO smsMsUserVO)
			throws Exception {
	 // SMS_MS_USER 업데이트, SMS_MS_ROLE_USER은 DELETE 후 INSERT
		
		// password 가 null, 이거나 "" 이면, password 는 변경하지 않는다.
		if(smsMsUserVO.getUserPwd() == null || "".equals(smsMsUserVO.getUserPwd())){
			smsMsUserDAO.updateSmsMsUserWithoutPassword(smsMsUserVO);
		}else{
			smsMsUserDAO.updateSmsMsUser(smsMsUserVO);
		}
		SmsMsRoleUserVO smsMsRoleUserVO = new SmsMsRoleUserVO();
		smsMsRoleUserVO.setUserEml(smsMsUserVO.getUserEml());
		smsMsRoleUserDAO.deleteSmsMsRoleUserByUserEml(smsMsRoleUserVO);
		
		if(smsMsUserVO.getRoleGrpDivCd1()!=null){
			if("".equals(smsMsUserVO.getRoleGrpDivCd1())==false){
				smsMsRoleUserVO.setRoleGrpDivCd(smsMsUserVO.getRoleGrpDivCd1());
				smsMsRoleUserDAO.insertSmsMsRoleUser(smsMsRoleUserVO);
			}
		}
		if(smsMsUserVO.getRoleGrpDivCd2()!=null){
			if("".equals(smsMsUserVO.getRoleGrpDivCd2())==false){
				smsMsRoleUserVO.setRoleGrpDivCd(smsMsUserVO.getRoleGrpDivCd2());
				smsMsRoleUserDAO.insertSmsMsRoleUser(smsMsRoleUserVO);
			}
		}
		if(smsMsUserVO.getRoleGrpDivCd3()!=null){
			if("".equals(smsMsUserVO.getRoleGrpDivCd3())==false){
				smsMsRoleUserVO.setRoleGrpDivCd(smsMsUserVO.getRoleGrpDivCd3());
				smsMsRoleUserDAO.insertSmsMsRoleUser(smsMsRoleUserVO);
			}
		}
		if(smsMsUserVO.getRoleGrpDivCd4()!=null){
			if("".equals(smsMsUserVO.getRoleGrpDivCd4())==false){
				smsMsRoleUserVO.setRoleGrpDivCd(smsMsUserVO.getRoleGrpDivCd4());
				smsMsRoleUserDAO.insertSmsMsRoleUser(smsMsRoleUserVO);
			}
		}

	}

	@Override
	public void insertSmsMsUserNSmsMsRoleUser(SmsMsUserVO smsMsUserVO)
			throws Exception {
		// TODO Auto-generated method stub
		smsMsUserDAO.insertSmsMsUser(smsMsUserVO);
		SmsMsRoleUserVO smsMsRoleUserVO = new SmsMsRoleUserVO();
		smsMsRoleUserVO.setUserEml(smsMsUserVO.getUserEml());

		if(smsMsUserVO.getRoleGrpDivCd1()!=null){
			if("".equals(smsMsUserVO.getRoleGrpDivCd1())==false){
				smsMsRoleUserVO.setRoleGrpDivCd(smsMsUserVO.getRoleGrpDivCd1());
				smsMsRoleUserDAO.insertSmsMsRoleUser(smsMsRoleUserVO);
			}
		}
		if(smsMsUserVO.getRoleGrpDivCd2()!=null){
			if("".equals(smsMsUserVO.getRoleGrpDivCd2())==false){
				smsMsRoleUserVO.setRoleGrpDivCd(smsMsUserVO.getRoleGrpDivCd2());
				smsMsRoleUserDAO.insertSmsMsRoleUser(smsMsRoleUserVO);
			}
		}
		if(smsMsUserVO.getRoleGrpDivCd3()!=null){
			if("".equals(smsMsUserVO.getRoleGrpDivCd3())==false){
				smsMsRoleUserVO.setRoleGrpDivCd(smsMsUserVO.getRoleGrpDivCd3());
				smsMsRoleUserDAO.insertSmsMsRoleUser(smsMsRoleUserVO);
			}
		}
		if(smsMsUserVO.getRoleGrpDivCd4()!=null){
			if("".equals(smsMsUserVO.getRoleGrpDivCd4())==false){
				smsMsRoleUserVO.setRoleGrpDivCd(smsMsUserVO.getRoleGrpDivCd4());
				smsMsRoleUserDAO.insertSmsMsRoleUser(smsMsRoleUserVO);
			}
		}
	}

	@Override
	public List<SmsMsUserVO> selectSmsMsUserByOrdNo(String ordNo)	throws Exception {
		return smsMsUserDAO.selectSmsMsUserbyOrdNo(ordNo);
	}
	
	

}
