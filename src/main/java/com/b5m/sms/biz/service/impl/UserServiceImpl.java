package com.b5m.sms.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.b5m.sms.biz.dao.SmsMsRoleUserDAO;
import com.b5m.sms.biz.dao.SmsMsUserDAO;
import com.b5m.sms.biz.service.UserService;
import com.b5m.sms.vo.PasswordChangeVO;
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

	
	@Autowired
	private PasswordEncoder bcrypt;
	
	@Override
	public String passwordChangeUpdate(PasswordChangeVO passwordChangeVo) throws Exception {
		//1.비밀번호체크를 위해 해당 유저아이디의 정보를 받아온다.
		SmsMsUserVO paramUserVo = new SmsMsUserVO();
		paramUserVo.setUserEml(passwordChangeVo.getUserEml());
		List<SmsMsUserVO> userList = smsMsUserDAO.selectSmsMsUser(paramUserVo);	//userList.get(0)		<<-select 함수의 return 형태는 List이므로
		
		//2.두 비밀번호가 다른경우		
		if(!bcrypt.matches(passwordChangeVo.getUserPwd_old(),userList.get(0).getUserPwd())){		//brcypt.matches(원본형태,암호화된형태)

			return "false";
		}
		
		//3.두 비밀번호가 같은경우 
		userList.get(0).setUserPwdStatCd("N000600200");		
		userList.get(0).setUserPwd(bcrypt.encode(passwordChangeVo.getUserPwd().trim()));
		paramUserVo.setUserPwdStatCd("N000600200");				//비밀번호상태 일반(N000600200)으로 변경
		paramUserVo.setUserPwd(bcrypt.encode(passwordChangeVo.getUserPwd().trim()));		//새로운 비밀번호로 변경		
		System.out.println(paramUserVo.toString());
		smsMsUserDAO.updateSmsMsUser(userList.get(0));		
		return "success";   //저장성공
		
	}
	
	

}
