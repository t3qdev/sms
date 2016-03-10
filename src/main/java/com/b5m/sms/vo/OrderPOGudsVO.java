package com.b5m.sms.vo;

public class OrderPOGudsVO {
	private String imgSrcPath;		//이미지경로
	
	private String gudsUpcId;		//상품바코드
	private String gudsCnsNm;		//중문상품명-
	private String gudsKorNm;			//한글상품명-
	private String ordGudsQty;		//상품주문수량-
	
	private String gudsInbxQty;		//인박스 수량
	private String vatYn;		//부가세 적용여부
	private String pcPrc;		//매입단가 purchase price (gudsOrgPrc)-
	private String pcPrcVat;		//매입단가 vat포함
	private String pcPrcNoVat;		//매입단가 vat미포함
	
	private String poPrc;		//po단가	(gudsSalePrc)-
	private String poPrcSum;		//po합계 (po단가*po주문수량)
	private String poXchrPrc;		//po단가(환율) (po단가*환율) 
	private String poXchrPrcSum;		//po합계 (po단가*환율)*po주문수량
	private String pvdrnNm;		//공급자명-
	
	private String crn;		//사업자등록-
	
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
	public String getGudsCnsNm() {
		return gudsCnsNm;
	}
	public void setGudsCnsNm(String gudsCnsNm) {
		this.gudsCnsNm = gudsCnsNm;
	}
	public String getGudsKorNm() {
		return gudsKorNm;
	}
	public void setGudsKorNm(String gudsKorNm) {
		this.gudsKorNm = gudsKorNm;
	}
	public String getOrdGudsQty() {
		return ordGudsQty;
	}
	public void setOrdGudsQty(String ordGudsQty) {
		this.ordGudsQty = ordGudsQty;
	}
	public String getGudsInbxQty() {
		return gudsInbxQty;
	}
	public void setGudsInbxQty(String gudsInbxQty) {
		this.gudsInbxQty = gudsInbxQty;
	}
	public String getVatYn() {
		return vatYn;
	}
	public void setVatYn(String vatYn) {
		this.vatYn = vatYn;
	}
	public String getPcPrc() {
		return pcPrc;
	}
	public void setPcPrc(String pcPrc) {
		this.pcPrc = pcPrc;
	}
	public String getPcPrcVat() {
		return pcPrcVat;
	}
	public void setPcPrcVat(String pcPrcVat) {
		this.pcPrcVat = pcPrcVat;
	}
	public String getPcPrcNoVat() {
		return pcPrcNoVat;
	}
	public void setPcPrcNoVat(String pcPrcNoVat) {
		this.pcPrcNoVat = pcPrcNoVat;
	}
	public String getPoPrc() {
		return poPrc;
	}
	public void setPoPrc(String poPrc) {
		this.poPrc = poPrc;
	}
	public String getPoPrcSum() {
		return poPrcSum;
	}
	public void setPoPrcSum(String poPrcSum) {
		this.poPrcSum = poPrcSum;
	}
	public String getPoXchrPrc() {
		return poXchrPrc;
	}
	public void setPoXchrPrc(String poXchrPrc) {
		this.poXchrPrc = poXchrPrc;
	}
	public String getPoXchrPrcSum() {
		return poXchrPrcSum;
	}
	public void setPoXchrPrcSum(String poXchrPrcSum) {
		this.poXchrPrcSum = poXchrPrcSum;
	}
	public String getPvdrnNm() {
		return pvdrnNm;
	}
	public void setPvdrnNm(String pvdrnNm) {
		this.pvdrnNm = pvdrnNm;
	}
	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
	@Override
	public String toString() {
		return "OrderPOGudsVO [imgSrcPath=" + imgSrcPath + ", gudsUpcId="
				+ gudsUpcId + ", gudsCnsNm=" + gudsCnsNm + ", gudsKorNm="
				+ gudsKorNm + ", ordGudsQty=" + ordGudsQty + ", gudsInbxQty="
				+ gudsInbxQty + ", vatYn=" + vatYn + ", pcPrc=" + pcPrc
				+ ", pcPrcVat=" + pcPrcVat + ", pcPrcNoVat=" + pcPrcNoVat
				+ ", poPrc=" + poPrc + ", poPrcSum=" + poPrcSum
				+ ", poXchrPrc=" + poXchrPrc + ", poXchrPrcSum=" + poXchrPrcSum
				+ ", pvdrnNm=" + pvdrnNm + ", crn=" + crn + "]";
	}
	
	
	
	
}
