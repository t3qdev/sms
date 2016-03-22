package com.b5m.sms.vo;

import java.math.BigDecimal;

public class SmsMsOrdVO {
	private String ordNo;			//주문번호
	private String ordNm;			//주문명
	private String ordStatCd;		//주문상태코드
	private String b5cOrdNo;		//b5c주문번호
	private String dlvModeCd;		//배송방식코드 = 견적조건
	
	private String dlvDestCd;		//배송목적코드 = 항구
	private String custId;		//고객Id 
	private BigDecimal ordSumAmt	;		//주문총액
	private String ordEstmDt;		//주문견적일
	private String ordReqDt;		//주문등록일
	
	private String ordExpDt;		//주문만료일
	private String ordHopeArvlDt;	//주문희망도착일
	private String ordArvlDt;		//주문도착일
	private String stdXchrKindCd;	//기준환율종류코드
	private BigDecimal stdXchrAmt;		//기준환율총액
	
	private String bactRegDt;			//정산등록일
	private String bactRegrEml;		//정산등록이메일
	private String bactPrvdDt;		//정산지급일
	private BigDecimal bactPrvdAmt;		//정산지급액
	private String bactPrvdMemoCont;		//정산지급메모
	
	private String poSchdDt;		//PO예상일
	private String pymtPrvdModeCont;		//결제지급방식내용
	private String ctrtTmplYn;		//계약서템플릿 유무
	private String smplReqYn;		//샘플요청유무
	private String qlfcReqYn;		//자격요청유무
	
	private String custOrdProcCont;		//고객주문프로세스
	
	private String paptDpstDt;		//선금입금일자
	private BigDecimal paptDpstAmt;		//선금입금금액
	private BigDecimal paptDpstRate;		//선금입금율

	private String raptDpstDt;		//잔금입금일자
	private BigDecimal raptDpstAmt;		//잔금입금금액
	private BigDecimal raptDpstRate;		//잔금입금율
	
	private String wrhsDlvDt;		//입고 배송일자
	private String wrhsDlvDestCd;		//입고배송도착지코드
	
	private String dptrDlvDt;		//출항배송일자
	private String dptrDlvDestCd;		//출항배송도착지코드

	private String arvlDlvDt;		//도착배송일자
	private String arvlDlvDestCd;		//도착배송도착지코드
	
	private String poDlvDt;		//po배송일자
	private String poDlvDestCd;		//po배송목적지코드
	
	private String ordMemoCont;		//주문메모내용
	private String ordTypeCd;			//주문영입여부
	private String b5mBuyCont;      // 방한핀여부

	//SMS_MS_USER 테이블의 변수
	private String userEml;				// 사용자 ID
	private String userAlasCnsNm;	// 사용자 중문화명   <- 클라이언트 요청견적서에는 중문화명이 담당자로 들어간다.

	
	//orderManamentView 에서 있는 특이한 변수들.
	private String clientNm;				// 클라이언트
	private String orderedGudsNm;	// 오더상품
	private String showDetail;			// 상세보기
	private String korMng;             // 한국팀담당자
	private String cnsMng; 			// 상해팀담당자
	private String histDetail;          // 현재상태상세내용

	//orderManamentView 에서 sql paging 하기 위해 사용하는 변수.
	private int start;
	private int end;

	//orderManamentView 에서 공급상물품대금입금 : bactRegDt+" "+bactPrvdAmt
	private String bactPrvdDtPlusbactPrvdAmt;
	private String count;     // 총 열 수  // paging 위해서 필요
	private int page;		// 현재 페이지  // paging 위해서 필요
	private int row;		// 현재 페이지의 row 수 // paging 위해서 필요

	//orderManamentView 에서 바코드 OR 상품명으로 검색하기 위해서 필요
	private String searchKeyword;

	//orderManamentView 에서 딜규모에 환율 보여주기 위해서 필요
	private BigDecimal korXchrAmt;
	private BigDecimal usdXchrAmt;
	private BigDecimal cnsXchrAmt;
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
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
	public String getB5cOrdNo() {
		return b5cOrdNo;
	}
	public void setB5cOrdNo(String b5cOrdNo) {
		this.b5cOrdNo = b5cOrdNo;
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
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public BigDecimal getOrdSumAmt() {
		return ordSumAmt;
	}
	public void setOrdSumAmt(BigDecimal ordSumAmt) {
		this.ordSumAmt = ordSumAmt;
	}
	public String getOrdEstmDt() {
		return ordEstmDt;
	}
	public void setOrdEstmDt(String ordEstmDt) {
		this.ordEstmDt = ordEstmDt;
	}
	public String getOrdReqDt() {
		return ordReqDt;
	}
	public void setOrdReqDt(String ordReqDt) {
		this.ordReqDt = ordReqDt;
	}
	public String getOrdExpDt() {
		return ordExpDt;
	}
	public void setOrdExpDt(String ordExpDt) {
		this.ordExpDt = ordExpDt;
	}
	public String getOrdHopeArvlDt() {
		return ordHopeArvlDt;
	}
	public void setOrdHopeArvlDt(String ordHopeArvlDt) {
		this.ordHopeArvlDt = ordHopeArvlDt;
	}
	public String getOrdArvlDt() {
		return ordArvlDt;
	}
	public void setOrdArvlDt(String ordArvlDt) {
		this.ordArvlDt = ordArvlDt;
	}
	public String getStdXchrKindCd() {
		return stdXchrKindCd;
	}
	public void setStdXchrKindCd(String stdXchrKindCd) {
		this.stdXchrKindCd = stdXchrKindCd;
	}
	public BigDecimal getStdXchrAmt() {
		return stdXchrAmt;
	}
	public void setStdXchrAmt(BigDecimal stdXchrAmt) {
		this.stdXchrAmt = stdXchrAmt;
	}
	public String getBactRegDt() {
		return bactRegDt;
	}
	public void setBactRegDt(String bactRegDt) {
		this.bactRegDt = bactRegDt;
	}
	public String getBactRegrEml() {
		return bactRegrEml;
	}
	public void setBactRegrEml(String bactRegrEml) {
		this.bactRegrEml = bactRegrEml;
	}
	public String getBactPrvdDt() {
		return bactPrvdDt;
	}
	public void setBactPrvdDt(String bactPrvdDt) {
		this.bactPrvdDt = bactPrvdDt;
	}
	public BigDecimal getBactPrvdAmt() {
		return bactPrvdAmt;
	}
	public void setBactPrvdAmt(BigDecimal bactPrvdAmt) {
		this.bactPrvdAmt = bactPrvdAmt;
	}
	public String getBactPrvdMemoCont() {
		return bactPrvdMemoCont;
	}
	public void setBactPrvdMemoCont(String bactPrvdMemoCont) {
		this.bactPrvdMemoCont = bactPrvdMemoCont;
	}
	public String getPoSchdDt() {
		return poSchdDt;
	}
	public void setPoSchdDt(String poSchdDt) {
		this.poSchdDt = poSchdDt;
	}
	public String getPymtPrvdModeCont() {
		return pymtPrvdModeCont;
	}
	public void setPymtPrvdModeCont(String pymtPrvdModeCont) {
		this.pymtPrvdModeCont = pymtPrvdModeCont;
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
	public String getPaptDpstDt() {
		return paptDpstDt;
	}
	public void setPaptDpstDt(String paptDpstDt) {
		this.paptDpstDt = paptDpstDt;
	}
	public BigDecimal getPaptDpstAmt() {
		return paptDpstAmt;
	}
	public void setPaptDpstAmt(BigDecimal paptDpstAmt) {
		this.paptDpstAmt = paptDpstAmt;
	}
	public BigDecimal getPaptDpstRate() {
		return paptDpstRate;
	}
	public void setPaptDpstRate(BigDecimal paptDpstRate) {
		this.paptDpstRate = paptDpstRate;
	}
	public String getRaptDpstDt() {
		return raptDpstDt;
	}
	public void setRaptDpstDt(String raptDpstDt) {
		this.raptDpstDt = raptDpstDt;
	}
	public BigDecimal getRaptDpstAmt() {
		return raptDpstAmt;
	}
	public void setRaptDpstAmt(BigDecimal raptDpstAmt) {
		this.raptDpstAmt = raptDpstAmt;
	}
	public BigDecimal getRaptDpstRate() {
		return raptDpstRate;
	}
	public void setRaptDpstRate(BigDecimal raptDpstRate) {
		this.raptDpstRate = raptDpstRate;
	}
	public String getWrhsDlvDt() {
		return wrhsDlvDt;
	}
	public void setWrhsDlvDt(String wrhsDlvDt) {
		this.wrhsDlvDt = wrhsDlvDt;
	}
	public String getWrhsDlvDestCd() {
		return wrhsDlvDestCd;
	}
	public void setWrhsDlvDestCd(String wrhsDlvDestCd) {
		this.wrhsDlvDestCd = wrhsDlvDestCd;
	}
	public String getDptrDlvDt() {
		return dptrDlvDt;
	}
	public void setDptrDlvDt(String dptrDlvDt) {
		this.dptrDlvDt = dptrDlvDt;
	}
	public String getDptrDlvDestCd() {
		return dptrDlvDestCd;
	}
	public void setDptrDlvDestCd(String dptrDlvDestCd) {
		this.dptrDlvDestCd = dptrDlvDestCd;
	}
	public String getArvlDlvDt() {
		return arvlDlvDt;
	}
	public void setArvlDlvDt(String arvlDlvDt) {
		this.arvlDlvDt = arvlDlvDt;
	}
	public String getArvlDlvDestCd() {
		return arvlDlvDestCd;
	}
	public void setArvlDlvDestCd(String arvlDlvDestCd) {
		this.arvlDlvDestCd = arvlDlvDestCd;
	}
	public String getPoDlvDt() {
		return poDlvDt;
	}
	public void setPoDlvDt(String poDlvDt) {
		this.poDlvDt = poDlvDt;
	}
	public String getPoDlvDestCd() {
		return poDlvDestCd;
	}
	public void setPoDlvDestCd(String poDlvDestCd) {
		this.poDlvDestCd = poDlvDestCd;
	}
	public String getOrdMemoCont() {
		return ordMemoCont;
	}
	public void setOrdMemoCont(String ordMemoCont) {
		this.ordMemoCont = ordMemoCont;
	}
	public String getOrdTypeCd() {
		return ordTypeCd;
	}
	public void setOrdTypeCd(String ordTypeCd) {
		this.ordTypeCd = ordTypeCd;
	}
	public String getB5mBuyCont() {
		return b5mBuyCont;
	}
	public void setB5mBuyCont(String b5mBuyCont) {
		this.b5mBuyCont = b5mBuyCont;
	}
	public String getUserEml() {
		return userEml;
	}
	public void setUserEml(String userEml) {
		this.userEml = userEml;
	}
	public String getUserAlasCnsNm() {
		return userAlasCnsNm;
	}
	public void setUserAlasCnsNm(String userAlasCnsNm) {
		this.userAlasCnsNm = userAlasCnsNm;
	}
	public String getClientNm() {
		return clientNm;
	}
	public void setClientNm(String clientNm) {
		this.clientNm = clientNm;
	}
	public String getOrderedGudsNm() {
		return orderedGudsNm;
	}
	public void setOrderedGudsNm(String orderedGudsNm) {
		this.orderedGudsNm = orderedGudsNm;
	}
	public String getShowDetail() {
		return showDetail;
	}
	public void setShowDetail(String showDetail) {
		this.showDetail = showDetail;
	}
	public String getKorMng() {
		return korMng;
	}
	public void setKorMng(String korMng) {
		this.korMng = korMng;
	}
	public String getCnsMng() {
		return cnsMng;
	}
	public void setCnsMng(String cnsMng) {
		this.cnsMng = cnsMng;
	}
	public String getHistDetail() {
		return histDetail;
	}
	public void setHistDetail(String histDetail) {
		this.histDetail = histDetail;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getBactPrvdDtPlusbactPrvdAmt() {
		return bactPrvdDtPlusbactPrvdAmt;
	}
	public void setBactPrvdDtPlusbactPrvdAmt(String bactPrvdDtPlusbactPrvdAmt) {
		this.bactPrvdDtPlusbactPrvdAmt = bactPrvdDtPlusbactPrvdAmt;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public BigDecimal getKorXchrAmt() {
		return korXchrAmt;
	}
	public void setKorXchrAmt(BigDecimal korXchrAmt) {
		this.korXchrAmt = korXchrAmt;
	}
	public BigDecimal getUsdXchrAmt() {
		return usdXchrAmt;
	}
	public void setUsdXchrAmt(BigDecimal usdXchrAmt) {
		this.usdXchrAmt = usdXchrAmt;
	}
	public BigDecimal getCnsXchrAmt() {
		return cnsXchrAmt;
	}
	public void setCnsXchrAmt(BigDecimal cnsXchrAmt) {
		this.cnsXchrAmt = cnsXchrAmt;
	}
	@Override
	public String toString() {
		return "SmsMsOrdVO [ordNo=" + ordNo + ", ordNm=" + ordNm
				+ ", ordStatCd=" + ordStatCd + ", b5cOrdNo=" + b5cOrdNo
				+ ", dlvModeCd=" + dlvModeCd + ", dlvDestCd=" + dlvDestCd
				+ ", custId=" + custId + ", ordSumAmt=" + ordSumAmt
				+ ", ordEstmDt=" + ordEstmDt + ", ordReqDt=" + ordReqDt
				+ ", ordExpDt=" + ordExpDt + ", ordHopeArvlDt=" + ordHopeArvlDt
				+ ", ordArvlDt=" + ordArvlDt + ", stdXchrKindCd="
				+ stdXchrKindCd + ", stdXchrAmt=" + stdXchrAmt + ", bactRegDt="
				+ bactRegDt + ", bactRegrEml=" + bactRegrEml + ", bactPrvdDt="
				+ bactPrvdDt + ", bactPrvdAmt=" + bactPrvdAmt
				+ ", bactPrvdMemoCont=" + bactPrvdMemoCont + ", poSchdDt="
				+ poSchdDt + ", pymtPrvdModeCont=" + pymtPrvdModeCont
				+ ", ctrtTmplYn=" + ctrtTmplYn + ", smplReqYn=" + smplReqYn
				+ ", qlfcReqYn=" + qlfcReqYn + ", custOrdProcCont="
				+ custOrdProcCont + ", paptDpstDt=" + paptDpstDt
				+ ", paptDpstAmt=" + paptDpstAmt + ", paptDpstRate="
				+ paptDpstRate + ", raptDpstDt=" + raptDpstDt
				+ ", raptDpstAmt=" + raptDpstAmt + ", raptDpstRate="
				+ raptDpstRate + ", wrhsDlvDt=" + wrhsDlvDt
				+ ", wrhsDlvDestCd=" + wrhsDlvDestCd + ", dptrDlvDt="
				+ dptrDlvDt + ", dptrDlvDestCd=" + dptrDlvDestCd
				+ ", arvlDlvDt=" + arvlDlvDt + ", arvlDlvDestCd="
				+ arvlDlvDestCd + ", poDlvDt=" + poDlvDt + ", poDlvDestCd="
				+ poDlvDestCd + ", ordMemoCont=" + ordMemoCont + ", ordTypeCd="
				+ ordTypeCd + ", b5mBuyCont=" + b5mBuyCont + ", userEml="
				+ userEml + ", userAlasCnsNm=" + userAlasCnsNm + ", clientNm="
				+ clientNm + ", orderedGudsNm=" + orderedGudsNm
				+ ", showDetail=" + showDetail + ", korMng=" + korMng
				+ ", cnsMng=" + cnsMng + ", histDetail=" + histDetail
				+ ", start=" + start + ", end=" + end
				+ ", bactPrvdDtPlusbactPrvdAmt=" + bactPrvdDtPlusbactPrvdAmt
				+ ", count=" + count + ", page=" + page + ", row=" + row
				+ ", searchKeyword=" + searchKeyword + ", korXchrAmt="
				+ korXchrAmt + ", usdXchrAmt=" + usdXchrAmt + ", cnsXchrAmt="
				+ cnsXchrAmt + "]";
	}

	
	
	
	
	
	
}
