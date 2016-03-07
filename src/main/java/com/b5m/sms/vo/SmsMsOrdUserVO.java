package com.b5m.sms.vo;


public class SmsMsOrdUserVO {
 
    /** ORD_NO */
    private java.lang.String ordNo;
    
    /** USER_EML */
    private java.lang.String userEml;

    public java.lang.String getOrdNo() {
        return this.ordNo;
    }
    
    public void setOrdNo(java.lang.String ordNo) {
        this.ordNo = ordNo;
    }
    
    public java.lang.String getUserEml() {
        return this.userEml;
    }
    
    public void setUserEml(java.lang.String userEml) {
        this.userEml = userEml;
    }

	@Override
	public String toString() {
		return "SmsMsOrdUserVO [ordNo=" + ordNo + ", userEml=" + userEml + "]";
	}
    
    
}
