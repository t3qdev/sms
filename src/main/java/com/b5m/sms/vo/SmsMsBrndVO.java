package com.b5m.sms.vo;

public class SmsMsBrndVO {
	private String brndId;				// 브랜드 아이디 = sllrId
	private String brndEngNm;		// 브랜드 영문명
	private String brndCnsNm;		// 브랜드 중문명
	
	public String getBrndId() {
		return brndId;
	}
	public void setBrndId(String brndId) {
		this.brndId = brndId;
	}
	public String getBrndEngNm() {
		return brndEngNm;
	}
	public void setBrndEngNm(String brndEngNm) {
		this.brndEngNm = brndEngNm;
	}
	public String getBrndCnsNm() {
		return brndCnsNm;
	}
	public void setBrndCnsNm(String brndCnsNm) {
		this.brndCnsNm = brndCnsNm;
	}
	@Override
	public String toString() {
		return "SmsMsBrndVO [brndId=" + brndId + ", brndEngNm=" + brndEngNm
				+ ", brndCnsNm=" + brndCnsNm + "]";
	}
	
	
}
