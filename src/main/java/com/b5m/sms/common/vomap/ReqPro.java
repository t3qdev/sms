package com.b5m.sms.common.vomap;

import javax.servlet.http.HttpServletRequest;

/**
 * request 관련 core 클래스
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2013. 8. 18.
 * @version 1.0
 * @author 김병찬
 */
public class ReqPro {
	/**
	 * request 내용을 box에 넣는 기능을 한다.
	 * @param request
	 * @return
	 */
	public DataBox getBox(HttpServletRequest request) {
		
		DataBox box = null;
		
		try {
			
			box = (DataBox) request.getAttribute("box");
		
		if (box == null) {
			box = new DataBox(request);
		}
		
		} catch (Exception ex) {
		}
		
		return box;
	}
}
