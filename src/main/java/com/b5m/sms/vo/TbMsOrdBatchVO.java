package com.b5m.sms.vo;

public class TbMsOrdBatchVO {

	//tbMsOrd.selectTbMsOrdForSmsMsOrd 에서 select 해오는 것들
	private String ordNo;				//주문번호 pfK         1
	private String b5cOrdNo;
	private String gudsOptId;
	private String ordGudsSalePrc;		//주문상품판매가(PO단가USD)
	private String ordGudsQty;			//상품 수량
	private String gudsOptUpcId;		//upc 바코드
	private String gudsOptOrgPrc;      
	private String gudsNm;					// 상품명
	private String gudsCnsNm;			// 상품 중문명
	private String gudsVatRfndYn;	
	private String brndId;              // =sllrId
	private String brndStrNm;				//브랜드 네임
	private String brndStrEngNm;		// 브랜드 네임 영문
	private String gudsIdOfB5m;        // b5m 에서의 gudsId 를 가져오기 위함. - 원래는 opt id가 필요한데, IMG 는 gudsID + sllrID 가 필요.
	private String gudsId;                //  SMS 에서 넣을 새로운 gudsId
	private String custId;				 // TB_MS_ORD 의 custID 를 가져온다.
	private String dlvModeCd;          // TB_MS_ORD 의 dlvModeCd를 가져온다.
	private String gudsDlvcDesnVal5;    // TB_MS_ORD 의 인박스수량
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public String getB5cOrdNo() {
		return b5cOrdNo;
	}
	public void setB5cOrdNo(String b5cOrdNo) {
		this.b5cOrdNo = b5cOrdNo;
	}
	public String getGudsOptId() {
		return gudsOptId;
	}
	public void setGudsOptId(String gudsOptId) {
		this.gudsOptId = gudsOptId;
	}
	public String getOrdGudsSalePrc() {
		return ordGudsSalePrc;
	}
	public void setOrdGudsSalePrc(String ordGudsSalePrc) {
		this.ordGudsSalePrc = ordGudsSalePrc;
	}
	public String getOrdGudsQty() {
		return ordGudsQty;
	}
	public void setOrdGudsQty(String ordGudsQty) {
		this.ordGudsQty = ordGudsQty;
	}
	public String getGudsOptUpcId() {
		return gudsOptUpcId;
	}
	public void setGudsOptUpcId(String gudsOptUpcId) {
		this.gudsOptUpcId = gudsOptUpcId;
	}
	public String getGudsOptOrgPrc() {
		return gudsOptOrgPrc;
	}
	public void setGudsOptOrgPrc(String gudsOptOrgPrc) {
		this.gudsOptOrgPrc = gudsOptOrgPrc;
	}
	public String getGudsNm() {
		return gudsNm;
	}
	public void setGudsNm(String gudsNm) {
		this.gudsNm = gudsNm;
	}
	public String getGudsCnsNm() {
		return gudsCnsNm;
	}
	public void setGudsCnsNm(String gudsCnsNm) {
		this.gudsCnsNm = gudsCnsNm;
	}
	public String getGudsVatRfndYn() {
		return gudsVatRfndYn;
	}
	public void setGudsVatRfndYn(String gudsVatRfndYn) {
		this.gudsVatRfndYn = gudsVatRfndYn;
	}
	public String getBrndId() {
		return brndId;
	}
	public void setBrndId(String brndId) {
		this.brndId = brndId;
	}
	public String getBrndStrNm() {
		return brndStrNm;
	}
	public void setBrndStrNm(String brndStrNm) {
		this.brndStrNm = brndStrNm;
	}
	public String getBrndStrEngNm() {
		return brndStrEngNm;
	}
	public void setBrndStrEngNm(String brndStrEngNm) {
		this.brndStrEngNm = brndStrEngNm;
	}
	public String getGudsIdOfB5m() {
		return gudsIdOfB5m;
	}
	public void setGudsIdOfB5m(String gudsIdOfB5m) {
		this.gudsIdOfB5m = gudsIdOfB5m;
	}
	public String getGudsId() {
		return gudsId;
	}
	public void setGudsId(String gudsId) {
		this.gudsId = gudsId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getDlvModeCd() {
		return dlvModeCd;
	}
	public void setDlvModeCd(String dlvModeCd) {
		this.dlvModeCd = dlvModeCd;
	}
	public String getGudsDlvcDesnVal5() {
		return gudsDlvcDesnVal5;
	}
	public void setGudsDlvcDesnVal5(String gudsDlvcDesnVal5) {
		this.gudsDlvcDesnVal5 = gudsDlvcDesnVal5;
	}
	@Override
	public String toString() {
		return "TbMsOrdBatchVO [ordNo=" + ordNo + ", b5cOrdNo=" + b5cOrdNo
				+ ", gudsOptId=" + gudsOptId + ", ordGudsSalePrc="
				+ ordGudsSalePrc + ", ordGudsQty=" + ordGudsQty
				+ ", gudsOptUpcId=" + gudsOptUpcId + ", gudsOptOrgPrc="
				+ gudsOptOrgPrc + ", gudsNm=" + gudsNm + ", gudsCnsNm="
				+ gudsCnsNm + ", gudsVatRfndYn=" + gudsVatRfndYn + ", brndId="
				+ brndId + ", brndStrNm=" + brndStrNm + ", brndStrEngNm="
				+ brndStrEngNm + ", gudsIdOfB5m=" + gudsIdOfB5m + ", gudsId="
				+ gudsId + ", custId=" + custId + ", dlvModeCd=" + dlvModeCd
				+ ", gudsDlvcDesnVal5=" + gudsDlvcDesnVal5 + "]";
	}
	
	
	
	
	
	
	
	

	
}
