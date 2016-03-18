package com.b5m.sms.vo;


public class TbMsCmnCdVO {
    
    /** CD */
    private java.lang.String cd;
    
    /** CD_NM */
    private java.lang.String cdNm;
    
    /** CD_VAL */
    private java.lang.String cdVal;
    
    /** USE_YN */
    private java.lang.String useYn;
    
    /** SORT_NO */
    private java.math.BigDecimal sortNo;
    
    /** ETC */
    private java.lang.String etc;
    
    public java.lang.String getCd() {
        return this.cd;
    }
    
    public void setCd(java.lang.String cd) {
        this.cd = cd;
    }
    
    public java.lang.String getCdNm() {
        return this.cdNm;
    }
    
    public void setCdNm(java.lang.String cdNm) {
        this.cdNm = cdNm;
    }
    
    public java.lang.String getCdVal() {
        return this.cdVal;
    }
    
    public void setCdVal(java.lang.String cdVal) {
        this.cdVal = cdVal;
    }
    
    public java.lang.String getUseYn() {
        return this.useYn;
    }
    
    public void setUseYn(java.lang.String useYn) {
        this.useYn = useYn;
    }
    
    public java.math.BigDecimal getSortNo() {
        return this.sortNo;
    }
    
    public void setSortNo(java.math.BigDecimal sortNo) {
        this.sortNo = sortNo;
    }
    
    public java.lang.String getEtc() {
        return this.etc;
    }
    
    public void setEtc(java.lang.String etc) {
        this.etc = etc;
    }

	@Override
	public String toString() {
		return "TbMsCmnCdVO [cd=" + cd + ", cdNm=" + cdNm + ", cdVal=" + cdVal
				+ ", useYn=" + useYn + ", sortNo=" + sortNo + ", etc=" + etc
				+ "]";
	}
    
}
