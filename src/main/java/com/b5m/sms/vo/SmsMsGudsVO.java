package com.b5m.sms.vo;

public class SmsMsGudsVO {
	private String gudsId;			//상품Id  PK
	private String brndId;			//브랜드Id
	private String gudsKorNm;		//상품한글명
	private String gudsCnsNm;	//상품중문명
	private String gudsUpcId;		//상품바코드
	
	private String gudsVatRfndYn;	//부가가치세 환급여부
	private String gudsUrlAddr;		//상품 url 주소
	private String gudsInbxQty;		//상품인박스수량
	private String b5cSkuId;          //  =방우차이의 guds_opt_id 와 동일하며, UPC ID 가 없는 것을 대체
	
	//로컬이미지 경로추가
	private String imgSrcPath;		//이미지 경로

	public String getGudsId() {
		return gudsId;
	}

	public void setGudsId(String gudsId) {
		this.gudsId = gudsId;
	}

	public String getBrndId() {
		return brndId;
	}

	public void setBrndId(String brndId) {
		this.brndId = brndId;
	}

	public String getGudsKorNm() {
		return gudsKorNm;
	}

	public void setGudsKorNm(String gudsKorNm) {
		this.gudsKorNm = gudsKorNm;
	}

	public String getGudsCnsNm() {
		return gudsCnsNm;
	}

	public void setGudsCnsNm(String gudsCnsNm) {
		this.gudsCnsNm = gudsCnsNm;
	}

	public String getGudsUpcId() {
		return gudsUpcId;
	}

	public void setGudsUpcId(String gudsUpcId) {
		this.gudsUpcId = gudsUpcId;
	}

	public String getGudsVatRfndYn() {
		return gudsVatRfndYn;
	}

	public void setGudsVatRfndYn(String gudsVatRfndYn) {
		this.gudsVatRfndYn = gudsVatRfndYn;
	}

	public String getGudsUrlAddr() {
		return gudsUrlAddr;
	}

	public void setGudsUrlAddr(String gudsUrlAddr) {
		this.gudsUrlAddr = gudsUrlAddr;
	}

	public String getGudsInbxQty() {
		return gudsInbxQty;
	}

	public void setGudsInbxQty(String gudsInbxQty) {
		this.gudsInbxQty = gudsInbxQty;
	}

	public String getB5cSkuId() {
		return b5cSkuId;
	}

	public void setB5cSkuId(String b5cSkuId) {
		this.b5cSkuId = b5cSkuId;
	}

	public String getImgSrcPath() {
		return imgSrcPath;
	}

	public void setImgSrcPath(String imgSrcPath) {
		this.imgSrcPath = imgSrcPath;
	}

	@Override
	public String toString() {
		return "SmsMsGudsVO [gudsId=" + gudsId + ", brndId=" + brndId
				+ ", gudsKorNm=" + gudsKorNm + ", gudsCnsNm=" + gudsCnsNm
				+ ", gudsUpcId=" + gudsUpcId + ", gudsVatRfndYn="
				+ gudsVatRfndYn + ", gudsUrlAddr=" + gudsUrlAddr
				+ ", gudsInbxQty=" + gudsInbxQty + ", b5cSkuId=" + b5cSkuId
				+ ", imgSrcPath=" + imgSrcPath + "]";
	}
	
	
	
	
	
	
	
	
}
