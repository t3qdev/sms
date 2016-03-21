package com.b5m.sms.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;








import com.b5m.sms.biz.service.UserService;
import com.b5m.sms.common.security.User;
import com.b5m.sms.vo.SmsMsUserVO;

@Controller
public class LogInController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogInController.class);
		
	
	@Resource(name = "userService")
	public UserService userService;
	
	/**
	 * 로그인페이지 출력
	 * 
	 * @param session
	 * @return String
	 * @throws Exception 
	 */
	@RequestMapping(value="/login")
	public String login(	HttpSession session) throws Exception{
		String result = "login";
		
		//세션 관리 - login.do 로 들어올때 로그인된 세션이 있다면, 로그인이 아니라, orderManagement.do 로 보내준다.
		//하지만, 로그인 된 아이디의 사용자상태코드가 휴직(N000610200) or 퇴직(N000610300)인 경우 로그인 불가
		//하지만, 로그인 된 아이디의 사용자비밀번호상태코드가 초기화(N000600100) 이면, 비밀번호 변경페이지 (passwordChange.do) 로 redirect 한다.
		if(session.getAttribute("SPRING_SECURITY_CONTEXT")!= null){
			LOGGER.info("SPRING_SECURITY_CONTEXT in NOT NULL !!!");
			
			// spring security 에 따라, 로그인한 정보 가져옴
		    User usr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    String userEml = usr.getUsername();
		    if(userEml == null) return "redirect:/login.do";
		    
		    //세션에서 아이디 가져와서, 다시 서비스에서 로그인된 사용자의 비밀번호 상태코드와 사용자 상태코드를 가져와 비교한다.
		    SmsMsUserVO smsMsUserVO = new SmsMsUserVO();
		    smsMsUserVO.setUserEml(userEml);
		    List<SmsMsUserVO> smsMsUserVOList= userService.selectSmsMsUser(smsMsUserVO);
		    for(int i=0; i<smsMsUserVOList.size(); i++){
		    	if(("N000610200").equals(smsMsUserVOList.get(i).getUserStatCd())){   // 사용자가 휴직중인가?
		    		return "redirect:/logOut.do";
		    	}
		    	if(("N000610300").equals(smsMsUserVOList.get(i).getUserStatCd())){    // 사용자가 퇴직중인가?
		    		return "redirect:/logOut.do";
		    	}
		    	if(("N000600100").equals(smsMsUserVOList.get(i).getUserPwdStatCd())){   // 사용자 비밀번호가 초기화된 상태라서 비밀번호를 변경해주어야 하는가?
		    		return "redirect:/passwordChangeView.do";
		    	}
		    }
			result = "redirect:/orderManagementView.do";
		}
		
		return result;
	}
	
	/**
	 * Spring Security Intercepter 에 의해 가로채지기 때문에 사실상 호출되진 않음, 형식유지를 위한 Method.
	 * @param paramMap
	 * @param map
	 */
	@RequestMapping(value="/loginCheck", method=RequestMethod.POST)
	public void loginCheck(@RequestParam Map<String, Object> paramMap, ModelMap map){

	}
	/**
	 *  * Spring Security Intercepter 에 의해 가로채지기 때문에 사실상 호출되진 않음, 형식유지를 위한 Method.
	 * @param session
	 */
	@RequestMapping(value="/logOut")
	public void logOut(@RequestParam Map<String, Object> paramMap, ModelMap map){
		
	}
	
}
