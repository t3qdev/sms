package com.b5m.sms.web.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b5m.sms.biz.service.UserService;
import com.b5m.sms.common.security.User;
import com.b5m.sms.common.util.StringUtil;
import com.b5m.sms.vo.PasswordChangeVO;
import com.b5m.sms.vo.SmsMsUserVO;

@Controller
public class PasswordChangeController {
	
	@Resource(name= "userService")
	private UserService userService;
	
	@Autowired
	private PasswordEncoder bcrypt;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordChangeController.class);
		
	@RequestMapping(value="/passwordChangeView")
	public String passwordChange(Model model) throws Exception{
		
		// spring security 에 따라, 로그인한 정보 가져옴
		User usr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userEml = usr.getUsername();	// 유저 아이디
		
		//1. 유저정보를 가져온다
		SmsMsUserVO paramUserVo= new SmsMsUserVO();
		paramUserVo.setUserEml(userEml);
		List<SmsMsUserVO> userList = userService.selectSmsMsUser(paramUserVo);	
	
		//2.중문화명(영문화명) 생성
		String userAlas =userList.get(0).getUserAlasCnsNm()+"("+userList.get(0).getUserAlasEngNm()+")";
		if(StringUtil.isNullOrEmpty(userList.get(0).getUserAlasCnsNm())&&StringUtil.isNullOrEmpty(userList.get(0).getUserAlasEngNm())){
			userAlas="";
		}
		//3. model에 필요 정보를 담는다.
		model.addAttribute("userEml",	userEml);
		model.addAttribute("userAlas",userAlas);

		
		return "passwordChange";
		
	}
	

	@RequestMapping(value="/passwordChangeUpdate")
	public String passwordChangeUpdate(PasswordChangeVO passwordChangeVo, Model model) throws Exception{
		

		//1.비밀번호체크를 위해 해당 유저아이디의 정보를 받아온다.
		SmsMsUserVO paramUserVo = new SmsMsUserVO();
		paramUserVo.setUserEml(passwordChangeVo.getUserEml());
		List<SmsMsUserVO> userList = userService.selectSmsMsUser(paramUserVo);	//userList.get(0)		<<-select 함수의 return 형태는 List이므로
		
		//2.두 비밀번호가 다른경우		
		if(!bcrypt.matches(passwordChangeVo.getUserPwd_old(),userList.get(0).getUserPwd())){		//brcypt.matches(원본형태,암호화된형태)
			model.addAttribute("userEml",	passwordChangeVo.getUserEml());
			model.addAttribute("userAlas",passwordChangeVo.getUsrAlas());
			return "passwordChange";
		}
		
		//3.두 비밀번호가 같은경우 
		paramUserVo.setUserPwdStatCd("N000600200");				//비밀번호상태 일반(N000600200)으로 변경
		paramUserVo.setUserPwd(bcrypt.encode(passwordChangeVo.getUserPwd().trim()));		//새로운 비밀번호로 변경		
		
		userService.updateSmsMsUser(paramUserVo);		
		return "userManagement";
		
	}
	
}
