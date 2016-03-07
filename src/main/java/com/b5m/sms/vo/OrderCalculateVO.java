package com.b5m.sms.vo;

import java.math.BigDecimal;

public class OrderCalculateVO {
	private String ordNo;				//주문번호
	private String bactPrvdDt;		//정산지급일
	private BigDecimal bactPrvdAmt;		//정산지급액
	private String bactPrvdMemoCont;		//정산지급메모
	private String bactRegrEml;		//정산지급자이메일
	private String bactRegDt;			//정산 등록일 (사용안됨)
	
	
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public String getBactPrvdDt() {
		return bactPrvdDt;
	}
	public void setBactPrvdDt(String bactPrvdDt) {
		this.bactPrvdDt = bactPrvdDt;
	}
	
	public BigDecimal getBactPrvdAmt() {
		return bactPrvdAmt;
	}
	public void setBactPrvdAmt(BigDecimal bactPrvdAmt) {
		this.bactPrvdAmt = bactPrvdAmt;
	}
	public String getBactPrvdMemoCont() {
		return bactPrvdMemoCont;
	}
	public void setBactPrvdMemoCont(String bactPrvdMemoCont) {
		this.bactPrvdMemoCont = bactPrvdMemoCont;
	}
	public String getBactRegrEml() {
		return bactRegrEml;
	}
	public void setBactRegrEml(String bactRegrEml) {
		this.bactRegrEml = bactRegrEml;
	}
	public String getBactRegDt() {
		return bactRegDt;
	}
	public void setBactRegDt(String bactRegDt) {
		this.bactRegDt = bactRegDt;
	}
	@Override
	public String toString() {
		return "OrderCalculateVO [ordNo=" + ordNo + ", bactPrvdDt="
				+ bactPrvdDt + ", bactPrvdAmt=" + bactPrvdAmt
				+ ", bactPrvdMemoCont=" + bactPrvdMemoCont + ", bactRegrEml="
				+ bactRegrEml + ", bactRegDt=" + bactRegDt + "]";
	}

	
	
	
	
}
