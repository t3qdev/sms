package com.b5m.sms.vo;

public class ExcelClientReqGudsVO {

	//클라이언트 요청 견적서.xlsl 의 상품을 담기 위한 VO
	//NO, 바코드, 상품명, 수량, 규격, 가격, 유효기간, URL
	
	private String ordGudsSeq;   	//NO    주문상품번호 PK  
	private String gudsUpcId;       	//상품바코드		
	private String ordGudsCnsNm;  	//중문 상품명
	private String ordGudsQty;     	//상품 수량, (예상요청)수량
//	private String 규격;                    // 규격 - SKIP. 의미없음.
	private String ordGudsSalePrc;		//주문상품판매가(PO단가USD)
//	private String 유효기간;               // 의미 없음.
	private String ordGudsUrlAddr;		//주문상품url주소, 링크
	public String getOrdGudsSeq() {
		return ordGudsSeq;
	}
	public void setOrdGudsSeq(String ordGudsSeq) {
		this.ordGudsSeq = ordGudsSeq;
	}
	public String getGudsUpcId() {
		return gudsUpcId;
	}
	public void setGudsUpcId(String gudsUpcId) {
		this.gudsUpcId = gudsUpcId;
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
	public String getOrdGudsSalePrc() {
		return ordGudsSalePrc;
	}
	public void setOrdGudsSalePrc(String ordGudsSalePrc) {
		this.ordGudsSalePrc = ordGudsSalePrc;
	}
	public String getOrdGudsUrlAddr() {
		return ordGudsUrlAddr;
	}
	public void setOrdGudsUrlAddr(String ordGudsUrlAddr) {
		this.ordGudsUrlAddr = ordGudsUrlAddr;
	}
	@Override
	public String toString() {
		return "ExcelClientReqVO [ordGudsSeq=" + ordGudsSeq + ", gudsUpcId="
				+ gudsUpcId + ", ordGudsCnsNm=" + ordGudsCnsNm
				+ ", ordGudsQty=" + ordGudsQty + ", ordGudsSalePrc="
				+ ordGudsSalePrc + ", ordGudsUrlAddr=" + ordGudsUrlAddr + "]";
	}
	
	
	
	
}
