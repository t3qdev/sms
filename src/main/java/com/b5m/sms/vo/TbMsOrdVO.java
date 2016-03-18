package com.b5m.sms.vo;

public class TbMsOrdVO {//주문번호
	private String ordId;
	private String ordCustNm;
	private String ordCustCpNo;
	private String ordCustEml;
	private String dlvModeCd;

	private String sysReqDttm;
	private String b5cOrdNo;
	
	private String custId;

	private String ordEstmDt;
	private String stdXchrKindCd;
	private String stdXchrAmt;
	
	private String ordSumAmt;
	private String ordHopeArvlDt;

	private String rmbXchrAmt;

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getOrdCustNm() {
		return ordCustNm;
	}

	public void setOrdCustNm(String ordCustNm) {
		this.ordCustNm = ordCustNm;
	}

	public String getOrdCustCpNo() {
		return ordCustCpNo;
	}

	public void setOrdCustCpNo(String ordCustCpNo) {
		this.ordCustCpNo = ordCustCpNo;
	}

	public String getOrdCustEml() {
		return ordCustEml;
	}

	public void setOrdCustEml(String ordCustEml) {
		this.ordCustEml = ordCustEml;
	}

	public String getDlvModeCd() {
		return dlvModeCd;
	}

	public void setDlvModeCd(String dlvModeCd) {
		this.dlvModeCd = dlvModeCd;
	}

	public String getSysReqDttm() {
		return sysReqDttm;
	}

	public void setSysReqDttm(String sysReqDttm) {
		this.sysReqDttm = sysReqDttm;
	}

	public String getB5cOrdNo() {
		return b5cOrdNo;
	}

	public void setB5cOrdNo(String b5cOrdNo) {
		this.b5cOrdNo = b5cOrdNo;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOrdEstmDt() {
		return ordEstmDt;
	}

	public void setOrdEstmDt(String ordEstmDt) {
		this.ordEstmDt = ordEstmDt;
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

	public String getOrdSumAmt() {
		return ordSumAmt;
	}

	public void setOrdSumAmt(String ordSumAmt) {
		this.ordSumAmt = ordSumAmt;
	}

	public String getOrdHopeArvlDt() {
		return ordHopeArvlDt;
	}

	public void setOrdHopeArvlDt(String ordHopeArvlDt) {
		this.ordHopeArvlDt = ordHopeArvlDt;
	}

	public String getRmbXchrAmt() {
		return rmbXchrAmt;
	}

	public void setRmbXchrAmt(String rmbXchrAmt) {
		this.rmbXchrAmt = rmbXchrAmt;
	}

	@Override
	public String toString() {
		return "TbMsOrdVO [ordId=" + ordId + ", ordCustNm=" + ordCustNm
				+ ", ordCustCpNo=" + ordCustCpNo + ", ordCustEml=" + ordCustEml
				+ ", dlvModeCd=" + dlvModeCd + ", sysReqDttm=" + sysReqDttm
				+ ", b5cOrdNo=" + b5cOrdNo + ", custId=" + custId
				+ ", ordEstmDt=" + ordEstmDt + ", stdXchrKindCd="
				+ stdXchrKindCd + ", stdXchrAmt=" + stdXchrAmt + ", ordSumAmt="
				+ ordSumAmt + ", ordHopeArvlDt=" + ordHopeArvlDt
				+ ", rmbXchrAmt=" + rmbXchrAmt + "]";
	}
	
	
}
