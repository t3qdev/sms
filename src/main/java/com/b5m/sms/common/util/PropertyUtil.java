package com.b5m.sms.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Property를 로드하는 Util Class <br />
 * Hashtabl에 프로퍼티 내용을 저장한다. 
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2013. 8. 18.
 * @version 1.0
 * @author 김병찬
 */
public class PropertyUtil {
	
	private static Hashtable<String, String> htProperty = new Hashtable<String, String>();
	
	/**
	 * 프러퍼티 로드.
	 * @since 2010. 7. 5.
	 * @param event ServletContextEvent
	 */
	public static void Load(){
		
		Properties properties = new Properties();
		
		try {
			properties.load(PropertyUtil.class.getClassLoader().getResourceAsStream("/properties/file.properties"));
			Enumeration<Object> enumeration= properties.keys();
			String sKey = null;
			String sValue = null;
			while(enumeration.hasMoreElements()){
				sKey = enumeration.nextElement().toString();
				sValue  = new String( properties.getProperty(sKey).getBytes("ISO-8859-1"),"UTF-8");
				htProperty.put(sKey, sValue);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 프러퍼티 반환.
	 * @since 2010. 7. 5.
	 * @param sKey 프로퍼티 키
	 * @return 프러퍼티 값
	 */
	public static String getProperty(String sKey){
		
		String sPropertyValue = null;
		
		if(htProperty.get(sKey) != null){
			sPropertyValue = htProperty.get(sKey).toString();
		}else{
			sPropertyValue = "";
		}
		return sPropertyValue;
	}
}
