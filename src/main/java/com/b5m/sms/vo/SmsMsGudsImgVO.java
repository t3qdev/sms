package com.b5m.sms.vo;

public class SmsMsGudsImgVO {
	private String gudsId;
	private String gudsImgCd;
	private String gudsImgOrgtFileNm;		//상품이미지 원본파일이름
	private String gudsImgSysFileNm;		//상품이미지 시스템파일이름
	private String gudsImgCdnAddr;			//상품이미지cdn주소
	
	public String getGudsId() {
		return gudsId;
	}
	public void setGudsId(String gudsId) {
		this.gudsId = gudsId;
	}
	public String getGudsImgCd() {
		return gudsImgCd;
	}
	public void setGudsImgCd(String gudsImgCd) {
		this.gudsImgCd = gudsImgCd;
	}
	public String getGudsImgOrgtFileNm() {
		return gudsImgOrgtFileNm;
	}
	public void setGudsImgOrgtFileNm(String gudsImgOrgtFileNm) {
		this.gudsImgOrgtFileNm = gudsImgOrgtFileNm;
	}
	public String getGudsImgSysFileNm() {
		return gudsImgSysFileNm;
	}
	public void setGudsImgSysFileNm(String gudsImgSysFileNm) {
		this.gudsImgSysFileNm = gudsImgSysFileNm;
	}
	public String getGudsImgCdnAddr() {
		return gudsImgCdnAddr;
	}
	public void setGudsImgCdnAddr(String gudsImgCdnAddr) {
		this.gudsImgCdnAddr = gudsImgCdnAddr;
	}
	@Override
	public String toString() {
		return "SmsMsGudsImgVO [gudsId=" + gudsId + ", gudsImgCd=" + gudsImgCd
				+ ", gudsImgOrgtFileNm=" + gudsImgOrgtFileNm
				+ ", gudsImgSysFileNm=" + gudsImgSysFileNm
				+ ", gudsImgCdnAddr=" + gudsImgCdnAddr + "]";
	}
	
	
}
