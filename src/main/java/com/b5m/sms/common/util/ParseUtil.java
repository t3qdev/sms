package com.b5m.sms.common.util;

/**
 * Parse를 정의한 Util Class <br />
 * Parse 관련 함수를 정의한다. 
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2013. 8. 18.
 * @version 1.0
 * @author 김병찬
 */
public class ParseUtil {
	
    /**
     * String형을 double형으로 변환 후 반환 (null이나 공백이면 0 반환).
     * <pre>
     * double dReturn = ParseUtil.dParseDouble("123456");
     * dReturn 결과 : 123456.0
     * <br />
     * double d = ParseUtil.dParseDouble("");
     * d = 0.0
     * </pre>
     * @since  2010. 3. 3.
     * @param src double형으로 변환활 String 값
     * @return double 변형된 반환 값
     */
    public static double dGetParseDouble(String src) {
        return dGetParseDouble(src, 0.0);
    }
    
    /**
     * String형을 double형으로 변환 후 반환 (null이나 공백이면 dft 반환).
     * <pre>
     * double dReturn = ParseUtil.dParseDouble("123456", 321);
     * dReturn 결과 : 123456.0
     * <br />
     * double d = ParseUtil.dParseDouble("", 321);
     * d = 321.0
     * </pre>
     * @since  2010. 3. 3.
     * @param src double형으로 변환활 String 값
     * @param dft 값이 없을 경우 반환될 값
     * @return double 변형된 반환 값
     */
    public static double dGetParseDouble(String src, double dft) {
        //if (src == null || src == "" || src.trim() == ""){ return dft; } 박병훈 버그 수정 
        if (src==null || "".equals(src.trim()) ){ return dft; }
        return Double.parseDouble(src.trim());
    }

    /**
     * String형을 int형으로 변환 후 반환 (null이나 공백이면 -1 반환).
     * <pre>
     * int iReturn = ParseUtil.iParseInt("123456");
     * iReturn 결과 : 123456
     * </pre>
     * <br />
     * int i = ParseUtil.iParseInt("");
     * i = -1
     * @since  2010. 3. 3.
     * @param src int형으로 변환할 String 값
     * @return int 변형된 반환 값
     */
    public static int iGetParseInt(String src) {
        String s;
        
        if(StringUtil.isNullOrEmpty(src)){ return -1; }
        
        if (src == null) return -1;
        if ((s=src.trim()).length() < 1) return -1;

    	int ret = 0;
    	
    	if (s.charAt(0) == '+') { 
    		ret = Integer.parseInt(s.substring(1));
    		return ret;
    	}
    	else {
    		ret = Integer.parseInt(s);
    		return ret;
    	}
    }
	
	/**
	 * String을 int형으로 변환 후 반환 (null이나 공백이면 dft 반환).
	 * <pre>
	 * int iReturn = ParseUtil.iParseInt("123456", 321);
     * iReturn 결과 : 123456
     * <br />
     * int i = ParseUtil.iParseInt("", 321);
     * iReturn 결과 : 321
     * </pre>
	 * @since  2010. 3. 3.
	 * @param src int형으로 변환할 String 값
	 * @param dft Exception 발생시 반환될 값
	 * @return int 변형된 반환 값
	 */
	public static int iGetParseInt(String src, int dft) {
		if(StringUtil.isNullOrEmpty(src)){ return dft; }

		int result = 0;
		result = Integer.parseInt(src.trim());
		return result;
	}

    /**
     * String형을 long형으로 변환후 반환 (null이나 공백이면 -1 반환).
     * <pre>
     * long lReturn = ParseUtil.lParseLong("123456");
     * lReturn 결과 : 123456
     * <br />
     * long l = ParseUtil.lParseLong("");
     * lReturn 결과 : -1
     * </pre> 
     * @since  2010. 3. 3.
     * @param src long형으로 변환할 String 값
     * @return long 변형된 반환 값
     */
    public static long lGetParseLong(String src) {
        String s;

        if (src == null) return -1;
        if ((s=src.trim()).length() < 1) return -1;

    	long ret = 0;
    	
    	if (s.charAt(0) == '+') { 
    		ret = Long.parseLong(s.substring(1));
    		return ret;
    	}
    	else {
    		ret = Long.parseLong(s);
    		return ret;
    	}

    }
	
	/**
	 * String형을 long형으로 변환 후 반환 (null이나 공백이면 dft 반환).
	 * <pre>
	 * long l = ParseUtil.lParseLong("123456", 321);
	 * l 결과 : 123456
	 * <br />
	 * long l = ParseUtil.lParseLong("", 321);
	 * l 결과 : 321
     * </pre>
	 * @since  2010. 3. 3.
	 * @param src long형으로 변환할 String 값
	 * @param dft Exception 발생시 반환될 값
	 * @return long 변형된 반환 값
	 */
	public static long lGetParseLong(String src, long dft) {
		if(StringUtil.isNullOrEmpty(src)){ return 0; }

		long result = dft;
		
		result = Long.parseLong(src.trim());
		return result;
	}
	
    /**
     * String을 float형으로 변환 후 반환 (null이나 공백이면 -1 반환).
     * <pre>
     * float fReturn = ParseUtil.fParseFloat("123456");
     * fReturn 결과 : 123456.0
     * <br />
     * float fReturn = ParseUtil.fParseFloat("");
     * fReturn 결과 : -1
     * </pre>
     * @since  2010. 3. 3.
     * @param src float형으로 변환할 String
     * @return float 변형된 반환 값
     */
    public static float fGetParseFloat(String src) {
        String s;

        if (src == null) return -1;
        if ((s=src.trim()).length() < 1) return -1;

    	float ret = 0;
    	
    	if (s.charAt(0) == '+') { 
    		ret = Float.parseFloat(s.substring(1));
    		return ret;
    	}
    	else {
    		ret = Float.parseFloat(s);
    		return ret;
    	}

    }
    
	/**
	 * String 2개를 받아서 값을 더하고 long으로 반환 후 변환 (null이나 공백이면 -1 반환).
	 * <pre>
	 * long lReturn = ParseUtil.lSumLong("123456", "654321");
	 * lReturn 결과 : 777777
	 * <br />
	 * long l = ParseUtil.lSumLong("", "654321");
	 * lReturn 결과 : -1
     * </pre>
	 * @since  2010. 3. 3.
	 * @param src1 더하기 할 String 값
	 * @param src2 더하기 할 String 값
	 * @return long 변형되고 더해진 반환 값
	 */
	public static long lGetSumLong(String src1, String src2) {
		return lGetSumLong(src1, src2, -1);
	}

	/**
	 * String 2개를 받아서 값을 더하고 long으로 반환 (null이나 공백이면 dft 반환).
	 * <pre>
	 * long lReturn = ParseUtil.lSumLong("123456", "654321", 111);
	 * lReturn 결과 : 777777
	 * <br />
	 * long l = ParseUtil.lSumLong("123456", "", 111);
	 * lReturn 결과 : 111
     * </pre>
	 * @since  2010. 3. 3.
	 * @param src1 더하기 할 String 값
	 * @param src2 더하기 할 String 값
	 * @param dft Exception이 일어날경우 반환될 값
	 * @return long 변형되고 더해진 반환 값
	 */
	public static long lGetSumLong(String src1, String src2, long dft) {
		if(StringUtil.isNullOrEmpty(src1) || StringUtil.isNullOrEmpty(src2)){ return dft; }

		long result = dft;
		result = Long.parseLong(src1.trim()) + Long.parseLong(src2.trim());

		return result;
	}
	
	/**
	 * String 2개를 받아서 값을 빼고 long으로 반환 (null이나 공백이면 -1 반환).
	 * <pre>
	 * long lReturn = ParseUtil.lMinusLong("654321", "123456");
	 * lReturn 결과 : 530865
	 * <br />
	 * long l = ParseUtil.lMinusLong("654321", "");
	 * lReturn 결과 : -1
     * </pre>
	 * @since  2010. 3. 3.
	 * @param src1 빼어질 대상
	 * @param src2 빼어질 대상에서 빼는 수
	 * @return long 변형되고 뺄셈한 반환 값
	 */
	public static long lGetMinusLong(String src1, String src2) {
		return lGetMinusLong(src1, src2, -1);
	}

	/**
	 * String 2개를 받아서 값을 빼고 long으로 반환 (null이나 공백이면 dft 반환).
	 * <pre>
	 * long lReturn = ParseUtil.lMinusLong("654321", "123456", 111);
	 * lReturn 결과 : 530865
	 * <br />
	 * long lReturn = ParseUtil.lMinusLong("654321", "", 111);
	 * lReturn 결과 : 111
     * </pre>
	 * @since  2010. 3. 3.
	 * @param src1 빼어질 대상
	 * @param src2 빼어질 대상에서 빼는 수
	 * @param dft Exception이 일어날경우 반환될 값
	 * @return long 변형되고 뺄셈한 반환 값
	 */
	public static long lGetMinusLong(String src1, String src2, long dft) {
		if(StringUtil.isNullOrEmpty(src1) || StringUtil.isNullOrEmpty(src2)){ return dft; }

		long result = dft;
		result = Long.parseLong(src1.trim()) - Long.parseLong(src2.trim());
		return result;
	}

    /**
	 * 문자열을 정수로 변환 후 반환 (null이나 공백이면 0을 반환).
	 * <pre>
	 * int iReturn = ParseUtil.iStrToInt("123456");
	 * iReturn 결과 : 123456
     * </pre>
	 * @since  : 2010. 3. 3.
	 * @param str 변환할 문자열
	 * @return int 변환된 반환값
	 */
	public static int iGetStrToInt(String str)
	{
		if(str == null)
			return 0;
		else
			return Integer.valueOf(str).intValue();
	}
	
}

