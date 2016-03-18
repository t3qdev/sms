package com.b5m.sms.vo;

public class CodeVO {
	private String cd;			
	private String cdVal;
	private String etc;
	
	
	
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getCdVal() {
		return cdVal;
	}
	public void setCdVal(String cdVal) {
		this.cdVal = cdVal;
	}
	@Override
	public String toString() {
		return "CodeVO [cd=" + cd + ", cdVal=" + cdVal + ", etc=" + etc + "]";
	}		
	
	
}
