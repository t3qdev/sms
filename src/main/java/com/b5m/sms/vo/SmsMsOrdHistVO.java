package com.b5m.sms.vo;

public class SmsMsOrdHistVO {

	//SMS_MS_ORD_HIST
	// 주문 번호
	String ordNo;
	
	//주문이력일련번호
	String ordHistSeq; 
	
	//주문상태코드
	String ordStatCd;
	
	//주문이력작성자이메일
	String ordHistWrtrEml;
	
	//주문이력등록일시
	String ordHistRegDttm;
	
	//주문이력내용
	String ordHistHistCont;

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getOrdHistSeq() {
		return ordHistSeq;
	}

	public void setOrdHistSeq(String ordHistSeq) {
		this.ordHistSeq = ordHistSeq;
	}

	public String getOrdStatCd() {
		return ordStatCd;
	}

	public void setOrdStatCd(String ordStatCd) {
		this.ordStatCd = ordStatCd;
	}

	public String getOrdHistWrtrEml() {
		return ordHistWrtrEml;
	}

	public void setOrdHistWrtrEml(String ordHistWrtrEml) {
		this.ordHistWrtrEml = ordHistWrtrEml;
	}

	public String getOrdHistRegDttm() {
		return ordHistRegDttm;
	}

	public void setOrdHistRegDttm(String ordHistRegDttm) {
		this.ordHistRegDttm = ordHistRegDttm;
	}

	public String getOrdHistHistCont() {
		return ordHistHistCont;
	}

	public void setOrdHistHistCont(String ordHistHistCont) {
		this.ordHistHistCont = ordHistHistCont;
	}

	@Override
	public String toString() {
		return "SmsMsOrdHistVO [ordNo=" + ordNo + ", ordHistSeq=" + ordHistSeq
				+ ", ordStatCd=" + ordStatCd + ", ordHistWrtrEml="
				+ ordHistWrtrEml + ", ordHistRegDttm=" + ordHistRegDttm
				+ ", ordHistHistCont=" + ordHistHistCont + "]";
	}
	
	
	
	
}
