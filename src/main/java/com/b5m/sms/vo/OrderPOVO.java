package com.b5m.sms.vo;

public class OrderPOVO {
	private String poAmt;		//-po총금액
	private String poXchrAmt;		//po총금액*환율
	private String pcSum;		//매입합계	(PurChase)
	private String pcSumNoVat;	//매입합계 부가세 제외
	private String dlvPcSum;		//물류비+매입합계
	
	private String dlvPcSumNoVat;	//물류비+매입합계 부가세 제외
	private String dlvAmt;		//-물류비
	private String pf;			//수익(물류비제외+vat포함) profit
	private String pfNoVat;		//수익(물류비제외+vat제외)
	private String pfDlvAmt;	//수익(물류비포함+vat포함)
	
	private String pfDlvAmtNoVat;		//수익(물류비포함+vat제외)
	private String poMemoCont;			//-비고
	private String ordNo;		//-주문번호
	private String poNo;		//-po번호
	private String custId;		//클라이언트
	
	private String stdXchrAmt;		//-기준환율
	private String stdXchrKindCd;	//-견적화폐
	private String dlvModeCd;		//-견적조건
	private String poDt;		//-po일자
	private String ordArvlDt;		//-상품인도일자(주문도착일자)
	
	private String poRegrEml;		//-PO등록자 이메일
	private String dlvDestCd;	//존재하지않는데 Estm에존재 , 배송목적지코드
	public String getPoAmt() {
		return poAmt;
	}
	public void setPoAmt(String poAmt) {
		this.poAmt = poAmt;
	}
	public String getPoXchrAmt() {
		return poXchrAmt;
	}
	public void setPoXchrAmt(String poXchrAmt) {
		this.poXchrAmt = poXchrAmt;
	}
	public String getPcSum() {
		return pcSum;
	}
	public void setPcSum(String pcSum) {
		this.pcSum = pcSum;
	}
	public String getPcSumNoVat() {
		return pcSumNoVat;
	}
	public void setPcSumNoVat(String pcSumNoVat) {
		this.pcSumNoVat = pcSumNoVat;
	}
	public String getDlvPcSum() {
		return dlvPcSum;
	}
	public void setDlvPcSum(String dlvPcSum) {
		this.dlvPcSum = dlvPcSum;
	}
	public String getDlvPcSumNoVat() {
		return dlvPcSumNoVat;
	}
	public void setDlvPcSumNoVat(String dlvPcSumNoVat) {
		this.dlvPcSumNoVat = dlvPcSumNoVat;
	}
	public String getDlvAmt() {
		return dlvAmt;
	}
	public void setDlvAmt(String dlvAmt) {
		this.dlvAmt = dlvAmt;
	}
	public String getPf() {
		return pf;
	}
	public void setPf(String pf) {
		this.pf = pf;
	}
	public String getPfNoVat() {
		return pfNoVat;
	}
	public void setPfNoVat(String pfNoVat) {
		this.pfNoVat = pfNoVat;
	}
	public String getPfDlvAmt() {
		return pfDlvAmt;
	}
	public void setPfDlvAmt(String pfDlvAmt) {
		this.pfDlvAmt = pfDlvAmt;
	}
	public String getPfDlvAmtNoVat() {
		return pfDlvAmtNoVat;
	}
	public void setPfDlvAmtNoVat(String pfDlvAmtNoVat) {
		this.pfDlvAmtNoVat = pfDlvAmtNoVat;
	}
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
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getStdXchrAmt() {
		return stdXchrAmt;
	}
	public void setStdXchrAmt(String stdXchrAmt) {
		this.stdXchrAmt = stdXchrAmt;
	}
	public String getStdXchrKindCd() {
		return stdXchrKindCd;
	}
	public void setStdXchrKindCd(String stdXchrKindCd) {
		this.stdXchrKindCd = stdXchrKindCd;
	}
	public String getDlvModeCd() {
		return dlvModeCd;
	}
	public void setDlvModeCd(String dlvModeCd) {
		this.dlvModeCd = dlvModeCd;
	}
	public String getPoDt() {
		return poDt;
	}
	public void setPoDt(String poDt) {
		this.poDt = poDt;
	}
	public String getOrdArvlDt() {
		return ordArvlDt;
	}
	public void setOrdArvlDt(String ordArvlDt) {
		this.ordArvlDt = ordArvlDt;
	}
	public String getPoRegrEml() {
		return poRegrEml;
	}
	public void setPoRegrEml(String poRegrEml) {
		this.poRegrEml = poRegrEml;
	}
	public String getDlvDestCd() {
		return dlvDestCd;
	}
	public void setDlvDestCd(String dlvDestCd) {
		this.dlvDestCd = dlvDestCd;
	}
	@Override
	public String toString() {
		return "OrderPOVO [poAmt=" + poAmt + ", poXchrAmt=" + poXchrAmt
				+ ", pcSum=" + pcSum + ", pcSumNoVat=" + pcSumNoVat
				+ ", dlvPcSum=" + dlvPcSum + ", dlvPcSumNoVat=" + dlvPcSumNoVat
				+ ", dlvAmt=" + dlvAmt + ", pf=" + pf + ", pfNoVat=" + pfNoVat
				+ ", pfDlvAmt=" + pfDlvAmt + ", pfDlvAmtNoVat=" + pfDlvAmtNoVat
				+ ", poMemoCont=" + poMemoCont + ", ordNo=" + ordNo + ", poNo="
				+ poNo + ", custId=" + custId + ", stdXchrAmt=" + stdXchrAmt
				+ ", stdXchrKindCd=" + stdXchrKindCd + ", dlvModeCd="
				+ dlvModeCd + ", poDt=" + poDt + ", ordArvlDt=" + ordArvlDt
				+ ", poRegrEml=" + poRegrEml + ", dlvDestCd=" + dlvDestCd + "]";
	}
	
	
	
	
	
	
	
}
