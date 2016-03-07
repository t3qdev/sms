package com.b5m.sms.vo;

public class SmsMsOrdFileVO {
	private String ordNo;				//주문번호
	private String ordFileSeq;			//주문파일일련번호
	private String ordFileKindCd;		//주문파일종류코드
	private String ordFileOrgtFileNm;	//주문파일 원본이름
	private String ordFileSysFileNm;		//주문파일 시스템이름
	private String ordFileRegrEml;		//주문파일 등록자이메일
	private String ordFileRegDttm;		//주문파일 등록일자
	
	//주문상세페이지 표시를 위해 사용
	private String userAlasEngNm;		//약칭
	private String userAlasCnsNm;		//화명
	private String ordFilepath;			//주문파일실제 경로
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public String getOrdFileSeq() {
		return ordFileSeq;
	}
	public void setOrdFileSeq(String ordFileSeq) {
		this.ordFileSeq = ordFileSeq;
	}
	public String getOrdFileKindCd() {
		return ordFileKindCd;
	}
	public void setOrdFileKindCd(String ordFileKindCd) {
		this.ordFileKindCd = ordFileKindCd;
	}
	public String getOrdFileOrgtFileNm() {
		return ordFileOrgtFileNm;
	}
	public void setOrdFileOrgtFileNm(String ordFileOrgtFileNm) {
		this.ordFileOrgtFileNm = ordFileOrgtFileNm;
	}
	public String getOrdFileSysFileNm() {
		return ordFileSysFileNm;
	}
	public void setOrdFileSysFileNm(String ordFileSysFileNm) {
		this.ordFileSysFileNm = ordFileSysFileNm;
	}
	public String getOrdFileRegrEml() {
		return ordFileRegrEml;
	}
	public void setOrdFileRegrEml(String ordFileRegrEml) {
		this.ordFileRegrEml = ordFileRegrEml;
	}
	public String getOrdFileRegDttm() {
		return ordFileRegDttm;
	}
	public void setOrdFileRegDttm(String ordFileRegDttm) {
		this.ordFileRegDttm = ordFileRegDttm;
	}
	public String getUserAlasEngNm() {
		return userAlasEngNm;
	}
	public void setUserAlasEngNm(String userAlasEngNm) {
		this.userAlasEngNm = userAlasEngNm;
	}
	public String getUserAlasCnsNm() {
		return userAlasCnsNm;
	}
	public void setUserAlasCnsNm(String userAlasCnsNm) {
		this.userAlasCnsNm = userAlasCnsNm;
	}
	public String getOrdFilepath() {
		return ordFilepath;
	}
	public void setOrdFilepath(String ordFilepath) {
		this.ordFilepath = ordFilepath;
	}
	@Override
	public String toString() {
		return "SmsMsOrdFileVO [ordNo=" + ordNo + ", ordFileSeq=" + ordFileSeq
				+ ", ordFileKindCd=" + ordFileKindCd + ", ordFileOrgtFileNm="
				+ ordFileOrgtFileNm + ", ordFileSysFileNm=" + ordFileSysFileNm
				+ ", ordFileRegrEml=" + ordFileRegrEml + ", ordFileRegDttm="
				+ ordFileRegDttm + ", userAlasEngNm=" + userAlasEngNm
				+ ", userAlasCnsNm=" + userAlasCnsNm + ", ordFilepath="
				+ ordFilepath + "]";
	}
	
	
	
	
	
}
