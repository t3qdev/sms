package com.b5m.sms.common.security;

import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Resource(name="loginService")
    LoginService loginService;

    @Resource(name="bcrypt")
    private PasswordEncoder bcrypt;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        User user;
        Collection<? extends GrantedAuthority> authorities;

        try {
            user = loginService.loadUserByUsername(username);
            String hashedPassword = bcrypt.encode(password);
//            logger.info("username : " + username + " / password : " + password + " / hash password : " + hashedPassword);
//            logger.info("username : " + user.getUsername() + " / password : " + user.getPassword());
            //if (!hashedPassword.matches(user.getPassword())) throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
            if(!bcrypt.matches(password, user.getPassword())) throw new BadCredentialsException("密码不一致");
            authorities = user.getAuthorities();
        } catch(UsernameNotFoundException e) {
            logger.info(e.toString());
            throw new UsernameNotFoundException(e.getMessage());
        }

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}
