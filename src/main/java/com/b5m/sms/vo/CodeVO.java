package com.b5m.sms.vo;

public class CodeVO {
	private String cd;			
	private String cdVal;
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
		return "CodeVO [cd=" + cd + ", cdVal=" + cdVal + "]";
	}		
	
	
}
