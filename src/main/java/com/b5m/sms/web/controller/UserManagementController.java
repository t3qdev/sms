package com.b5m.sms.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;










import org.springframework.web.bind.annotation.ResponseBody;

import com.b5m.sms.biz.service.UserService;
import com.b5m.sms.vo.SmsMsRoleUserVO;
import com.b5m.sms.vo.SmsMsUserVO;

@Controller
public class UserManagementController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementController.class);
		
	@Autowired
	private PasswordEncoder bcrypt;
	
	@Resource(name = "userService")
	public UserService userService;

	@RequestMapping(value="/userManagementView")
	public String userManagement(HttpSession session, Model model){
		String result = "userManagement";
		String result2 = "userManagement";
		//충돌테스트를 위한 내용들
		
		return result2;
	}
	
	@ResponseBody
	@RequestMapping("/userManagementLoad.ajax")
	public List<SmsMsUserVO> userManagementLoad(HttpSession session, Model model){
		// SMS_MS_USER와 SMS_MS_ROLE_USER 은 서로 조인 관계로 이어져 있습니다.
		// 하지만 userManagement.jsp 에서는 1개 아이디당 4개의 role로 규정되어 있습니다.(추후 바뀔수도 있음)
		// 따라서 SMS_MS_USER와 SMS_MS_ROLE_USER를 JOIN 하면 중복된 row 가 발생하게 됩니다.
		// 따라서, SmsMsUserVO  자체를 아예 SMS_MS_USER + SMS_MS_ROLE_USER + SMS_MS_ORD_USER 를 합친 VO 로 만들어 버렸습니다.
		// 아래 코드는, selectSmsMsUser 와 selectSmsMsRoleUser를 해서 수동으로 JOIN(?) 작업을 하였습니다.
		
		List<SmsMsUserVO> smsMsUserVOList = null;
		SmsMsUserVO smsMsUserVO = new SmsMsUserVO();
		try {
			smsMsUserVOList = userService.selectSmsMsUser(smsMsUserVO);     // selectSmsMsUser에 null VO를 넣어서 모든 user 나올수 있게 한다.
			for(int i=0; i<smsMsUserVOList.size();i++){
				SmsMsRoleUserVO smsMsRoleUserVO = new SmsMsRoleUserVO();
				smsMsRoleUserVO.setUserEml(smsMsUserVOList.get(i).getUserEml());   
				List<SmsMsRoleUserVO> smsMsRoleUserVOList = null;
				smsMsRoleUserVOList = userService.selectSmsMsRoleUser(smsMsRoleUserVO);  // 한 list 의 유저의 모든 role 을 가져온다.
				if(smsMsRoleUserVOList!=null){		//role 갯수만큼 VO 에 넣어준다.
					if(smsMsRoleUserVOList.size()==1){
						smsMsUserVOList.get(i).setRoleGrpDivCd1(smsMsRoleUserVOList.get(0).getRoleGrpDivCd());
					}else if(smsMsRoleUserVOList.size()==2){
						smsMsUserVOList.get(i).setRoleGrpDivCd1(smsMsRoleUserVOList.get(0).getRoleGrpDivCd());
						smsMsUserVOList.get(i).setRoleGrpDivCd2(smsMsRoleUserVOList.get(1).getRoleGrpDivCd());
					}else if(smsMsRoleUserVOList.size()==3){
						smsMsUserVOList.get(i).setRoleGrpDivCd1(smsMsRoleUserVOList.get(0).getRoleGrpDivCd());
						smsMsUserVOList.get(i).setRoleGrpDivCd2(smsMsRoleUserVOList.get(1).getRoleGrpDivCd());
						smsMsUserVOList.get(i).setRoleGrpDivCd3(smsMsRoleUserVOList.get(2).getRoleGrpDivCd());
					}else if(smsMsRoleUserVOList.size()==4){
						smsMsUserVOList.get(i).setRoleGrpDivCd1(smsMsRoleUserVOList.get(0).getRoleGrpDivCd());
						smsMsUserVOList.get(i).setRoleGrpDivCd2(smsMsRoleUserVOList.get(1).getRoleGrpDivCd());
						smsMsUserVOList.get(i).setRoleGrpDivCd3(smsMsRoleUserVOList.get(2).getRoleGrpDivCd());
						smsMsUserVOList.get(i).setRoleGrpDivCd4(smsMsRoleUserVOList.get(3).getRoleGrpDivCd());
					}else{
						
					}
					smsMsUserVOList.get(i).setUserPwd("**********");         // 패스워드는 암호화 되어 있다. 하지만 jsp 로 보내주지 않는다.
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smsMsUserVOList;
	}
	
	@ResponseBody
	@RequestMapping("/userManagementSave.ajax")
	public String userManagementSave(HttpSession session, Model model, SmsMsUserVO smsMsUserVO) throws Exception {
		// 화면상에서는 [저장]만 누르기 때문에, 이 값이 (가입)인지 (수정)인지 구분할 필요가 있다.
		// 화면상에서 로직은, 체크한 Row들 마다 따로 ajax 호출을 받아 오기 때문에, 본 controller 에서는 1개의 SmsMsUserVO가 들어온다.
		// 	1.	해당 vo의 email을 SMS_MS_USER 테이블에서 열람을 하고, 있는 id 인지 없는 id 인지 확인한다.                 smsMsUserDAO.selectSmsMsUser                                 
		//		 if : 해당 email이 DB에 존재한다?
		//          if : 비밀번호가 수정 되었는가? 
		//             USER_PWD_STAT_CD 를 초기화 상태로 바꿔준다												     smsMsUserDAO.updateSmsMsUser 
		//			(수정) 에 해당하므로, SMS_MS_USER 에 update 하고, SMS_MS_ROLE_USER 은, 모두 지우고 다시 집어 넣는다.
		//       else if : 해당 email이 db에 존재 하지 않는가?
		//          (회원가입)에 해당한다. SMS_MS_USER에 넣고  USER_PWD_STAT_CD 를 초기화 상태로 바꿔준다  		 smsMsUserDAO.insertSmsMsUser 
		//                                 , SMS_MS_ROLE_USER 에 권한 갯수만큼 넣어준다.									smsMsRoleUserDAO .insertSmsMsRoleUser


		// service로 넣어줄 변수 vo 선언
		SmsMsUserVO tempMsUserByUserEmlVO = new SmsMsUserVO();
		tempMsUserByUserEmlVO.setUserEml(smsMsUserVO.getUserEml());
		
		// 	1.	해당 vo의 email을 SMS_MS_USER 테이블에서 열람을 하고, 있는 id 인지 없는 id 인지 확인한다.                 smsMsUserDAO.selectSmsMsUser 
		List<SmsMsUserVO> selectSmsMsUserVOList = null;						
		selectSmsMsUserVOList = userService.selectSmsMsUser(tempMsUserByUserEmlVO);     
		
		
		tempMsUserByUserEmlVO.setUserEml(smsMsUserVO.getUserEml());
		if(smsMsUserVO.getUserPwd()!=null){						      				
			if("".equals(smsMsUserVO.getUserPwd())==false && "**********".equals(smsMsUserVO.getUserPwd())==false){					//   if : 비밀번호가 수정 되었는가?   true -> 비밀번호도 vo에 넣어주고 update
				// 변경할 비밀번호를 암호화 해서 vo에 세팅
				tempMsUserByUserEmlVO.setUserPwd(bcrypt.encode(smsMsUserVO.getUserPwd().trim()));
				 // 화면에서 password 를 변경했으면 사용자비밀번호상태코드를 초기화(N000600100)로 세팅  -> because 이 사용자는 로그인할 때, 비밀번호 변경 페이지로 자동 이동.
				tempMsUserByUserEmlVO.setUserPwdStatCd("N000600100");	
			}
		}
		tempMsUserByUserEmlVO.setUserAlasEngNm(smsMsUserVO.getUserAlasEngNm());
		tempMsUserByUserEmlVO.setUserAlasCnsNm(smsMsUserVO.getUserAlasCnsNm());
		tempMsUserByUserEmlVO.setOgnzDivCd(smsMsUserVO.getOgnzDivCd());
		tempMsUserByUserEmlVO.setUserStatCd(smsMsUserVO.getUserStatCd());
		
		//SMS_MS_ROLE_USER 은 권한도 pk  이기 때문에, 중복 검사가 필요.
		//다음 로직을 이용하면, 중복이면 누락시키고, 중간에 비어 있으면 당겨준다. -> so 화면상 validation 불필요
		Set<String> duplicateTest = new HashSet<String>();
		if (duplicateTest.add(smsMsUserVO.getRoleGrpDivCd1()) == true) {
			tempMsUserByUserEmlVO.setRoleGrpDivCd1(smsMsUserVO.getRoleGrpDivCd1());
		}
		if (duplicateTest.add(smsMsUserVO.getRoleGrpDivCd2()) == true) {
			tempMsUserByUserEmlVO.setRoleGrpDivCd2(smsMsUserVO.getRoleGrpDivCd2());
		}
		if (duplicateTest.add(smsMsUserVO.getRoleGrpDivCd3()) == true) {
			tempMsUserByUserEmlVO.setRoleGrpDivCd3(smsMsUserVO.getRoleGrpDivCd3());
		}
		if (duplicateTest.add(smsMsUserVO.getRoleGrpDivCd4()) == true) {
			tempMsUserByUserEmlVO.setRoleGrpDivCd4(smsMsUserVO.getRoleGrpDivCd4());
		}

		
		if(selectSmsMsUserVOList != null){
			if(selectSmsMsUserVOList.size()==1){                  				  //   if : 해당 email이 DB에 존재한다?  true -> update
				// SMS_MS_USER UPDATE, SMS_MS_ROLE_USER은 DELETE 한  후에 INSERT
				userService.updateSmsMsUserNSmsMsRoleUser(tempMsUserByUserEmlVO);     
				//update
			}
			else if(selectSmsMsUserVOList.size()==0){		//       else if : 해당 email이 db에 존재 하지 않는가?  (회원가입)에 해당한다. 
				// SMS_MS_USER INSERT, SMS_MS_ROLE_USER도 바로 INSERT
				userService.insertSmsMsUserNSmsMsRoleUser(tempMsUserByUserEmlVO);
			}else{
				LOGGER.debug("DB has problems..." );
				
			}
		}
		
		return null;
	}
}
