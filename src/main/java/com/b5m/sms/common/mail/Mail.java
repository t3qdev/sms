package com.b5m.sms.common.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.b5m.sms.common.util.StringUtil;

@Component("mail")
public class Mail {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Mail.class);
	
	
	

	// 메일 관련 정보
//	String host = "smtp.b5m.com";
//	String senderId = "brandstore.admin@b5m.com";
//	String senderName = "Brandstroe Admin";
//	String senderEmail = "brandstore.admin@b5m.com";
//	final String senderPassword = "qkddnak2!!!";
//	int port=25;
	
	
	String host;
	String senderId ;
	String senderName ;
	String senderEmail;
	String senderPassword;
	String port;
	
	
	/**
	 * 
	 * @param recipients 코마(,) 분리자로 여러명의 이메일 주소를 입력. 
	 * @param subject 메일제목 
	 * @param content 메일내용 
	 * @param contentFormat 0은 text(한글안깨짐), 1은 "text/html"(한글깨짐)
	 * @throws Exception
	 */
	public void sendEmail(String recipients, String subject, String content, int contentFormat) throws Exception {
	        // 메일 내용
	        //String recipient = "byunghoon.park@gmail.com";
	        //String subject = "방우마이 메일서버를 이요한 발송 테스트입니다.";
	        //String body = "내용무88888800000";
	         
	        Properties props = System.getProperties();

	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port",new Integer(port).intValue());
	        props.put("mail.smtp.auth", "true");
	        //props.put("mail.smtp.ssl.enable", "true");
	        //props.put("mail.smtp.ssl.trust", host);
	        
	        
	          
	        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(senderId, senderPassword);
	            }
	        });
	        session.setDebug(true); //for debug
	        
	        
	        // 이하 받는이 주소 만들기. 
	        InternetAddress[] recipientsAddress = null;
			String[] mailAddress = StringUtil.sGetSplit(recipients, ",");
			
			if(mailAddress!=null && mailAddress.length>0){
				recipientsAddress = new InternetAddress[mailAddress.length];
			}else{
				throw new AddressException("수신인이 없습니다.");
			}
			
			for (int i = 0; i < mailAddress.length; i++) {
				try {
					recipientsAddress[i] = new InternetAddress(mailAddress[i]);
				} catch (AddressException e) {
					throw new AddressException(mailAddress[i]+"(은)는 올바르지 않은 수신인 입니다.");
				}
				System.out.println(i+"="+ mailAddress[i]);
			}
			// 이상 받는이 주소 만들기. 끝. 
			
	        Message mimeMessage = new MimeMessage(session);
	        try {
				mimeMessage.setFrom(new InternetAddress( senderEmail, MimeUtility.encodeText(senderName, "UTF-8","B")));
		        mimeMessage.setRecipients(Message.RecipientType.TO, recipientsAddress);
		       
		        
		        mimeMessage.setSubject(subject);
		        
		        if(contentFormat==1){    // content 를 html 형식으로 보내야 할 때  - 한글이 깨짐    //  수정 - 주엽 20160212
		        	mimeMessage.setContent(content,"text/html");
		        }else if(contentFormat==0){      // content를 text 형식으로 보내야 할 때   -   한글이 깨지지 않는다.
		        	mimeMessage.setText(content);
		        }
		        
		        Transport.send(mimeMessage);
			} catch (UnsupportedEncodingException e) {
				throw e;
			} catch (MessagingException e) {
				throw e;
			}
	 }


	




	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}



	public String getSenderId() {
		return senderId;
	}



	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public String getSenderEmail() {
		return senderEmail;
	}


	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}


	public String getSenderPassword() {
		return senderPassword;
	}


	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}


	@Override
	public String toString() {
		return "Mail [host=" + host + ", senderId=" + senderId + ", senderName=" + senderName + ", senderEmail=" + senderEmail + ", senderPassword=" + senderPassword + ", port=" + port + "]";
	}
	




}
