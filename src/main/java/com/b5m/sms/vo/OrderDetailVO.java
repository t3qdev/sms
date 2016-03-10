package com.b5m.sms.vo;

import java.math.BigDecimal;

public class OrderDetailVO {
	private String ordNo;
	private String oprCns;			//상해담당자
	private String oprKr;			//한국담당자
	private String custId;				//클라이언트
	private String ordReqDt;			//문의일자

	private String ordHopeArvlDt;			//희망인도일자
	private BigDecimal stdXchrAmt;				//기준환율
	private String stdXchrKindCd;			//기준화폐
	private String pymtPrvdModeCont;			//대금지불방식
	
	private String dlvModeCd;			//견적조건
	private String dlvDestCd;		//항구
	private String ordEstmDt;			//견적일자 
	private String ordExpDt;		//견적유효
	
	private String ctrtTmplYn;		//계약서 템플릿 유무
	private String smplReqYn;		//샘플요청유무
	private String poSchdDt;		//PO예성일자
	private String qlfcReqYn;		//자격요청유무
	
	private String custOrdProcCont;		//주문프로세스
	
	private String ordMemoCont;			//비고 
	private String ordNm;					//주문명(견적서이름)
	private String ordStatCd;				//주문상태코드
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public String getOprCns() {
		return oprCns;
	}
	public void setOprCns(String oprCns) {
		this.oprCns = oprCns;
	}
	public String getOprKr() {
		return oprKr;
	}
	public void setOprKr(String oprKr) {
		this.oprKr = oprKr;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getOrdReqDt() {
		return ordReqDt;
	}
	public void setOrdReqDt(String ordReqDt) {
		this.ordReqDt = ordReqDt;
	}
	public String getOrdHopeArvlDt() {
		return ordHopeArvlDt;
	}
	public void setOrdHopeArvlDt(String ordHopeArvlDt) {
		this.ordHopeArvlDt = ordHopeArvlDt;
	}
	public BigDecimal getStdXchrAmt() {
		return stdXchrAmt;
	}
	public void setStdXchrAmt(BigDecimal stdXchrAmt) {
		this.stdXchrAmt = stdXchrAmt;
	}
	public String getStdXchrKindCd() {
		return stdXchrKindCd;
	}
	public void setStdXchrKindCd(String stdXchrKindCd) {
		this.stdXchrKindCd = stdXchrKindCd;
	}
	public String getPymtPrvdModeCont() {
		return pymtPrvdModeCont;
	}
	public void setPymtPrvdModeCont(String pymtPrvdModeCont) {
		this.pymtPrvdModeCont = pymtPrvdModeCont;
	}
	public String getDlvModeCd() {
		return dlvModeCd;
	}
	public void setDlvModeCd(String dlvModeCd) {
		this.dlvModeCd = dlvModeCd;
	}
	public String getDlvDestCd() {
		return dlvDestCd;
	}
	public void setDlvDestCd(String dlvDestCd) {
		this.dlvDestCd = dlvDestCd;
	}
	public String getOrdEstmDt() {
		return ordEstmDt;
	}
	public void setOrdEstmDt(String ordEstmDt) {
		this.ordEstmDt = ordEstmDt;
	}
	public String getOrdExpDt() {
		return ordExpDt;
	}
	public void setOrdExpDt(String ordExpDt) {
		this.ordExpDt = ordExpDt;
	}
	public String getCtrtTmplYn() {
		return ctrtTmplYn;
	}
	public void setCtrtTmplYn(String ctrtTmplYn) {
		this.ctrtTmplYn = ctrtTmplYn;
	}
	public String getSmplReqYn() {
		return smplReqYn;
	}
	public void setSmplReqYn(String smplReqYn) {
		this.smplReqYn = smplReqYn;
	}
	public String getPoSchdDt() {
		return poSchdDt;
	}
	public void setPoSchdDt(String poSchdDt) {
		this.poSchdDt = poSchdDt;
	}
	public String getQlfcReqYn() {
		return qlfcReqYn;
	}
	public void setQlfcReqYn(String qlfcReqYn) {
		this.qlfcReqYn = qlfcReqYn;
	}
	public String getCustOrdProcCont() {
		return custOrdProcCont;
	}
	public void setCustOrdProcCont(String custOrdProcCont) {
		this.custOrdProcCont = custOrdProcCont;
	}
	public String getOrdMemoCont() {
		return ordMemoCont;
	}
	public void setOrdMemoCont(String ordMemoCont) {
		this.ordMemoCont = ordMemoCont;
	}
	public String getOrdNm() {
		return ordNm;
	}
	public void setOrdNm(String ordNm) {
		this.ordNm = ordNm;
	}
	public String getOrdStatCd() {
		return ordStatCd;
	}
	public void setOrdStatCd(String ordStatCd) {
		this.ordStatCd = ordStatCd;
	}
	@Override
	public String toString() {
		return "OrderDetailVO [ordNo=" + ordNo + ", oprCns=" + oprCns
				+ ", oprKr=" + oprKr + ", custId=" + custId + ", ordReqDt="
				+ ordReqDt + ", ordHopeArvlDt=" + ordHopeArvlDt
				+ ", stdXchrAmt=" + stdXchrAmt + ", stdXchrKindCd="
				+ stdXchrKindCd + ", pymtPrvdModeCont=" + pymtPrvdModeCont
				+ ", dlvModeCd=" + dlvModeCd + ", dlvDestCd=" + dlvDestCd
				+ ", ordEstmDt=" + ordEstmDt + ", ordExpDt=" + ordExpDt
				+ ", ctrtTmplYn=" + ctrtTmplYn + ", smplReqYn=" + smplReqYn
				+ ", poSchdDt=" + poSchdDt + ", qlfcReqYn=" + qlfcReqYn
				+ ", custOrdProcCont=" + custOrdProcCont + ", ordMemoCont="
				+ ordMemoCont + ", ordNm=" + ordNm + ", ordStatCd=" + ordStatCd
				+ "]";
	}

	
	
	
	
	
}
