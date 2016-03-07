package com.b5m.sms.vo;

public class SmsMsEstmVO {
	private String ordNo;		//주문번호
	private String poNo;		//po번호
	private String dlvModeCd;		//배송방식코드 (견적조건)
	private String dlvDestCd;		//배송목적지코드 : 견적서에 존재X
	private String dlvAmt;		//배송금액 (물류비)
	
	private String poDt;		//po일자
	private String poRegrEml;		//po등록자이메일
	private String poSumAmt;		//po총액
	private String stdXchrKindCd;		//기준환율종류코드
	private String stdXchrAmt;		//기준환율금액
	
	private String ordArvlDt;		//주문도착일자
	private String poMemoCont;	//po메모

	
	
	public String getPoMemoCont() {
		return poMemoCont;
	}

	public void setPoMemoCont(String poMemoCont) {
		this.poMemoCont = poMemoCont;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getDlvModeCd() {
		return dlvModeCd;
	}

	public void setDlvModeCd(String dlvModeCd) {
		this.dlvModeCd = dlvModeCd;
	}

	public String getDlvDestCd() {
		return dlvDestCd;
	}

	public void setDlvDestCd(String dlvDestCd) {
		this.dlvDestCd = dlvDestCd;
	}

	public String getDlvAmt() {
		return dlvAmt;
	}

	public void setDlvAmt(String dlvAmt) {
		this.dlvAmt = dlvAmt;
	}

	public String getPoDt() {
		return poDt;
	}

	public void setPoDt(String poDt) {
		this.poDt = poDt;
	}

	public String getPoRegrEml() {
		return poRegrEml;
	}

	public void setPoRegrEml(String poRegrEml) {
		this.poRegrEml = poRegrEml;
	}

	public String getPoSumAmt() {
		return poSumAmt;
	}

	public void setPoSumAmt(String poSumAmt) {
		this.poSumAmt = poSumAmt;
	}

	public String getStdXchrKindCd() {
		return stdXchrKindCd;
	}

	public void setStdXchrKindCd(String stdXchrKindCd) {
		this.stdXchrKindCd = stdXchrKindCd;
	}

	public String getStdXchrAmt() {
		return stdXchrAmt;
	}

	public void setStdXchrAmt(String stdXchrAmt) {
		this.stdXchrAmt = stdXchrAmt;
	}

	public String getOrdArvlDt() {
		return ordArvlDt;
	}

	public void setOrdArvlDt(String ordArvlDt) {
		this.ordArvlDt = ordArvlDt;
	}

	@Override
	public String toString() {
		return "SmsMsEstmVO [ordNo=" + ordNo + ", poNo=" + poNo
				+ ", dlvModeCd=" + dlvModeCd + ", dlvDestCd=" + dlvDestCd
				+ ", dlvAmt=" + dlvAmt + ", poDt=" + poDt + ", poRegrEml="
				+ poRegrEml + ", poSumAmt=" + poSumAmt + ", stdXchrKindCd="
				+ stdXchrKindCd + ", stdXchrAmt=" + stdXchrAmt + ", ordArvlDt="
				+ ordArvlDt + ", poMemoCont=" + poMemoCont + "]";
	}

	
	
	
	
}
