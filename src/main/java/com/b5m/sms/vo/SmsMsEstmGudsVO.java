package com.b5m.sms.vo;

public class SmsMsEstmGudsVO {
	private String ordNo;		//주문번호
	private String gudsId;		//상품Id
	private String ordGudsKorNm;		//상품한글명
	private String ordGudsCnsNm;	//상품중문명
	private String ordGudsQty;			//주문수량 ordGudsQty
	private String ordGudsOrgPrc;		//상품원가(매입단가)pcPrc
	private String ordGudsSalePrc;		//상품판매가(po단가)poPrc
	private String ordGudsPrvdNm;		//주문상품지급(사) 이름pvdrnNm
	private String ordGudsPrvdCrn;		//주문상품지급 사업자번호crn
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public String getGudsId() {
		return gudsId;
	}
	public void setGudsId(String gudsId) {
		this.gudsId = gudsId;
	}
	public String getOrdGudsKorNm() {
		return ordGudsKorNm;
	}
	public void setOrdGudsKorNm(String ordGudsKorNm) {
		this.ordGudsKorNm = ordGudsKorNm;
	}
	public String getOrdGudsCnsNm() {
		return ordGudsCnsNm;
	}
	public void setOrdGudsCnsNm(String ordGudsCnsNm) {
		this.ordGudsCnsNm = ordGudsCnsNm;
	}
	public String getOrdGudsQty() {
		return ordGudsQty;
	}
	public void setOrdGudsQty(String ordGudsQty) {
		this.ordGudsQty = ordGudsQty;
	}
	public String getOrdGudsOrgPrc() {
		return ordGudsOrgPrc;
	}
	public void setOrdGudsOrgPrc(String ordGudsOrgPrc) {
		this.ordGudsOrgPrc = ordGudsOrgPrc;
	}
	public String getOrdGudsSalePrc() {
		return ordGudsSalePrc;
	}
	public void setOrdGudsSalePrc(String ordGudsSalePrc) {
		this.ordGudsSalePrc = ordGudsSalePrc;
	}
	public String getOrdGudsPrvdNm() {
		return ordGudsPrvdNm;
	}
	public void setOrdGudsPrvdNm(String ordGudsPrvdNm) {
		this.ordGudsPrvdNm = ordGudsPrvdNm;
	}
	public String getOrdGudsPrvdCrn() {
		return ordGudsPrvdCrn;
	}
	public void setOrdGudsPrvdCrn(String ordGudsPrvdCrn) {
		this.ordGudsPrvdCrn = ordGudsPrvdCrn;
	}
	@Override
	public String toString() {
		return "SmsMsEstmGudsVO [ordNo=" + ordNo + ", gudsId=" + gudsId
				+ ", ordGudsKorNm=" + ordGudsKorNm + ", ordGudsCnsNm="
				+ ordGudsCnsNm + ", ordGudsQty=" + ordGudsQty
				+ ", ordGudsOrgPrc=" + ordGudsOrgPrc + ", ordGudsSalePrc="
				+ ordGudsSalePrc + ", ordGudsPrvdNm=" + ordGudsPrvdNm
				+ ", ordGudsPrvdCrn=" + ordGudsPrvdCrn + "]";
	}
	
	
}
