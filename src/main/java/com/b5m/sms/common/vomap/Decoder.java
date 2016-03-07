package com.b5m.sms.common.vomap;

/**
 * 인코딩된 문자열을 디코딩하는 Class.
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2013. 8. 18.
 * @version 1.0
 * @author 김병찬
 */
public class Decoder {

	/**
	 * Decoder 생성자
	 */
	public Decoder() { 
	}

	/**
	 * Encoder.encode로 인코딩된 데이터를 Decode.
	 * @since 2010. 5. 28.
	 * @param s 디코딩할 문자열
	 * @return 디코딩된 문자
	 */
	public static String decode(String s, String sCharSet) {
		byte abyte0[] = null;
		try {
			StringBuffer stringbuffer = new StringBuffer();
			for (int i = 0; i < s.length(); i += 2)
				stringbuffer.append((char) Integer.parseInt(s.substring(i,
						i + 2), 16));

			String s1 = stringbuffer.toString();
			abyte0 = s1.getBytes(sCharSet);
		} catch (Exception exception) {
		}
		return new String(abyte0);
	}

}
