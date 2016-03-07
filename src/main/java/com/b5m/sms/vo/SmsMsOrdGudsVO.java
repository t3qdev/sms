package com.b5m.sms.vo;

public class SmsMsOrdGudsVO {
	private String ordNo;				//주문번호 pfK
	private String ordGudsSeq;		//주문상품번호 PK
	private String gudsId;				//상품Id FK
	private String ordGudsMpngYn;	//매핑정보여부
	private String ordGudsKorNm;		//한글 상품명 3
	
	private String ordGudsCnsNm;		//중문 상품명 4
	private String ordGudsOrgPrc;		//주문상품원가 ,상품가격 7
	private String ordGudsSalePrc;		//주문상품판매가(PO단가USD)
	private String ordGudsQty;			//상품 수량, (예상요청)수량 5
	private String ordGudsOprEml;		//상품 담당자 이메일 FK
	
	private String ordGudsSizeVal;		//주문상품크기값 ,규격 6
	private String ordGudsUrlAddr;		//주문상품url주소, 링크 9
	private String ordGudsUpcId;        //주문상품 바코드   : 클라이언트 요청견적서에서 들어온 바코드, 실제 guds db 바코드와 따로 관리 해야 한다.
		
	//orderDetail 페이지에서 사용하기 위한값
	private String imgSrcPath;		//이미지 경로 1
	private String gudsUpcId;			//바코드 2
	private String gudsInbxQty; 		//인박스 수량5
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public String getOrdGudsSeq() {
		return ordGudsSeq;
	}
	public void setOrdGudsSeq(String ordGudsSeq) {
		this.ordGudsSeq = ordGudsSeq;
	}
	public String getGudsId() {
		return gudsId;
	}
	public void setGudsId(String gudsId) {
		this.gudsId = gudsId;
	}
	public String getOrdGudsMpngYn() {
		return ordGudsMpngYn;
	}
	public void setOrdGudsMpngYn(String ordGudsMpngYn) {
		this.ordGudsMpngYn = ordGudsMpngYn;
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
	public String getOrdGudsQty() {
		return ordGudsQty;
	}
	public void setOrdGudsQty(String ordGudsQty) {
		this.ordGudsQty = ordGudsQty;
	}
	public String getOrdGudsOprEml() {
		return ordGudsOprEml;
	}
	public void setOrdGudsOprEml(String ordGudsOprEml) {
		this.ordGudsOprEml = ordGudsOprEml;
	}
	public String getOrdGudsSizeVal() {
		return ordGudsSizeVal;
	}
	public void setOrdGudsSizeVal(String ordGudsSizeVal) {
		this.ordGudsSizeVal = ordGudsSizeVal;
	}
	public String getOrdGudsUrlAddr() {
		return ordGudsUrlAddr;
	}
	public void setOrdGudsUrlAddr(String ordGudsUrlAddr) {
		this.ordGudsUrlAddr = ordGudsUrlAddr;
	}
	public String getOrdGudsUpcId() {
		return ordGudsUpcId;
	}
	public void setOrdGudsUpcId(String ordGudsUpcId) {
		this.ordGudsUpcId = ordGudsUpcId;
	}
	public String getImgSrcPath() {
		return imgSrcPath;
	}
	public void setImgSrcPath(String imgSrcPath) {
		this.imgSrcPath = imgSrcPath;
	}
	public String getGudsUpcId() {
		return gudsUpcId;
	}
	public void setGudsUpcId(String gudsUpcId) {
		this.gudsUpcId = gudsUpcId;
	}
	public String getGudsInbxQty() {
		return gudsInbxQty;
	}
	public void setGudsInbxQty(String gudsInbxQty) {
		this.gudsInbxQty = gudsInbxQty;
	}
	@Override
	public String toString() {
		return "SmsMsOrdGudsVO [ordNo=" + ordNo + ", ordGudsSeq=" + ordGudsSeq
				+ ", gudsId=" + gudsId + ", ordGudsMpngYn=" + ordGudsMpngYn
				+ ", ordGudsKorNm=" + ordGudsKorNm + ", ordGudsCnsNm="
				+ ordGudsCnsNm + ", ordGudsOrgPrc=" + ordGudsOrgPrc
				+ ", ordGudsSalePrc=" + ordGudsSalePrc + ", ordGudsQty="
				+ ordGudsQty + ", ordGudsOprEml=" + ordGudsOprEml
				+ ", ordGudsSizeVal=" + ordGudsSizeVal + ", ordGudsUrlAddr="
				+ ordGudsUrlAddr + ", ordGudsUpcId=" + ordGudsUpcId
				+ ", imgSrcPath=" + imgSrcPath + ", gudsUpcId=" + gudsUpcId
				+ ", gudsInbxQty=" + gudsInbxQty + "]";
	}

 
	
	
	
	
}
