package com.b5m.sms.vo;

public class SmsMsUserVO {

	//SMS_MS_USER 테이블정보
	//이메일
	String userEml;
	
	//비밀번호
	String userPwd;
	
	//비밀번호 초기화 상태
	String userPwdStatCd;
	
	//화명(영문)
	String userAlasEngNm;
	
	//화명(중문)
	String userAlasCnsNm;
	
	//소속
	String ognzDivCd;

	//사용자 상태
	String userStatCd;
	
	
	//SMS_MS_ROLE_USER 테이블 정보
	//권한 1
	String roleGrpDivCd1;
	
	//권한 2
	String roleGrpDivCd2;
	
	//권한 3
	String roleGrpDivCd3;
	
	//권한 4
	String roleGrpDivCd4;
	
	
	//SMS_MS_ORD_USER 테이블 정보
	//주문 번호
	String ordNo;
	


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

	public String getUserPwdStatCd() {
		return userPwdStatCd;
	}

	public void setUserPwdStatCd(String userPwdStatCd) {
		this.userPwdStatCd = userPwdStatCd;
	}

	public String getUserAlasEngNm() {
		return userAlasEngNm;
	}

	public void setUserAlasEngNm(String userAlasEngNm) {
		this.userAlasEngNm = userAlasEngNm;
	}

	public String getUserAlasCnsNm() {
		return userAlasCnsNm;
	}

	public void setUserAlasCnsNm(String userAlasCnsNm) {
		this.userAlasCnsNm = userAlasCnsNm;
	}

	public String getOgnzDivCd() {
		return ognzDivCd;
	}

	public void setOgnzDivCd(String ognzDivCd) {
		this.ognzDivCd = ognzDivCd;
	}

	public String getUserStatCd() {
		return userStatCd;
	}

	public void setUserStatCd(String userStatCd) {
		this.userStatCd = userStatCd;
	}

	public String getRoleGrpDivCd1() {
		return roleGrpDivCd1;
	}

	public void setRoleGrpDivCd1(String roleGrpDivCd1) {
		this.roleGrpDivCd1 = roleGrpDivCd1;
	}

	public String getRoleGrpDivCd2() {
		return roleGrpDivCd2;
	}

	public void setRoleGrpDivCd2(String roleGrpDivCd2) {
		this.roleGrpDivCd2 = roleGrpDivCd2;
	}

	public String getRoleGrpDivCd3() {
		return roleGrpDivCd3;
	}

	public void setRoleGrpDivCd3(String roleGrpDivCd3) {
		this.roleGrpDivCd3 = roleGrpDivCd3;
	}

	public String getRoleGrpDivCd4() {
		return roleGrpDivCd4;
	}

	public void setRoleGrpDivCd4(String roleGrpDivCd4) {
		this.roleGrpDivCd4 = roleGrpDivCd4;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	@Override
	public String toString() {
		return "SmsMsUserVO [userEml=" + userEml + ", userPwd=" + userPwd
				+ ", userPwdStatCd=" + userPwdStatCd + ", userAlasEngNm="
				+ userAlasEngNm + ", userAlasCnsNm=" + userAlasCnsNm
				+ ", ognzDivCd=" + ognzDivCd + ", userStatCd=" + userStatCd
				+ ", roleGrpDivCd1=" + roleGrpDivCd1 + ", roleGrpDivCd2="
				+ roleGrpDivCd2 + ", roleGrpDivCd3=" + roleGrpDivCd3
				+ ", roleGrpDivCd4=" + roleGrpDivCd4 + ", ordNo=" + ordNo + "]";
	}

	

	
	
}
