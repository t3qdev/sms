package com.b5m.sms.vo;

public class SmsMsRoleUserVO {

	//이메일
	String userEml;
	
	//권한코드
	String RoleGrpDivCd;

	public String getUserEml() {
		return userEml;
	}

	public void setUserEml(String userEml) {
		this.userEml = userEml;
	}

	public String getRoleGrpDivCd() {
		return RoleGrpDivCd;
	}

	public void setRoleGrpDivCd(String roleGrpDivCd) {
		RoleGrpDivCd = roleGrpDivCd;
	}

	@Override
	public String toString() {
		return "SmsMsRoleUserVO [userEml=" + userEml + ", RoleGrpDivCd="
				+ RoleGrpDivCd + "]";
	}
	
	
}
