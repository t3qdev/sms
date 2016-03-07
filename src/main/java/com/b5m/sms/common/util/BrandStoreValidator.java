package com.b5m.sms.common.util;

import org.apache.commons.validator.GenericValidator;

public class BrandStoreValidator extends GenericValidator {

	
	public static boolean isBro(String bro) {//'-' 기준으로 split한 후 숫자여부 파악
		
		return true;
	}
	public static boolean isJumin(String jumin) {//'-' 기준으로 split한 후 숫자여부 파악
		
		return true;
	}
	
	public static boolean isPhoneKR(String telNum) {//'-' 기준으로 split한 후 숫자여부 파악
		return true;
	}
	
	public static boolean isAccount(String account) {//'-' 기준으로 split한 후 숫자여부 파악
		return true;
	}
}
