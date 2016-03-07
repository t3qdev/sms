package com.b5m.sms.vo;

public class PasswordChangeVO {

	private String userEml;
	private String userPwd;
	private String userPwd_old;
	private String usrAlas;
	
	public String getUserEml() {
		return userEml;
	}
	public void setUserEml(String userEml) {
		this.userEml = userEml;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserPwd_old() {
		return userPwd_old;
	}
	public void setUserPwd_old(String userPwd_old) {
		this.userPwd_old = userPwd_old;
	}
	public String getUsrAlas() {
		return usrAlas;
	}
	public void setUsrAlas(String usrAlas) {
		this.usrAlas = usrAlas;
	}
	@Override
	public String toString() {
		return "PasswordChangeVO [userEml=" + userEml + ", userPwd=" + userPwd
				+ ", userPwd_old=" + userPwd_old + ", usrAlas=" + usrAlas + "]";
	}
	
	
	
}
