package com.b5m.sms.common.security;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.b5m.sms.biz.service.UserService;
import com.b5m.sms.vo.SmsMsRoleUserVO;
import com.b5m.sms.vo.SmsMsUserVO;

@Service("loginService")
public class LoginService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Resource
	private UserService userService;
	  
	@Autowired
	private PasswordEncoder bcrypt;
	
	@Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {

        logger.info("username : " + username);
      
        SmsMsUserVO smsMsUserVO = new SmsMsUserVO();
        smsMsUserVO.setUserEml(username);
        
        // 회원 정보 dao 에서 데이터를 읽어 옴.
        List<SmsMsUserVO> userList;
		try {
			userList = userService.selectSmsMsUser(smsMsUserVO);          //exception 처리
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
       
        // return 할 User 객체 - > Spring security 양식
        User user = new User();
        
        if(userList.size()==0){    // 로그인 정보가 DB 에 없음.
        	 throw new UsernameNotFoundException("접속자 정보를 찾을 수 없습니다.");
        }else{
        	// 아직 DB에 pwd가 암호화 상태가 아니기 때문에, 가져와서 암호화.
            user.setUsername(username);


            String password = userList.get(0).getUserPwd();
            String userAlasEngNm = userList.get(0).getUserAlasEngNm();
            String userAlasCnsNm = userList.get(0).getUserAlasCnsNm();
            
            System.out.println("입력한 비밀번호의 암호화 된 값 : ");
//			System.out.println(bcrypt.encode(password));
			System.out.println("- 추후 삭제 필수 ");
//        	String password = passwordEncoder.encodePassword(userList.get(0).getSllrPwd(), saltSource.getSalt(user));
        	user.setPassword(password);
        	user.setUserAlasCnsNm(userAlasCnsNm);
        	user.setUserAlasEngNm(userAlasEngNm);
            List<Role> roles = new ArrayList<Role>();
            
            SmsMsRoleUserVO smsMsRoleUserVO = new SmsMsRoleUserVO();
            smsMsRoleUserVO.setUserEml(username);
            // 해당 ID 의 ROLE 을 가져와서 Roles 에 list 로 넣는다.
        	List<SmsMsRoleUserVO> smsMsRoleUserList = null;
			try {
				smsMsRoleUserList = userService.selectSmsMsRoleUser(smsMsRoleUserVO);		 //exception 처리
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
        	for(int i=0; i<smsMsRoleUserList.size();i++){
        		Role role = new Role();
        		System.out.println(smsMsRoleUserList.get(i).getRoleGrpDivCd());
        		role.setName(smsMsRoleUserList.get(i).getRoleGrpDivCd());
        		System.out.println(role);
                roles.add(role);
        	}
        	for(int i=0; i<roles.size();i++){
        		System.out.println(roles.get(i).getName());
        	}
        	 
        	// user에 role set
            user.setAuthorities(roles);
        }

        // 만약 데이터가 없을 경우 익셉션
        //if (user == null) throw new UsernameNotFoundException("접속자 정보를 찾을 수 없습니다.");

        return user;
    }
}
